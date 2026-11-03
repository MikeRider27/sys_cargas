package com.syscargas.auth;

import com.syscargas.common.ApiException;
import com.syscargas.common.Estado;
import com.syscargas.config.AppProperties;
import java.time.Duration;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private static final String CREDENCIALES_INVALIDAS = "Usuario o contraseña incorrectos";

    private final JdbcClient jdbc;
    private final PasswordEncoder encoder;
    private final JwtEncoder jwtEncoder;
    private final AppProperties props;
    /** Hash usado cuando el usuario no existe, para que el tiempo de respuesta no revele si existe. */
    private final String dummyHash;

    public AuthService(JdbcClient jdbc, PasswordEncoder encoder, JwtEncoder jwtEncoder, AppProperties props) {
        this.jdbc = jdbc;
        this.encoder = encoder;
        this.jwtEncoder = jwtEncoder;
        this.props = props;
        this.dummyHash = encoder.encode("dummy-password-para-tiempo-constante");
    }

    record UsuarioAuth(int idUsu, String username, String passwordHash, String rol, Integer idPer,
                       int idEst, int intentosFallidos, OffsetDateTime bloqueadoHasta, String nombre) {}

    public record LoginResult(String token, Map<String, Object> usuario) {}

    @Transactional(noRollbackFor = ApiException.class)
    public LoginResult login(String username, String password) {
        Optional<UsuarioAuth> found = jdbc.sql("""
                SELECT u.id_usu, u.username, u.password_hash, r.codigo AS rol, u.id_per, u.id_est,
                       u.intentos_fallidos, u.bloqueado_hasta,
                       coalesce(p.nombres || ' ' || p.apellidos, u.username) AS nombre
                FROM usuario u
                JOIN rol r ON r.id_rol = u.id_rol
                LEFT JOIN personal p ON p.id_per = u.id_per
                WHERE u.username = :username
                FOR UPDATE OF u
                """)
                .param("username", username.trim().toLowerCase())
                .query(UsuarioAuth.class)
                .optional();

        if (found.isEmpty()) {
            encoder.matches(password, dummyHash);
            throw new ApiException(HttpStatus.UNAUTHORIZED, CREDENCIALES_INVALIDAS);
        }
        UsuarioAuth u = found.get();
        if (u.bloqueadoHasta() != null && u.bloqueadoHasta().toInstant().isAfter(Instant.now())) {
            throw new ApiException(HttpStatus.LOCKED,
                    "Usuario bloqueado temporalmente por intentos fallidos. Intente más tarde");
        }
        if (!encoder.matches(password, u.passwordHash())) {
            int intentos = u.intentosFallidos() + 1;
            boolean bloquear = intentos >= props.login().maxIntentos();
            jdbc.sql("""
                    UPDATE usuario SET intentos_fallidos = :intentos,
                           bloqueado_hasta = CASE WHEN :bloquear THEN now() + make_interval(mins => :mins) END
                    WHERE id_usu = :id
                    """)
                    .param("intentos", bloquear ? 0 : intentos)
                    .param("bloquear", bloquear)
                    .param("mins", props.login().bloqueoMinutos())
                    .param("id", u.idUsu())
                    .update();
            throw new ApiException(HttpStatus.UNAUTHORIZED, CREDENCIALES_INVALIDAS);
        }
        if (u.idEst() != Estado.ACTIVO) {
            throw new ApiException(HttpStatus.FORBIDDEN, "El usuario está inactivo, contacte con el administrador");
        }
        jdbc.sql("UPDATE usuario SET intentos_fallidos = 0, bloqueado_hasta = NULL, ultimo_acceso = now() WHERE id_usu = :id")
                .param("id", u.idUsu())
                .update();

        Instant now = Instant.now();
        JwtClaimsSet.Builder claims = JwtClaimsSet.builder()
                .issuer("sys-cargas")
                .subject(u.username())
                .issuedAt(now)
                .expiresAt(now.plus(Duration.ofMinutes(props.jwt().expirationMinutes())))
                .claim("uid", u.idUsu())
                .claim("rol", u.rol())
                .claim("nombre", u.nombre());
        if (u.idPer() != null) {
            claims.claim("per", u.idPer());
        }
        String token = jwtEncoder.encode(JwtEncoderParameters.from(
                JwsHeader.with(MacAlgorithm.HS256).build(), claims.build())).getTokenValue();
        return new LoginResult(token, Map.of(
                "id", u.idUsu(), "username", u.username(), "rol", u.rol(), "nombre", u.nombre()));
    }

    @Transactional
    public void cambiarPassword(int idUsu, String actual, String nueva) {
        String hash = jdbc.sql("SELECT password_hash FROM usuario WHERE id_usu = :id")
                .param("id", idUsu).query(String.class).single();
        if (!encoder.matches(actual, hash)) {
            throw ApiException.badRequest("La contraseña actual no es correcta");
        }
        PasswordPolicy.validar(nueva);
        jdbc.sql("UPDATE usuario SET password_hash = :h WHERE id_usu = :id")
                .param("h", encoder.encode(nueva)).param("id", idUsu).update();
    }
}

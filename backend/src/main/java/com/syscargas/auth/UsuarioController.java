package com.syscargas.auth;

import com.syscargas.common.ApiException;
import com.syscargas.common.CurrentUser;
import com.syscargas.common.Estado;
import com.syscargas.common.Rows;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Administracion de usuarios (solo rol ADMIN, ver SecurityConfig). */
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final JdbcClient jdbc;
    private final PasswordEncoder encoder;

    public UsuarioController(JdbcClient jdbc, PasswordEncoder encoder) {
        this.jdbc = jdbc;
        this.encoder = encoder;
    }

    public record UsuarioNuevo(@NotBlank @Pattern(regexp = "[a-zA-Z0-9._-]{3,50}") String username,
                               @NotBlank String password, @NotNull Integer idRol, Integer idPer) {}

    public record UsuarioEdicion(@NotNull Integer idRol, Integer idPer, @NotNull Integer idEst) {}

    public record NuevaPassword(@NotBlank String password) {}

    @GetMapping
    public List<Map<String, Object>> listar() {
        return jdbc.sql("""
                SELECT u.id_usu, u.username, u.id_rol, r.codigo AS rol, u.id_per,
                       p.nombres || ' ' || p.apellidos AS personal, u.id_est, e.descrip AS estado,
                       u.ultimo_acceso, (u.bloqueado_hasta > now()) AS bloqueado
                FROM usuario u
                JOIN rol r ON r.id_rol = u.id_rol
                JOIN estado e ON e.id_est = u.id_est
                LEFT JOIN personal p ON p.id_per = u.id_per
                ORDER BY u.username
                """).query(Rows.MAP).list();
    }

    @PostMapping
    @Transactional
    public Map<String, Object> crear(@Valid @RequestBody UsuarioNuevo req) {
        PasswordPolicy.validar(req.password());
        Integer id = jdbc.sql("""
                INSERT INTO usuario (username, password_hash, id_rol, id_per)
                VALUES (:u, :h, :rol, :per) RETURNING id_usu
                """)
                .param("u", req.username().toLowerCase())
                .param("h", encoder.encode(req.password()))
                .param("rol", req.idRol())
                .param("per", req.idPer())
                .query(Integer.class).single();
        return Map.of("id_usu", id);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> editar(@PathVariable int id, @Valid @RequestBody UsuarioEdicion req,
                                       @AuthenticationPrincipal Jwt jwt) {
        if (id == CurrentUser.idUsu(jwt) && req.idEst() != Estado.ACTIVO) {
            throw ApiException.badRequest("No puede desactivar su propio usuario");
        }
        if (req.idEst() != Estado.ACTIVO && req.idEst() != Estado.ANULADO) {
            throw ApiException.badRequest("Estado inválido");
        }
        int n = jdbc.sql("UPDATE usuario SET id_rol = :rol, id_per = :per, id_est = :est WHERE id_usu = :id")
                .param("rol", req.idRol()).param("per", req.idPer()).param("est", req.idEst()).param("id", id)
                .update();
        if (n == 0) {
            throw ApiException.notFound("Usuario");
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/password")
    @Transactional
    public ResponseEntity<Void> resetPassword(@PathVariable int id, @Valid @RequestBody NuevaPassword req) {
        PasswordPolicy.validar(req.password());
        int n = jdbc.sql("""
                UPDATE usuario SET password_hash = :h, intentos_fallidos = 0, bloqueado_hasta = NULL
                WHERE id_usu = :id
                """)
                .param("h", encoder.encode(req.password())).param("id", id).update();
        if (n == 0) {
            throw ApiException.notFound("Usuario");
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desactivar(@PathVariable int id, @AuthenticationPrincipal Jwt jwt) {
        return editarEstado(id, jwt);
    }

    private ResponseEntity<Void> editarEstado(int id, Jwt jwt) {
        if (id == CurrentUser.idUsu(jwt)) {
            throw ApiException.badRequest("No puede desactivar su propio usuario");
        }
        jdbc.sql("UPDATE usuario SET id_est = 0 WHERE id_usu = :id").param("id", id).update();
        return ResponseEntity.noContent().build();
    }
}

package com.syscargas.auth;

import com.syscargas.config.AppProperties;
import java.security.SecureRandom;
import java.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/** Crea el usuario administrador en el primer arranque (reemplaza al usuario con clave MD5 fija del legacy). */
@Component
public class AdminBootstrap implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(AdminBootstrap.class);

    private final JdbcClient jdbc;
    private final PasswordEncoder encoder;
    private final AppProperties props;

    public AdminBootstrap(JdbcClient jdbc, PasswordEncoder encoder, AppProperties props) {
        this.jdbc = jdbc;
        this.encoder = encoder;
        this.props = props;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        long usuarios = jdbc.sql("SELECT count(*) FROM usuario").query(Long.class).single();
        if (usuarios > 0) {
            return;
        }
        String password = props.admin().password();
        boolean generada = password == null || password.isBlank();
        if (generada) {
            byte[] bytes = new byte[18];
            new SecureRandom().nextBytes(bytes);
            password = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes) + "1a";
        } else {
            PasswordPolicy.validar(password);
        }
        String username = props.admin().username().trim().toLowerCase();
        jdbc.sql("""
                INSERT INTO usuario (username, password_hash, id_rol, id_per)
                SELECT :u, :h, (SELECT id_rol FROM rol WHERE codigo = 'ADMIN'),
                       (SELECT id_per FROM personal WHERE nro_doc = 'ADMIN')
                """)
                .param("u", username)
                .param("h", encoder.encode(password))
                .update();
        if (generada) {
            log.warn("ADMIN_PASSWORD no definido. Usuario '{}' creado con contraseña temporal: {}  (cámbiela al ingresar)",
                    username, password);
        } else {
            log.info("Usuario administrador '{}' creado", username);
        }
    }
}

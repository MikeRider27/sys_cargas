package com.syscargas.auth;

import com.syscargas.common.ApiException;
import java.nio.charset.StandardCharsets;

final class PasswordPolicy {

    private PasswordPolicy() {}

    static void validar(String password) {
        if (password == null || password.length() < 8) {
            throw ApiException.badRequest("La contraseña debe tener al menos 8 caracteres");
        }
        // BCrypt solo considera los primeros 72 bytes
        if (password.getBytes(StandardCharsets.UTF_8).length > 72) {
            throw ApiException.badRequest("La contraseña no puede superar 72 bytes");
        }
        boolean letra = password.chars().anyMatch(Character::isLetter);
        boolean digito = password.chars().anyMatch(Character::isDigit);
        if (!letra || !digito) {
            throw ApiException.badRequest("La contraseña debe combinar letras y números");
        }
    }
}

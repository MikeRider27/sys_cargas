package com.syscargas.common;

import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;

/** Datos del usuario autenticado tomados de los claims del token. */
public final class CurrentUser {

    private CurrentUser() {}

    public static int idPer(Jwt jwt) {
        Object per = jwt.getClaim("per");
        if (per == null) {
            throw new ApiException(HttpStatus.FORBIDDEN,
                    "Su usuario no esta vinculado a un registro de personal; solicite al administrador que lo vincule");
        }
        return ((Number) per).intValue();
    }

    public static int idUsu(Jwt jwt) {
        return ((Number) jwt.getClaim("uid")).intValue();
    }
}

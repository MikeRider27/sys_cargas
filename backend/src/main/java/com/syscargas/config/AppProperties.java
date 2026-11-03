package com.syscargas.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public record AppProperties(Jwt jwt, Admin admin, Login login) {

    public record Jwt(String secret, long expirationMinutes) {}

    public record Admin(String username, String password) {}

    public record Login(int maxIntentos, int bloqueoMinutos) {}
}

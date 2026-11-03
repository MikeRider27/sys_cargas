package com.syscargas.auth;

import com.syscargas.common.CurrentUser;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    public record LoginRequest(@NotBlank @Size(max = 50) String username,
                               @NotBlank @Size(max = 200) String password) {}

    public record CambioPassword(@NotBlank String actual, @NotBlank String nueva) {}

    @PostMapping("/login")
    public AuthService.LoginResult login(@Valid @RequestBody LoginRequest req) {
        return service.login(req.username(), req.password());
    }

    @GetMapping("/me")
    public Map<String, Object> me(@AuthenticationPrincipal Jwt jwt) {
        return Map.of("id", jwt.getClaim("uid"), "username", jwt.getSubject(),
                "rol", jwt.getClaim("rol"), "nombre", jwt.getClaim("nombre"));
    }

    @PostMapping("/password")
    public ResponseEntity<Void> cambiarPassword(@AuthenticationPrincipal Jwt jwt, @Valid @RequestBody CambioPassword req) {
        service.cambiarPassword(CurrentUser.idUsu(jwt), req.actual(), req.nueva());
        return ResponseEntity.noContent().build();
    }
}

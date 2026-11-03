package com.syscargas.logistica;

import com.syscargas.common.CurrentUser;
import com.syscargas.logistica.LogisticaDtos.AsignacionReq;
import com.syscargas.logistica.LogisticaDtos.FleteroReq;
import com.syscargas.logistica.LogisticaDtos.LlegadaReq;
import com.syscargas.logistica.LogisticaDtos.SalidaReq;
import com.syscargas.logistica.LogisticaDtos.ViajeReq;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LogisticaController {

    private final LogisticaService service;

    public LogisticaController(LogisticaService service) {
        this.service = service;
    }

    @GetMapping("/asignaciones")
    public List<Map<String, Object>> asignaciones() {
        return service.asignaciones();
    }

    @GetMapping("/asignaciones/{id}")
    public Map<String, Object> asignacion(@PathVariable int id) {
        return service.asignacion(id);
    }

    @PostMapping("/asignaciones")
    public Map<String, Object> crearAsignacion(@Valid @RequestBody AsignacionReq req, @AuthenticationPrincipal Jwt jwt) {
        return service.crearAsignacion(req, CurrentUser.idPer(jwt));
    }

    @DeleteMapping("/asignaciones/{id}")
    public ResponseEntity<Void> anularAsignacion(@PathVariable int id) {
        service.anularAsignacion(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/salidas")
    public List<Map<String, Object>> salidas() {
        return service.salidas();
    }

    @PostMapping("/salidas")
    public Map<String, Object> crearSalida(@Valid @RequestBody SalidaReq req) {
        return service.crearSalida(req);
    }

    @DeleteMapping("/salidas/{id}")
    public ResponseEntity<Void> anularSalida(@PathVariable int id) {
        service.anularSalida(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/llegadas")
    public List<Map<String, Object>> llegadas() {
        return service.llegadas();
    }

    @PostMapping("/llegadas")
    public Map<String, Object> crearLlegada(@Valid @RequestBody LlegadaReq req) {
        return service.crearLlegada(req);
    }

    @DeleteMapping("/llegadas/{id}")
    public ResponseEntity<Void> anularLlegada(@PathVariable int id) {
        service.anularLlegada(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/viajes")
    public List<Map<String, Object>> viajes() {
        return service.viajes();
    }

    @GetMapping("/viajes/{id}")
    public Map<String, Object> viaje(@PathVariable int id) {
        return service.viaje(id);
    }

    @PostMapping("/viajes")
    public Map<String, Object> crearViaje(@Valid @RequestBody ViajeReq req, @AuthenticationPrincipal Jwt jwt) {
        return service.crearViaje(req, CurrentUser.idPer(jwt));
    }

    @DeleteMapping("/viajes/{id}")
    public ResponseEntity<Void> anularViaje(@PathVariable int id) {
        service.anularViaje(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/viajes/{id}/fleteros")
    public Map<String, Object> asignarFletero(@PathVariable int id, @Valid @RequestBody FleteroReq req) {
        return service.asignarFletero(id, req);
    }

    @DeleteMapping("/fleteros-viaje/{id}")
    public ResponseEntity<Void> quitarFletero(@PathVariable int id) {
        service.quitarFletero(id);
        return ResponseEntity.noContent().build();
    }
}

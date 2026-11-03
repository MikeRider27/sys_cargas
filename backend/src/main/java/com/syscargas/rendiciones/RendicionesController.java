package com.syscargas.rendiciones;

import com.syscargas.common.CurrentUser;
import com.syscargas.rendiciones.RendicionesDtos.AperturaReq;
import com.syscargas.rendiciones.RendicionesDtos.CierreReq;
import com.syscargas.rendiciones.RendicionesDtos.ComprobanteReq;
import com.syscargas.rendiciones.RendicionesDtos.RendicionReq;
import com.syscargas.rendiciones.RendicionesDtos.ViaticoReq;
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
public class RendicionesController {

    private final RendicionesService service;

    public RendicionesController(RendicionesService service) {
        this.service = service;
    }

    @GetMapping("/cajas")
    public List<Map<String, Object>> aperturas() {
        return service.aperturas();
    }

    @PostMapping("/cajas")
    public Map<String, Object> abrir(@Valid @RequestBody AperturaReq req, @AuthenticationPrincipal Jwt jwt) {
        return service.abrir(req, CurrentUser.idPer(jwt));
    }

    @PostMapping("/cajas/{id}/cerrar")
    public Map<String, Object> cerrar(@PathVariable int id, @Valid @RequestBody CierreReq req) {
        return service.cerrar(id, req);
    }

    @GetMapping("/viaticos")
    public List<Map<String, Object>> viaticos() {
        return service.viaticos();
    }

    @GetMapping("/viaticos/{id}")
    public Map<String, Object> viatico(@PathVariable int id) {
        return service.viatico(id);
    }

    @PostMapping("/viaticos")
    public Map<String, Object> crearViatico(@Valid @RequestBody ViaticoReq req) {
        return service.crearViatico(req);
    }

    @DeleteMapping("/viaticos/{id}")
    public ResponseEntity<Void> anularViatico(@PathVariable int id) {
        service.anularViatico(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/comprobantes")
    public List<Map<String, Object>> comprobantes() {
        return service.comprobantes();
    }

    @PostMapping("/comprobantes")
    public Map<String, Object> crearComprobante(@Valid @RequestBody ComprobanteReq req) {
        return service.crearComprobante(req);
    }

    @DeleteMapping("/comprobantes/{id}")
    public ResponseEntity<Void> anularComprobante(@PathVariable int id) {
        service.anularComprobante(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/rendiciones")
    public List<Map<String, Object>> rendiciones() {
        return service.rendiciones();
    }

    @PostMapping("/rendiciones")
    public Map<String, Object> crearRendicion(@Valid @RequestBody RendicionReq req, @AuthenticationPrincipal Jwt jwt) {
        return service.crearRendicion(req, CurrentUser.idPer(jwt));
    }

    @DeleteMapping("/rendiciones/{id}")
    public ResponseEntity<Void> anularRendicion(@PathVariable int id) {
        service.anularRendicion(id);
        return ResponseEntity.noContent().build();
    }
}

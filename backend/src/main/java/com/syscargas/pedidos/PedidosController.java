package com.syscargas.pedidos;

import com.syscargas.common.CurrentUser;
import com.syscargas.pedidos.PedidosDtos.CalculoCotizacion;
import com.syscargas.pedidos.PedidosDtos.CotizacionReq;
import com.syscargas.pedidos.PedidosDtos.CrtReq;
import com.syscargas.pedidos.PedidosDtos.EventoReq;
import com.syscargas.pedidos.PedidosDtos.PedidoFleteReq;
import com.syscargas.pedidos.PedidosDtos.PedidoReq;
import com.syscargas.pedidos.PedidosDtos.ReclamoReq;
import com.syscargas.pedidos.PedidosDtos.ResolucionReclamo;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PedidosController {

    private final PedidosService service;

    public PedidosController(PedidosService service) {
        this.service = service;
    }

    // --- Pedidos del cliente
    @GetMapping("/pedidos")
    public List<Map<String, Object>> pedidos() {
        return service.pedidos();
    }

    @GetMapping("/pedidos/{id}")
    public Map<String, Object> pedido(@PathVariable int id) {
        return service.pedido(id);
    }

    @PostMapping("/pedidos")
    public Map<String, Object> crearPedido(@Valid @RequestBody PedidoReq req, @AuthenticationPrincipal Jwt jwt) {
        return service.crearPedido(req, CurrentUser.idPer(jwt));
    }

    @DeleteMapping("/pedidos/{id}")
    public ResponseEntity<Void> anularPedido(@PathVariable int id) {
        service.anularPedido(id);
        return ResponseEntity.noContent().build();
    }

    // --- Cotizaciones
    @GetMapping("/cotizaciones")
    public List<Map<String, Object>> cotizaciones() {
        return service.cotizaciones();
    }

    @GetMapping("/cotizaciones/{id}")
    public Map<String, Object> cotizacion(@PathVariable int id) {
        return service.cotizacion(id);
    }

    @PostMapping("/cotizaciones/calcular")
    public Map<String, Object> calcular(@Valid @RequestBody CalculoCotizacion req) {
        return service.calcular(req);
    }

    @PostMapping("/cotizaciones")
    public Map<String, Object> crearCotizacion(@Valid @RequestBody CotizacionReq req, @AuthenticationPrincipal Jwt jwt) {
        return service.crearCotizacion(req, CurrentUser.idPer(jwt));
    }

    @DeleteMapping("/cotizaciones/{id}")
    public ResponseEntity<Void> anularCotizacion(@PathVariable int id) {
        service.anularCotizacion(id);
        return ResponseEntity.noContent().build();
    }

    // --- Pedidos de flete
    @GetMapping("/pedidos-flete")
    public List<Map<String, Object>> pedidosFlete() {
        return service.pedidosFlete();
    }

    @GetMapping("/pedidos-flete/{id}")
    public Map<String, Object> pedidoFlete(@PathVariable int id) {
        return service.pedidoFlete(id);
    }

    @PostMapping("/pedidos-flete")
    public Map<String, Object> crearPedidoFlete(@Valid @RequestBody PedidoFleteReq req, @AuthenticationPrincipal Jwt jwt) {
        return service.crearPedidoFlete(req, CurrentUser.idPer(jwt));
    }

    @DeleteMapping("/pedidos-flete/{id}")
    public ResponseEntity<Void> anularPedidoFlete(@PathVariable int id) {
        service.anularPedidoFlete(id);
        return ResponseEntity.noContent().build();
    }

    // --- CRT
    @GetMapping("/crt")
    public List<Map<String, Object>> crts() {
        return service.crts();
    }

    @GetMapping("/crt/{id}")
    public Map<String, Object> crt(@PathVariable int id) {
        return service.crt(id);
    }

    @PostMapping("/crt")
    public Map<String, Object> crearCrt(@Valid @RequestBody CrtReq req, @AuthenticationPrincipal Jwt jwt) {
        return service.crearCrt(req, CurrentUser.idPer(jwt));
    }

    @DeleteMapping("/crt/{id}")
    public ResponseEntity<Void> anularCrt(@PathVariable int id) {
        service.anularCrt(id);
        return ResponseEntity.noContent().build();
    }

    // --- Eventos de carga
    @GetMapping("/eventos")
    public List<Map<String, Object>> eventos(@RequestParam(required = false) Integer crt) {
        return service.eventos(crt);
    }

    @PostMapping("/eventos")
    public Map<String, Object> crearEvento(@Valid @RequestBody EventoReq req, @AuthenticationPrincipal Jwt jwt) {
        return service.crearEvento(req, CurrentUser.idPer(jwt));
    }

    @DeleteMapping("/eventos/{id}")
    public ResponseEntity<Void> anularEvento(@PathVariable int id) {
        service.anularEvento(id);
        return ResponseEntity.noContent().build();
    }

    // --- Reclamos
    @GetMapping("/reclamos")
    public List<Map<String, Object>> reclamos(@RequestParam(required = false) Integer estado) {
        return service.reclamos(estado);
    }

    @GetMapping("/reclamos/{id}")
    public Map<String, Object> reclamo(@PathVariable int id) {
        return service.reclamo(id);
    }

    @PostMapping("/reclamos")
    public Map<String, Object> crearReclamo(@Valid @RequestBody ReclamoReq req, @AuthenticationPrincipal Jwt jwt) {
        return service.crearReclamo(req, CurrentUser.idPer(jwt));
    }

    @PostMapping("/reclamos/{id}/resolver")
    public Map<String, Object> resolverReclamo(@PathVariable int id, @Valid @RequestBody ResolucionReclamo req) {
        return service.resolverReclamo(id, req);
    }

    @DeleteMapping("/reclamos/{id}")
    public ResponseEntity<Void> anularReclamo(@PathVariable int id) {
        service.anularReclamo(id);
        return ResponseEntity.noContent().build();
    }
}

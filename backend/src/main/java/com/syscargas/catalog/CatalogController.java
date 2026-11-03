package com.syscargas.catalog;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/catalogos")
public class CatalogController {

    private final CatalogService service;
    private final CatalogRegistry registry;

    public CatalogController(CatalogService service, CatalogRegistry registry) {
        this.service = service;
        this.registry = registry;
    }

    /** Metadatos para que el frontend genere listados y formularios. */
    @GetMapping
    public Collection<CatalogDef> definiciones() {
        return registry.all();
    }

    @GetMapping("/{name}")
    public List<Map<String, Object>> list(@PathVariable String name,
                                          @RequestParam(required = false) String q,
                                          @RequestParam(defaultValue = "false") boolean anulados) {
        return service.list(name, q, anulados);
    }

    @GetMapping("/{name}/{id}")
    public Map<String, Object> get(@PathVariable String name, @PathVariable int id) {
        return service.get(name, id);
    }

    @PostMapping("/{name}")
    public Map<String, Object> create(@PathVariable String name, @RequestBody Map<String, Object> body) {
        return service.create(name, body);
    }

    @PutMapping("/{name}/{id}")
    public Map<String, Object> update(@PathVariable String name, @PathVariable int id,
                                      @RequestBody Map<String, Object> body) {
        return service.update(name, id, body);
    }

    @DeleteMapping("/{name}/{id}")
    public ResponseEntity<Void> delete(@PathVariable String name, @PathVariable int id) {
        service.delete(name, id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{name}/{id}/activar")
    public ResponseEntity<Void> activar(@PathVariable String name, @PathVariable int id) {
        service.activar(name, id);
        return ResponseEntity.noContent().build();
    }
}

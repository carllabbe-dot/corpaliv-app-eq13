package cl.corpaliv.ms_solicitudes.controller;

import cl.corpaliv.ms_solicitudes.entity.Solicitud;
import cl.corpaliv.ms_solicitudes.service.SolicitudService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/solicitudes")
public class SolicitudController {

    private final SolicitudService service;

    public SolicitudController(SolicitudService service) {
        this.service = service;
    }

    // GET: obtener todas las solicitudes
    @GetMapping
    public List<Solicitud> listar() {
        return service.listar();
    }

    // GET: obtener solicitud por ID
    @GetMapping("/{id}")
    public Solicitud buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    // POST: crear solicitud
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Solicitud crear(@RequestBody Solicitud solicitud) {
        return service.crear(solicitud);
    }

    // PUT: actualizar solicitud
    @PutMapping("/{id}")
    public Solicitud actualizar(
            @PathVariable Long id,
            @RequestBody Solicitud solicitud) {

        return service.actualizar(id, solicitud);
    }

    // DELETE: eliminar solicitud
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}

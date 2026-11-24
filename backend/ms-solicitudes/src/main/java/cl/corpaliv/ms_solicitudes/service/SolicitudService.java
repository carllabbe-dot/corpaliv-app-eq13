
package cl.corpaliv.ms_solicitudes.service;

import cl.corpaliv.ms_solicitudes.entity.Solicitud;
import cl.corpaliv.ms_solicitudes.repository.SolicitudRepository;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

@Service
public class SolicitudService {

    private final SolicitudRepository repository;

    public SolicitudService(SolicitudRepository repository) {
        this.repository = repository;
    }

    // CREATE: guardar una solicitud
    public Solicitud crear(Solicitud solicitud) {
        solicitud.setId(null);
        return repository.save(solicitud);
    }

    // READ: obtener todas las solicitudes
    public List<Solicitud> listar() {
        return repository.findAll();
    }

    // READ: obtener una solicitud por ID
    public Solicitud buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Solicitud no encontrada"
                ));
    }

    // UPDATE: modificar una solicitud
    public Solicitud actualizar(Long id, Solicitud datos) {
        Solicitud solicitud = buscarPorId(id);

        solicitud.setNombre(datos.getNombre());
        solicitud.setCorreo(datos.getCorreo());
        solicitud.setDireccion(datos.getDireccion());
        solicitud.setLogoEmpresa(datos.getLogoEmpresa());

        return repository.save(solicitud);
    }

    // DELETE: eliminar una solicitud
    public void eliminar(Long id) {
        Solicitud solicitud = buscarPorId(id);
        repository.delete(solicitud);
    }
}

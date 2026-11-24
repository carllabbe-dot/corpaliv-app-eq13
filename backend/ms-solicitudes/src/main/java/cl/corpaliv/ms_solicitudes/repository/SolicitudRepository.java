
package cl.corpaliv.ms_solicitudes.repository;

import cl.corpaliv.ms_solicitudes.entity.Solicitud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolicitudRepository
        extends JpaRepository<Solicitud, Long> {

}

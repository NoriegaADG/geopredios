package mx.noriegaadg.geopredios_backend.catalogs.infrastructure.persistence;

import mx.noriegaadg.geopredios_backend.catalogs.domain.Municipality;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MunicipalityRepository
        extends JpaRepository<Municipality, UUID> {

    List<Municipality> findAllByActiveTrueOrderByStateCodeAscNameAsc();
}
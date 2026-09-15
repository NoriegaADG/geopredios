package mx.noriegaadg.geopredios_backend.catalogs.infrastructure.persistence;

import mx.noriegaadg.geopredios_backend.catalogs.domain.Program;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProgramRepository extends JpaRepository<Program, UUID> {

    List<Program> findAllByActiveTrueOrderByNameAsc();
}
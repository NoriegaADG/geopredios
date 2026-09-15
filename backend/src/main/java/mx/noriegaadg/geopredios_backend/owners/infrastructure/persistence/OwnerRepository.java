package mx.noriegaadg.geopredios_backend.owners.infrastructure.persistence;

import mx.noriegaadg.geopredios_backend.owners.domain.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OwnerRepository extends JpaRepository<Owner, UUID> {

    List<Owner> findAllByOrderByNameAsc();
}
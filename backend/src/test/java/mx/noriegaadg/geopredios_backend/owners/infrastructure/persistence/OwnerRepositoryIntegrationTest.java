package mx.noriegaadg.geopredios_backend.owners.infrastructure.persistence;

import jakarta.persistence.EntityManager;
import mx.noriegaadg.geopredios_backend.owners.domain.Owner;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class OwnerRepositoryIntegrationTest {

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void shouldCreateAndUpdateOwnerWithAuditFieldsAndVersion() {
        Owner owner = new Owner(
                "Productor de integración",
                "NOGD950923HOCXXXXXXXX".substring(0, 18),
                "9510000000"
        );

        Owner savedOwner = ownerRepository.saveAndFlush(owner);

        UUID ownerId = savedOwner.getId();
        long initialVersion = savedOwner.getVersion();
        Instant initialCreatedAt = savedOwner.getCreatedAt();
        Instant initialUpdatedAt = savedOwner.getUpdatedAt();

        assertThat(ownerId).isNotNull();
        assertThat(initialVersion).isZero();
        assertThat(initialCreatedAt).isNotNull();
        assertThat(initialUpdatedAt).isNotNull();

        savedOwner.update(
                "Productor actualizado",
                null,
                "9511111111"
        );

        ownerRepository.saveAndFlush(savedOwner);
        entityManager.clear();

        Owner updatedOwner = ownerRepository.findById(ownerId).orElseThrow();

        assertThat(updatedOwner.getName()).isEqualTo("Productor actualizado");
        assertThat(updatedOwner.getCurp()).isNull();
        assertThat(updatedOwner.getPhone()).isEqualTo("9511111111");
        assertThat(updatedOwner.getVersion()).isEqualTo(initialVersion + 1);
        assertThat(updatedOwner.getCreatedAt()).isEqualTo(initialCreatedAt);
        assertThat(updatedOwner.getUpdatedAt())
                .isAfterOrEqualTo(initialUpdatedAt);
    }
}
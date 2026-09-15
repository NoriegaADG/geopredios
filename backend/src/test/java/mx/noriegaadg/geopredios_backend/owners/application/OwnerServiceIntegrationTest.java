package mx.noriegaadg.geopredios_backend.owners.application;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class OwnerServiceIntegrationTest {

    @Autowired
    private OwnerService ownerService;

    @Test
    void shouldCreateOwnerAndNormalizeItsData() {
        OwnerResult owner = ownerService.create(
                new CreateOwnerCommand(
                        "  Productor de servicio  ",
                        "abcd950923hocxyz01",
                        "  9512222222  "
                )
        );

        assertThat(owner.id()).isNotNull();
        assertThat(owner.name()).isEqualTo("Productor de servicio");
        assertThat(owner.curp()).isEqualTo("ABCD950923HOCXYZ01");
        assertThat(owner.phone()).isEqualTo("9512222222");
        assertThat(owner.version()).isZero();
        assertThat(owner.createdAt()).isNotNull();
        assertThat(owner.updatedAt()).isNotNull();

        OwnerResult foundOwner = ownerService.findById(owner.id());

        assertThat(foundOwner).isEqualTo(owner);
    }

    @Test
    void shouldRejectUpdateWithOutdatedVersion() {
        OwnerResult created = ownerService.create(
                new CreateOwnerCommand(
                        "Productor con versión",
                        null,
                        null
                )
        );

        OwnerResult updated = ownerService.update(
                created.id(),
                new UpdateOwnerCommand(
                        "Productor actualizado",
                        null,
                        null,
                        created.version()
                )
        );

        assertThat(updated.version()).isEqualTo(created.version() + 1);

        assertThatThrownBy(() -> ownerService.update(
                created.id(),
                new UpdateOwnerCommand(
                        "Actualización obsoleta",
                        null,
                        null,
                        created.version()
                )
        ))
                .isInstanceOf(OwnerVersionConflictException.class)
                .hasMessageContaining(created.id().toString());
    }

    @Test
    void shouldFailWhenOwnerDoesNotExist() {
        UUID missingOwnerId = UUID.randomUUID();

        assertThatThrownBy(() -> ownerService.findById(missingOwnerId))
                .isInstanceOf(OwnerNotFoundException.class)
                .hasMessageContaining(missingOwnerId.toString());
    }

@Test
void shouldListOwnersOrderedByName() {
    OwnerResult lastOwner = ownerService.create(
            new CreateOwnerCommand(
                    "Productor Zeta",
                    null,
                    null
            )
    );

    OwnerResult firstOwner = ownerService.create(
            new CreateOwnerCommand(
                    "Agricultor Alfa",
                    null,
                    null
            )
    );

    Set<UUID> createdIds = Set.of(
            firstOwner.id(),
            lastOwner.id()
    );

    assertThat(ownerService.findAll())
            .filteredOn(owner -> createdIds.contains(owner.id()))
            .extracting(OwnerResult::name)
            .containsExactly(
                    "Agricultor Alfa",
                    "Productor Zeta"
            );
    }
}
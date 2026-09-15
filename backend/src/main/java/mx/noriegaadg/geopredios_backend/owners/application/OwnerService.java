package mx.noriegaadg.geopredios_backend.owners.application;

import mx.noriegaadg.geopredios_backend.owners.domain.Owner;
import mx.noriegaadg.geopredios_backend.owners.infrastructure.persistence.OwnerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class OwnerService {

    private final OwnerRepository ownerRepository;

    public OwnerService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @Transactional
    public OwnerResult create(CreateOwnerCommand command) {
        Owner owner = new Owner(
                normalizeRequiredName(command.name()),
                normalizeCurp(command.curp()),
                normalizeOptional(command.phone())
        );

        return toResult(ownerRepository.saveAndFlush(owner));
    }

    public List<OwnerResult> findAll() {
        return ownerRepository
                .findAllByOrderByNameAsc()
                .stream()
                .map(this::toResult)
                .toList();
    }

    public OwnerResult findById(UUID ownerId) {
        return toResult(findOwner(ownerId));
    }

    @Transactional
    public OwnerResult update(UUID ownerId, UpdateOwnerCommand command) {
        Owner owner = findOwner(ownerId);

        if (owner.getVersion() != command.version()) {
            throw new OwnerVersionConflictException(
                    ownerId,
                    command.version(),
                    owner.getVersion()
            );
        }

        owner.update(
                normalizeRequiredName(command.name()),
                normalizeCurp(command.curp()),
                normalizeOptional(command.phone())
        );

        return toResult(ownerRepository.saveAndFlush(owner));
    }

    private Owner findOwner(UUID ownerId) {
        return ownerRepository
                .findById(ownerId)
                .orElseThrow(() -> new OwnerNotFoundException(ownerId));
    }

    private OwnerResult toResult(Owner owner) {
        return new OwnerResult(
                owner.getId(),
                owner.getName(),
                owner.getCurp(),
                owner.getPhone(),
                owner.getVersion(),
                owner.getCreatedAt(),
                owner.getUpdatedAt()
        );
    }

    private String normalizeRequiredName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Owner name is required");
        }

        return name.trim();
    }

    private String normalizeCurp(String curp) {
        String normalized = normalizeOptional(curp);

        return normalized == null
                ? null
                : normalized.toUpperCase(Locale.ROOT);
    }

    private String normalizeOptional(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        return value.trim();
    }
}
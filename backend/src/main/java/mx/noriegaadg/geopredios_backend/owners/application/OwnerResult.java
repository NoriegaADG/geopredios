package mx.noriegaadg.geopredios_backend.owners.application;

import java.time.Instant;
import java.util.UUID;

public record OwnerResult(
        UUID id,
        String name,
        String curp,
        String phone,
        long version,
        Instant createdAt,
        Instant updatedAt
) {
}
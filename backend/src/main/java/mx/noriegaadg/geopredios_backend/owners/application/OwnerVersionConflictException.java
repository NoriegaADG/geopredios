package mx.noriegaadg.geopredios_backend.owners.application;

import java.util.UUID;

public class OwnerVersionConflictException extends RuntimeException {

    public OwnerVersionConflictException(
            UUID ownerId,
            long expectedVersion,
            long currentVersion
    ) {
        super(
                "Owner version conflict: "
                        + ownerId
                        + ", expected "
                        + expectedVersion
                        + ", current "
                        + currentVersion
        );
    }
}
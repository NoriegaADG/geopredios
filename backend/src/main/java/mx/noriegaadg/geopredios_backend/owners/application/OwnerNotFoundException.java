package mx.noriegaadg.geopredios_backend.owners.application;

import java.util.UUID;

public class OwnerNotFoundException extends RuntimeException {

    public OwnerNotFoundException(UUID ownerId) {
        super("Owner not found: " + ownerId);
    }
}
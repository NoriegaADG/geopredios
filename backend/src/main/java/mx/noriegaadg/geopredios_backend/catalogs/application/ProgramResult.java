package mx.noriegaadg.geopredios_backend.catalogs.application;

import java.util.UUID;

public record ProgramResult(
        UUID id,
        String code,
        String name
) {
}
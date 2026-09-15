package mx.noriegaadg.geopredios_backend.catalogs.web;

import java.util.UUID;

public record ProgramResponse(
        UUID id,
        String code,
        String name
) {
}
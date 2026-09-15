package mx.noriegaadg.geopredios_backend.catalogs.application;

import java.util.UUID;

public record MunicipalityResult(
        UUID id,
        String stateCode,
        String code,
        String name
) {
}
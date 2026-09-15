package mx.noriegaadg.geopredios_backend.catalogs.web;

import java.util.UUID;

public record MunicipalityResponse(
        UUID id,
        String stateCode,
        String code,
        String name
) {
}
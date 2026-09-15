package mx.noriegaadg.geopredios_backend.catalogs.web;

import mx.noriegaadg.geopredios_backend.catalogs.application.CatalogQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/catalogs")
public class CatalogController {

    private final CatalogQueryService catalogQueryService;

    public CatalogController(CatalogQueryService catalogQueryService) {
        this.catalogQueryService = catalogQueryService;
    }

    @GetMapping("/programs")
    public List<ProgramResponse> findActivePrograms() {
        return catalogQueryService
                .findActivePrograms()
                .stream()
                .map(program -> new ProgramResponse(
                        program.id(),
                        program.code(),
                        program.name()
                ))
                .toList();
    }

    @GetMapping("/municipalities")
    public List<MunicipalityResponse> findActiveMunicipalities() {
        return catalogQueryService
                .findActiveMunicipalities()
                .stream()
                .map(municipality -> new MunicipalityResponse(
                        municipality.id(),
                        municipality.stateCode(),
                        municipality.code(),
                        municipality.name()
                ))
                .toList();
    }
}
package mx.noriegaadg.geopredios_backend.catalogs.application;

import mx.noriegaadg.geopredios_backend.catalogs.infrastructure.persistence.MunicipalityRepository;
import mx.noriegaadg.geopredios_backend.catalogs.infrastructure.persistence.ProgramRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CatalogQueryService {

    private final ProgramRepository programRepository;
    private final MunicipalityRepository municipalityRepository;

    public CatalogQueryService(
            ProgramRepository programRepository,
            MunicipalityRepository municipalityRepository
    ) {
        this.programRepository = programRepository;
        this.municipalityRepository = municipalityRepository;
    }

    public List<ProgramResult> findActivePrograms() {
        return programRepository
                .findAllByActiveTrueOrderByNameAsc()
                .stream()
                .map(program -> new ProgramResult(
                        program.getId(),
                        program.getCode(),
                        program.getName()
                ))
                .toList();
    }

    public List<MunicipalityResult> findActiveMunicipalities() {
        return municipalityRepository
                .findAllByActiveTrueOrderByStateCodeAscNameAsc()
                .stream()
                .map(municipality -> new MunicipalityResult(
                        municipality.getId(),
                        municipality.getStateCode(),
                        municipality.getCode(),
                        municipality.getName()
                ))
                .toList();
    }
}
package mx.noriegaadg.geopredios_backend.catalogs.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CatalogControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnActiveProgramsOrderedByName() throws Exception {
        mockMvc.perform(get("/api/v1/catalogs/programs"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].code").value("DEMO-CULTIVOS"))
                .andExpect(jsonPath("$[0].name")
                        .value("Programa demostrativo de cultivos regionales"))
                .andExpect(jsonPath("$[1].code").value("DEMO-MAIZ"))
                .andExpect(jsonPath("$[1].name")
                        .value("Programa demostrativo de maíz"));
    }

    @Test
    void shouldReturnActiveMunicipalitiesOrderedByStateAndName()
            throws Exception {

        mockMvc.perform(get("/api/v1/catalogs/municipalities"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].stateCode").value("20"))
                .andExpect(jsonPath("$[0].code").value("043"))
                .andExpect(jsonPath("$[0].name")
                        .value("Heroica Ciudad de Juchitán de Zaragoza"))
                .andExpect(jsonPath("$[1].code").value("130"))
                .andExpect(jsonPath("$[1].name")
                        .value("San Dionisio del Mar"))
                .andExpect(jsonPath("$[2].code").value("515"))
                .andExpect(jsonPath("$[2].name")
                        .value("Santo Domingo Tehuantepec"));
    }
}
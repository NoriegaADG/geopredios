package mx.noriegaadg.geopredios_backend.shared.health;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class HealthController {

    private final JdbcTemplate jdbcTemplate;

    @Value("${spring.application.name}")
    private String applicationName;

    public HealthController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/api/v1/health")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "UP");
        response.put("application", applicationName);

        Map<String, Object> database = new LinkedHashMap<>();
        try {
            jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            database.put("status", "UP");
        } catch (Exception e) {
            database.put("status", "DOWN");
        }
        response.put("database", database);

        return ResponseEntity.ok(response);
    }
}
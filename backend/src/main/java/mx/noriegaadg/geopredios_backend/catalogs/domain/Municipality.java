package mx.noriegaadg.geopredios_backend.catalogs.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "municipalities",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_municipalities_state_code_code",
                        columnNames = {"state_code", "code"}
                )
        }
)
public class Municipality {

    @Id
    private UUID id;

    @Column(name = "state_code", nullable = false, length = 3)
    private String stateCode;

    @Column(nullable = false, length = 10)
    private String code;

    @Column(nullable = false, length = 160)
    private String name;

    @Column(nullable = false)
    private boolean active;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected Municipality() {
    }

    public UUID getId() {
        return id;
    }

    public String getStateCode() {
        return stateCode;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return active;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
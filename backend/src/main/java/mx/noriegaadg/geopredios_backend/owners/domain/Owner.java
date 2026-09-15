package mx.noriegaadg.geopredios_backend.owners.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Entity
@Table(name = "owners")
public class Owner {

    @Id
    private UUID id;

    @Column(nullable = false, length = 180)
    private String name;

    @Column(length = 18)
    private String curp;

    @Column(length = 25)
    private String phone;

    @Version
    @Column(nullable = false)
    private long version;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected Owner() {
    }

    public Owner(String name, String curp, String phone) {
        this.name = name;
        this.curp = curp;
        this.phone = phone;
    }

    public void update(String name, String curp, String phone) {
        this.name = name;
        this.curp = curp;
        this.phone = phone;
    }

    @PrePersist
    void beforeInsert() {
        Instant now = currentTimestamp();

        if (id == null) {
            id = UUID.randomUUID();
        }

        if (createdAt == null) {
            createdAt = now;
        }

        updatedAt = now;
    }

    @PreUpdate
    void beforeUpdate() {
        updatedAt = currentTimestamp();
    }

    private Instant currentTimestamp() {
        return Instant.now().truncatedTo(ChronoUnit.MICROS);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCurp() {
        return curp;
    }

    public String getPhone() {
        return phone;
    }

    public long getVersion() {
        return version;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
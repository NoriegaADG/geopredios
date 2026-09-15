-- Propietarios asociados a los predios
CREATE TABLE owners (
                        id UUID PRIMARY KEY,
                        name VARCHAR(180) NOT NULL,
                        curp VARCHAR(18),
                        phone VARCHAR(25),
                        version BIGINT NOT NULL DEFAULT 0,
                        created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
                        updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),

                        CONSTRAINT ck_owners_name_not_blank
                            CHECK (btrim(name) <> ''),
                        CONSTRAINT ck_owners_curp_length
                            CHECK (curp IS NULL OR length(curp) = 18)
);

-- Facilita la búsqueda de propietarios sin distinguir mayúsculas
CREATE INDEX ix_owners_name_lower
    ON owners (lower(name));
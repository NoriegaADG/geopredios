-- Catálogo de programas agrícolas
CREATE TABLE programs (
                          id UUID PRIMARY KEY,
                          code VARCHAR(40) NOT NULL,
                          name VARCHAR(160) NOT NULL,
                          active BOOLEAN NOT NULL DEFAULT true,
                          created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
                          updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),

                          CONSTRAINT uk_programs_code UNIQUE (code),
                          CONSTRAINT ck_programs_code_not_blank
                              CHECK (btrim(code) <> ''),
                          CONSTRAINT ck_programs_name_not_blank
                              CHECK (btrim(name) <> '')
);

-- Facilita búsquedas de programas por nombre sin distinguir mayúsculas
CREATE INDEX ix_programs_name_lower
    ON programs (lower(name));


-- Catálogo territorial de municipios
CREATE TABLE municipalities (
                                id UUID PRIMARY KEY,
                                state_code VARCHAR(3) NOT NULL,
                                code VARCHAR(10) NOT NULL,
                                name VARCHAR(160) NOT NULL,
                                active BOOLEAN NOT NULL DEFAULT true,
                                created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
                                updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),

                                CONSTRAINT uk_municipalities_state_code_code
                                    UNIQUE (state_code, code),
                                CONSTRAINT ck_municipalities_state_code_not_blank
                                    CHECK (btrim(state_code) <> ''),
                                CONSTRAINT ck_municipalities_code_not_blank
                                    CHECK (btrim(code) <> ''),
                                CONSTRAINT ck_municipalities_name_not_blank
                                    CHECK (btrim(name) <> '')
);

-- Facilita búsquedas y ordenamiento de municipios por estado y nombre
CREATE INDEX ix_municipalities_state_name
    ON municipalities (state_code, lower(name));
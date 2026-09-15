-- Predios agrícolas y su geometría oficial
CREATE TABLE parcels (
                         id UUID PRIMARY KEY,
                         parcel_key VARCHAR(80),
                         name VARCHAR(180) NOT NULL,
                         owner_id UUID NOT NULL,
                         program_id UUID NOT NULL,
                         municipality_id UUID NOT NULL,
                         geometry geometry(Polygon, 4326),
                         area_ha NUMERIC(18,6),
                         perimeter_m NUMERIC(18,3),
                         status VARCHAR(30) NOT NULL,
                         version BIGINT NOT NULL DEFAULT 0,
                         created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
                         updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),

                         CONSTRAINT fk_parcels_owner
                             FOREIGN KEY (owner_id)
                                 REFERENCES owners (id)
                                 ON DELETE RESTRICT,

                         CONSTRAINT fk_parcels_program
                             FOREIGN KEY (program_id)
                                 REFERENCES programs (id)
                                 ON DELETE RESTRICT,

                         CONSTRAINT fk_parcels_municipality
                             FOREIGN KEY (municipality_id)
                                 REFERENCES municipalities (id)
                                 ON DELETE RESTRICT,

                         CONSTRAINT ck_parcels_name_not_blank
                             CHECK (btrim(name) <> ''),

                         CONSTRAINT ck_parcels_parcel_key_not_blank
                             CHECK (
                                 parcel_key IS NULL
                                     OR btrim(parcel_key) <> ''
                                 ),

                         CONSTRAINT ck_parcels_status
                             CHECK (
                                 status IN (
                                            'DRAFT',
                                            'READY_TO_VALIDATE',
                                            'VALIDATED',
                                            'ARCHIVED'
                                     )
                                 ),

                         CONSTRAINT ck_parcels_area_non_negative
                             CHECK (
                                 area_ha IS NULL
                                     OR area_ha >= 0
                                 ),

                         CONSTRAINT ck_parcels_perimeter_non_negative
                             CHECK (
                                 perimeter_m IS NULL
                                     OR perimeter_m >= 0
                                 ),

                         CONSTRAINT ck_parcels_geometry_measures
                             CHECK (
                                 (
                                     geometry IS NULL
                                         AND area_ha IS NULL
                                         AND perimeter_m IS NULL
                                     )
                                     OR
                                 (
                                     geometry IS NOT NULL
                                         AND area_ha IS NOT NULL
                                         AND perimeter_m IS NOT NULL
                                     )
                                 ),

                         CONSTRAINT ck_parcels_geometry_valid
                             CHECK (
                                 geometry IS NULL
                                     OR ST_IsValid(geometry)
                                 ),

                         CONSTRAINT ck_parcels_validated_geometry
                             CHECK (
                                 status <> 'VALIDATED'
                                     OR geometry IS NOT NULL
                                 )
);

-- Índices para las relaciones y filtros frecuentes
CREATE INDEX ix_parcels_owner_id
    ON parcels (owner_id);

CREATE INDEX ix_parcels_program_id
    ON parcels (program_id);

CREATE INDEX ix_parcels_municipality_id
    ON parcels (municipality_id);

CREATE INDEX ix_parcels_status
    ON parcels (status);

-- Índice espacial para consultas sobre la geometría
CREATE INDEX ix_parcels_geometry
    ON parcels
    USING GIST (geometry)
    WHERE geometry IS NOT NULL;

-- La clave del predio es única dentro de cada municipio
CREATE UNIQUE INDEX ux_parcels_key
    ON parcels (
                municipality_id,
                upper(btrim(parcel_key))
        )
    WHERE parcel_key IS NOT NULL;
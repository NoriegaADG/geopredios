-- Vértices ordenados utilizados para construir la geometría del predio
CREATE TABLE vertices (
                          id UUID PRIMARY KEY,
                          parcel_id UUID NOT NULL,
                          ordinal INTEGER NOT NULL,
                          latitude NUMERIC(10,7) NOT NULL,
                          longitude NUMERIC(10,7) NOT NULL,
                          source VARCHAR(15) NOT NULL,
                          accuracy_m NUMERIC(10,2),
                          created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
                          updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),

                          CONSTRAINT fk_vertices_parcel
                              FOREIGN KEY (parcel_id)
                                  REFERENCES parcels (id)
                                  ON DELETE CASCADE,

                          CONSTRAINT uk_vertices_parcel_ordinal
                              UNIQUE (parcel_id, ordinal)
                                  DEFERRABLE INITIALLY DEFERRED,

                          CONSTRAINT ck_vertices_ordinal_positive
                              CHECK (ordinal > 0),

                          CONSTRAINT ck_vertices_latitude_range
                              CHECK (latitude BETWEEN -90 AND 90),

                          CONSTRAINT ck_vertices_longitude_range
                              CHECK (longitude BETWEEN -180 AND 180),

                          CONSTRAINT ck_vertices_source
                              CHECK (source IN ('GPS', 'MANUAL')),

                          CONSTRAINT ck_vertices_accuracy_non_negative
                              CHECK (
                                  accuracy_m IS NULL
                                      OR accuracy_m >= 0
                                  )
);
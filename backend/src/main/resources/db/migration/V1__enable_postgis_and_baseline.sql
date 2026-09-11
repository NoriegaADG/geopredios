-- Habilita la extensión PostGIS en la base de datos
CREATE EXTENSION IF NOT EXISTS postgis;
CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- Tabla de línea base para verificar que Flyway corrió correctamente
-- (usada por el endpoint de health en el Paso 7)
CREATE TABLE flyway_baseline_check (
                                       id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                       checked_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

INSERT INTO flyway_baseline_check DEFAULT VALUES;
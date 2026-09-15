-- Programas ficticios para el entorno local y demostrativo
INSERT INTO programs (
    id,
    code,
    name
)
VALUES
    (
        '60000000-0000-0000-0000-000000000001',
        'DEMO-MAIZ',
        'Programa demostrativo de maíz'
    ),
    (
        '60000000-0000-0000-0000-000000000002',
        'DEMO-CULTIVOS',
        'Programa demostrativo de cultivos regionales'
    );


-- Municipios de Oaxaca para el entorno local y demostrativo
INSERT INTO municipalities (
    id,
    state_code,
    code,
    name
)
VALUES
    (
        '61000000-0000-0000-0000-000000000001',
        '20',
        '043',
        'Heroica Ciudad de Juchitán de Zaragoza'
    ),
    (
        '61000000-0000-0000-0000-000000000002',
        '20',
        '130',
        'San Dionisio del Mar'
    ),
    (
        '61000000-0000-0000-0000-000000000003',
        '20',
        '515',
        'Santo Domingo Tehuantepec'
    );
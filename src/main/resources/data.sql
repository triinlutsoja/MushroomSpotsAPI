INSERT INTO mushroom_spot (coordinates, description)
VALUES
  (ST_SetSRID(ST_MakePoint(24.745, 59.437), 4326), 'Kukeseened'),
  (ST_SetSRID(ST_MakePoint(25.013, 58.383), 4326), 'Pilvikud');
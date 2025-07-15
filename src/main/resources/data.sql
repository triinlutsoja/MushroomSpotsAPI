INSERT INTO mushroom_spot (coordinates, description)
VALUES
  (ST_SetSRID(ST_MakePoint(26.745, 57.434), 4326), 'Kukeseened'),
  (ST_SetSRID(ST_MakePoint(24.754, 57.433), 4326), 'Puravikud'),
  (ST_SetSRID(ST_MakePoint(24.742, 59.472), 4326), 'Männiriisikad'),
  (ST_SetSRID(ST_MakePoint(24.735, 59.437), 4326), 'Kärbseseened'),
  (ST_SetSRID(ST_MakePoint(25.013, 58.383), 4326), 'Pilvikud');
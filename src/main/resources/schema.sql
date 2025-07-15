CREATE TABLE IF NOT EXISTS mushroom_spot (
  id BIGSERIAL PRIMARY KEY,
  coordinates GEOMETRY(Point, 4326),
  description TEXT
);
CREATE TABLE mushroom_spot (
  id SERIAL PRIMARY KEY,
  coordinates GEOMETRY(Point, 4326),
  description TEXT
);
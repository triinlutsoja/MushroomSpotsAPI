## Setting up the PostgreSQL + PostGIS database

This project uses a PostgreSQL database with PostGIS extension for spatial data. The database is started via Docker using the provided `docker-compose.yml` file.

### Prerequisites

- Docker installed and running
- DBeaver (or another SQL client, optional)

### How to start the database

1. Start the database container:

   ```bash
   docker compose up -d
   
2. Run the Spring Boot application to auto-create the mushroom_spot table from schema.sql and insert sample data 
   from data.sql.

### How to stop the database (when necessary)

1. To stop the container:

    ```bash
    docker compose down
   
2. To remove all data (if needed):

    ```bash
    docker compose down -v

## Mushroom Spots API

This is a RESTful Spring Boot application for managing mushroom picking spots. Spots are stored as GeoJSON Point features and support basic CRUD operations.

### Features
- Add new mushroom spots with a description and coordinates 
- Retrieve all spots (GeoJSON format)
- Retrieve a single spot by ID 
- Update an existing spot 
- Delete a spot 
- Validation and custom exception handling 

### API endpoints

GET /mushroomspots 
Response: `200 OK` 
Returns a list of GeoJSON Feature objects.

POST /mushroomspots 
Request Body (JSON):
```json
{
"description": "Kuuseriisikad",
  "latitude": 57.434,
  "longitude": 26.745
}
```
Response: `201 Created` 
Returns the created spot in GeoJSON format. 
Error: `400 Bad Request` if input is invalid.

PUT /mushroomspots/{id} 
Request Body (JSON):
```json
{
"description": "Updated description",
  "latitude": 57.434,
  "longitude": 26.745
}
```
Response: `200 OK` 
Returns the updated spot in GeoJSON format. 
Error: `400 Bad Request` if input is invalid. 
Error: `404 Not Found` if the spot with given ID doesn't exist.

DELETE /mushroomspots/{id} 
Response: `204 No Content` 
Error: `404 Not Found` if the spot with given ID doesn't exist.


## Frontend

This project includes a simple frontend web map using [Leaflet.js](https://leafletjs.com/) and [OpenStreetMap]
(https://www.openstreetmap.org/).

- The HTML, JS and CSS files are located in `src/main/resources/static`.
- The map uses Leaflet via a public CDN (cdnjs).
- After launching the application, open `http://localhost:8080` in your browser to view the frontend.

## Tests

This project includes unit tests for the service layer using Junit and Mockito. The location of test classes: `src/test/java/com/triin/mushroomspotapi`
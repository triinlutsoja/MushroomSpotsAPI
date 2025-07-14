## 🐘 Setting up the PostgreSQL + PostGIS database

This project uses a PostgreSQL database with PostGIS extension for spatial data. The database is started via Docker using the provided `docker-compose.yml` file.

### 📦 Prerequisites

- Docker installed and running
- DBeaver (or another SQL client, optional)

### 🚀 How to start the database

1. Start the database container:

   ```bash
   docker compose up -d
   
2. Run the Spring Boot application to auto-create the mushroom_spot table from schema.sql and insert sample data 
   from data.sql.

### 🚀 How to stop the database (when necessary)

1. To stop the container:

    ```bash
    docker compose down
   
2. To remove all data (if needed):

    ```bash
    docker compose down -v
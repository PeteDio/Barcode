# Barcode Database API

This project implements a barcode database API using Spring Boot 3 and MongoDB.

## Requirements

* Docker
* Docker Compose

For instructions on setting up and running the application outside of Docker containers, refer to the [**Standalone Setup Guide**](docs/native_setup.md).
## Running the application

1. Navigate to the root directory of the project.
2. Run the following command to start the application using Docker Compose:

```bash
docker compose up
```

This command will build the Docker images for the application and its dependencies, and then start the containers. The application will be accessible on port 8080.

Alternatively, you can start only the application service:

```bash
docker compose up api
``` 

## Accessing the Application

* **UI:** Access the user interface at: http://localhost:8080/
* **API:** Access the API at: http://localhost:8080/api/items

For a detailed description of the available API endpoints and their usage, please refer to the [**API Endpoints**](./docs/api_endpoints.md) documentation.
# Standalone Setup for Barcode Database API

This document provides instructions on setting up and running the Barcode Database API outside of a Docker container.

## Prerequisites

* Java 17 or later: https://www.oracle.com/java/technologies/downloads/
* Maven: https://maven.apache.org/download.cgi

## Configuration

**Set up MongoDB connection:**
   To connect your application to a MongoDB database, you'll need a MongoDB server running. Here are two options:

1. Install and configure your own MongoDB server:
  * Follow the official MongoDB documentation to install and configure a MongoDB server on your machine: https://www.mongodb.com/docs/manual/installation/

2. Use an online MongoDB service with Compass:
  * Several online MongoDB services provide access to a MongoDB database through a web interface like Compass. Choose a service that meets your needs and follow their instructions for setting up and connecting to your database.
            
**Configure `spring.data.mongodb.uri`:**
   The application uses the `spring.data.mongodb.uri` property to connect to the MongoDB server. You can set this property in a few ways:
   * **Environment variable:** Set an environment variable named `SPRING_DATA_MONGODB_URI` with the connection string to your MongoDB server.
   * **Application properties file:** Find the file named `application.properties` in the src/main/resources of the project directory and add the following line, replacing `<connection_string>` with the actual connection string to your MongoDB server:

        ```properties
        spring.data.mongodb.uri=<connection_string>
        ```

## Building and running the application

1. Navigate to the root directory of the project in your terminal.
2. Run the following command to build the application using Maven:

```bash
mvn clean package
```

3. Run the application using the following command:

```bash
java -jar target/barcode-database-api-*.jar
```
Replace barcode-database-api-*.jar with the actual name of the generated JAR file.
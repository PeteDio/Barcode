## API Endpoints

This document provides a detailed description of the available API endpoints for the Barcode Database API and their usage.

### GET /

**Description:** Retrieves all items available in the system.

**Response:**

* A list of `Item` objects with an HTTP status code of 200 (OK) if items are found.
* An HTTP status code of 204 (NO_CONTENT) if no items are found.

### GET /{id}

**Description:** Fetches an item by its unique identifier.

**Path Parameters:**

* `{id}`: The unique identifier of the item to retrieve.

**Response:**

* A `ResponseEntity` object containing the retrieved item data and the HTTP status code:
    * If the item is found, the status code is set to 200 (OK) and the response body contains the item data.
    * If the item is not found, the status code is set to 404 (NOT_FOUND).

### POST /

**Description:** Creates a new item and persists it to the database.

**Request Body:**

* The item data to be created in JSON format of type `ItemRequestDTO`.

**Response:**

* A `ResponseEntity` object containing:
    * The created item's details as a String representation and the HTTP status code of 201 (CREATED) if the item is created successfully.
    * An error message with the appropriate HTTP status code (BAD_REQUEST or CONFLICT) if the request is invalid or the item already exists.

**Validation:**

* The request body is validated for missing fields and invalid data types.
* Barcodes are validated to ensure they adhere to the expected format.
* Duplicate barcodes and names are checked to prevent conflicts.

### PUT /{id}

**Description:** Updates an existing item identified by its ID.

**Path Parameters:**

* `{id}`: The ID of the item to update.

**Request Body:**

* The updated item data in JSON format of type `Item`. Only provided fields will be updated.

**Response:**

* A `ResponseEntity` object containing:
    * The updated `Item` object with an HTTP status code of 200 (OK) if the update is successful.
    * An HTTP status code of 404 (NOT_FOUND) if an item with the given ID does not exist.

### PUT /{id}/barcodes

**Description:** Updates the barcode(s) of an existing item identified by its ID.

**Path Parameters:**

* `{id}`: The unique identifier of the item to update.

**Request Body:**

* A list of new barcodes for the item in JSON format.

**Response:**

* A `ResponseEntity` object containing a message string and the HTTP status code:
    * If the ID is invalid or the item is not found, the response body is "Item not found" and the status code is set to 400 (BAD_REQUEST).
    * If the provided barcode format is invalid, the response body contains the specific error message and the status code is set to 400 (BAD_REQUEST).
    * If any of the provided barcodes already exist in the system, the response body is "Barcode [existing_barcode] already assigned" and the status code is set to 409 (CONFLICT).
    * If the item is updated successfully, the response body is "Barcodes updated successfully" and the status code is set to 200 (OK).

**Validation:**

* The request body is validated for missing fields and invalid data types.
* Barcodes are validated to ensure they adhere to the expected format.
* Duplicate barcodes are checked to prevent conflicts.

### GET /search/barcode

**Description:** Searches for items by a list of barcodes.

**Request Body:**

* A string barcode to search for in JSON format.

**Response:**

* A `ResponseEntity` object containing:
    * A list of matching items on success (HTTP status 200 OK).
    * An empty body with HTTP status 400 (Bad Request) if no barcodes are provided or the list is empty.
    * An empty body with HTTP status 404 (Not Found) if no items are found for the provided barcodes.

### GET /search/name/

**Description:** Searches for items by name (partial match with case-insensitive search).

**Request Body:**

* The name (or part of the name) to search for in JSON format.

**Response:**

* A `ResponseEntity` object containing:
    * A list of matching items on success (HTTP status 200 OK).
    * An empty body with HTTP status 404 (Not Found) if no items are found for the provided name.

### DELETE /{id}

**Description:** Deletes an item by its ID.

**Path Parameters:**

* `{id}`: The ID of the item to be deleted.

**Response:**

* A `ResponseEntity` object containing:
    * A success message and HTTP status 200 (OK) if the item is deleted successfully.
    * A "No item found" message and HTTP status 404 (NOT_FOUND) if no item with the given ID is found. 

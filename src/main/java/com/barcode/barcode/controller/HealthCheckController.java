package com.barcode.barcode.controller;

import com.barcode.barcode.dto.HealthCheckResponseDTO;
import com.barcode.barcode.service.ItemService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/admin/healthcheck")
public class HealthCheckController {
    private final ItemService itemService;

    public HealthCheckController(ItemService itemService) {
        this.itemService = itemService;
    }

    /**
     * Performs a health check on the application.
     *
     * <p>This endpoint checks the health status of the application, including database connectivity.
     * It retrieves the current count of items in the database.
     *
     * @return A ResponseEntity containing a HealthCheckResponseDTO object with the health status message
     *         and the current item count, and an HTTP status of 200 (OK) if the check is successful.
     *         Returns an HTTP status of 500 (INTERNAL_SERVER_ERROR) if an error occurs during the check.
     */
    @GetMapping
    public ResponseEntity<HealthCheckResponseDTO> healthCheck() {
        try {
            long itemCount = itemService.getItemCount();
            HealthCheckResponseDTO response = new HealthCheckResponseDTO();
            response.setMessage("Database healthy");
            response.setItemCount(itemCount);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            HealthCheckResponseDTO errorResponse = new HealthCheckResponseDTO();
            errorResponse.setMessage("Database health check failed.");
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

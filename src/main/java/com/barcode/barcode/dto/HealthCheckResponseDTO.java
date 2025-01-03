package com.barcode.barcode.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Generates getters, setters, equals, hashCode, toString
@NoArgsConstructor // Generates a no-args constructor
@AllArgsConstructor // Generates an all-args constructor
public class HealthCheckResponseDTO {
    private String message;
    private Long itemCount;
}

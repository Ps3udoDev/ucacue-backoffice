package com.example.demo.categories;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryInDTO {

    @NotBlank(message = "Name is required")
    private String name;

    private String internalCode;

    private String description;

    private Integer idParentWoocommerce;

    private Integer idMoodle;

    private Map<String, Object> image;
}
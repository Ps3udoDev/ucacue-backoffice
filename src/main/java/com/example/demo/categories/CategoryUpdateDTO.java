package com.example.demo.categories;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryUpdateDTO {
    private String name;
    private String internalCode;
    private String description;
    private Integer idParentWoocommerce;
    private Integer idMoodle;
    private Map<String, Object> image;
}

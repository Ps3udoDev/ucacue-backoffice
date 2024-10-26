package com.example.demo.categories;

import com.example.demo.courses.CourseSummaryDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryOutDTO {
    private Long id;
    private String name;
    private String internalCode;
    private String description;
    private Integer idParentWoocommerce;
    private Integer idMoodle;
    private Integer idWooCommerce;
    private Map<String, Object> image;
    private int courseCount;
    private List<CourseSummaryDTO> courses;
    private Instant creationDate;
    private Instant modificationDate;
}

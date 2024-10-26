package com.example.demo.courses;

import com.example.demo.courses.types.DescriptionItem;
import com.example.demo.courses.types.TabContent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseOutDTO {
    private Long id;

    private Integer idCourseMoodle;

    private Integer idErp;

    private String name;

    private Long idCategory;

    private Integer idCategoryWooCommerce;

    private String internalCode;

    private BigDecimal price;

    private Boolean withIVA;

    private Boolean manageStock;

    private Integer stockQuantity;

    private String courseLocation;

    private LocalDate startDate;

    private LocalTime startTime;

    private Map<String, Object> image;

    private List<DescriptionItem> shortDescription;

    private List<TabContent> longDescription;

    private String courseUrlMoodle;

    private Integer idCategoryMoodle;

    private Instant creationDate;

    private Instant modificationDate;
}

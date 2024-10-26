package com.example.demo.courses;

import com.example.demo.courses.types.DescriptionItem;
import com.example.demo.courses.types.TabContent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseInDTO {
    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Category ID is required")
    private Long idCategory;

    @NotNull(message = "Category Woo ID is required")
    private Long idCategoryWooCommerce;

    @NotNull(message = "itemErp ID is required")
    private Long idErp;

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
}

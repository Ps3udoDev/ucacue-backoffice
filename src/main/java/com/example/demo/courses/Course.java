package com.example.demo.courses;

import com.example.demo.categories.Category;
import com.example.demo.courses.types.DescriptionItem;
import com.example.demo.courses.types.TabContent;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "course", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"internal_code"}),
        @UniqueConstraint(columnNames = {"id_erp"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_id_gen")
    @SequenceGenerator(name = "course_id_gen", sequenceName = "course_id_gen", allocationSize = 1)
    private Long id;

    @Column(name = "id_course_moodle", nullable = true)
    private Integer idCourseMoodle;

    @Column(name = "id_woocommerce", nullable = true)
    private Long idWooCommerce;

    @Column(name = "id_erp", nullable = true, unique = true)
    private Integer idErp;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_category", nullable = false)
    @ToString.Exclude
    private Category category;

    @Column(name = "id_category_woocommerce", nullable = true)
    private Integer idCategoryWooCommerce;

    @Column(name = "internal_code", nullable = true, unique = true)
    private String internalCode;

    @Column(name = "price", nullable = true, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "with_iva", nullable = true)
    private Boolean withIVA;

    @Column(name = "manage_stock", nullable = false)
    private Boolean manageStock;

    @Column(name = "stock_quantity", nullable = true)
    private Integer stockQuantity;

    @Column(name = "course_location", nullable = true)
    private String courseLocation;

    @Column(name = "start_date", nullable = true)
    private LocalDate startDate;

    @Column(name = "start_time", nullable = true)
    private LocalTime startTime;

    @Column(name = "image", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> image;

    @Column(name = "short_description", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private List<DescriptionItem> shortDescription;

    @Column(name = "long_description", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private List<TabContent> longDescription;

    @Column(name = "course_url_moodle", nullable = true)
    private String courseUrlMoodle;

    @Column(name = "id_category_moodle", nullable = true)
    private Integer idCategoryMoodle;

    @Column(name = "creation_date")
    private Instant creationDate;

    @Column(name = "modification_date")
    private Instant modificationDate;
}

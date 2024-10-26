package com.example.demo.categories;

import com.example.demo.courses.Course;
import com.example.demo.courses.CourseSummaryDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Entity
@Table(name = "category", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"internal_code"}),
        @UniqueConstraint(columnNames = {"name"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_id_gen")
    @SequenceGenerator(name = "category_id_gen", sequenceName = "category_id_gen", allocationSize = 1)
    private Long id;

    @Column(name = "id_moodle", nullable = true)
    private Integer idMoodle;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "internal_code", nullable = true, unique = true)
    private String internalCode;

    @Column(name = "description", nullable = true, length = 5000)
    private String description;

    @Column(name = "id_parent_woocommerce", nullable = true)
    private Integer idParentWoocommerce;

    @Column(name = "id_woocommerce", nullable = true)
    private Long idWooCommerce;

    @Column(name = "image", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> image;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Course> courses = new ArrayList<>();

    @Column(name = "creation_date")
    private Instant creationDate;

    @Column(name = "modification_date")
    private Instant modificationDate;

    public int getCourseCount() {
        return courses != null ? courses.size() : 0;
    }

    public List<CourseSummaryDTO> getCourseSummaries() {
        return courses != null ? courses.stream()
                .map(course -> new CourseSummaryDTO(course.getId(), course.getName(), course.getIdCourseMoodle()))
                .collect(Collectors.toList()) : new ArrayList<>();
    }
}


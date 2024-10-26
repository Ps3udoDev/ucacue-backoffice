package com.example.demo.courses;

import com.example.demo.categories.Category;
import com.example.demo.categories.CategoryService;
import com.example.demo.wordpress.WooCommerceService;
import com.example.demo.wordpress.course.WooCommerceProductDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;
    private final WooCommerceService wooCommerceService;
    private final CategoryService categoryService;

    public List<CourseOutDTO> findAll() {
        return courseRepository.findAll()
                .stream()
                .map(this::mapAndSetDetails)
                .collect(Collectors.toList());
    }

    public Course findOne(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }

    public CourseOutDTO create(CourseInDTO courseInDTO) {
        Course course = modelMapper.map(courseInDTO, Course.class);
        course.setCreationDate(Instant.now());
        course.setModificationDate(Instant.now());
        course.setCategory(categoryService.findOne(courseInDTO.getIdCategory()));
        return saveWithWooCommerce(course);
    }

    public CourseOutDTO update(Long id, CourseInDTO courseInDTO) {
        Course course = findOne(id);
        modelMapper.map(courseInDTO, course);
        course.setModificationDate(Instant.now());

        try {
            wooCommerceService.updateWooCommerceProduct(course.getIdWooCommerce().intValue(), course);
            return saveAndMap(course);
        } catch (Exception e) {
            throw new RuntimeException("Error updating course: " + e.getMessage(), e);
        }
    }

    public void delete(Long courseId) {
        Course course = findOne(courseId);
        try {
            wooCommerceService.deleteWooCommerceProduct(course.getIdWooCommerce().intValue());
            courseRepository.deleteById(courseId);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting course: " + e.getMessage(), e);
        }
    }

    private CourseOutDTO saveWithWooCommerce(Course course) {
        try {
            Integer wooCommerceProductId = wooCommerceService.createWooCommerceProduct(course);
            course.setIdWooCommerce(wooCommerceProductId.longValue());
            return saveAndMap(course);
        } catch (Exception e) {
            throw new RuntimeException("Error saving course: " + e.getMessage(), e);
        }
    }

    private CourseOutDTO saveAndMap(Course course) {
        course = courseRepository.save(course);
        return mapAndSetDetails(course);
    }

    private CourseOutDTO mapAndSetDetails(Course course) {
        return modelMapper.map(course, CourseOutDTO.class);
    }
}

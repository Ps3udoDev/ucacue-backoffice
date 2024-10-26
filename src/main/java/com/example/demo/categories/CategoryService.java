package com.example.demo.categories;

import com.example.demo.wordpress.WooCommerceService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final WooCommerceService wooCommerceService;
    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    public List<CategoryOutDTO> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(this::mapAndSetDetails)
                .collect(Collectors.toList());
    }

    public Category findOne(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    public CategoryOutDTO create(CategoryInDTO categoryInDTO) {
        Category category = modelMapper.map(categoryInDTO, Category.class);
        category.setCreationDate(Instant.now());
        category.setModificationDate(Instant.now());
        return saveWithWooCommerce(category);
    }

    public CategoryOutDTO update(Long id, CategoryUpdateDTO categoryUpdateDTO) {
        Category category = findOne(id);
        modelMapper.map(categoryUpdateDTO, category);
        category.setModificationDate(Instant.now());

        try {
            wooCommerceService.updateWooCommerceCategory(category.getIdWooCommerce().intValue(), category);
            return saveAndMap(category);
        } catch (Exception e) {
            throw new RuntimeException("Error updating category: " + e.getMessage(), e);
        }
    }

    public void delete(Long categoryId) {
        Category category = findOne(categoryId);
        try {
            logger.info("Deleting WooCommerce category with ID: {}", category.getIdWooCommerce());
            wooCommerceService.deleteWooCommerceCategory(category.getIdWooCommerce().intValue());
            categoryRepository.deleteById(categoryId);
        } catch (Exception e) {
            logger.error("Error deleting category: {}", e.getMessage(), e);
            throw new RuntimeException("Error deleting category: " + e.getMessage(), e);
        }
    }

    private CategoryOutDTO saveWithWooCommerce(Category category) {
        try {
            Integer wooCommerceCategoryId = wooCommerceService.createWooCommerceCategory(category);
            category.setIdWooCommerce(wooCommerceCategoryId.longValue());
            return saveAndMap(category);
        } catch (Exception e) {
            throw new RuntimeException("Error saving category: " + e.getMessage(), e);
        }
    }

    private CategoryOutDTO saveAndMap(Category category) {
        category = categoryRepository.save(category);
        return mapAndSetDetails(category);
    }

    private CategoryOutDTO mapAndSetDetails(Category category) {
        CategoryOutDTO categoryOutDTO = modelMapper.map(category, CategoryOutDTO.class);
        categoryOutDTO.setCourseCount(category.getCourseCount());
        categoryOutDTO.setCourses(category.getCourseSummaries());
        return categoryOutDTO;
    }
}

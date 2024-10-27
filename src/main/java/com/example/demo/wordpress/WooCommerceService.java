package com.example.demo.wordpress;

import com.example.demo.categories.Category;
import com.example.demo.courses.Course;
import com.example.demo.exception.WooCommerceException;
import com.example.demo.wordpress.category.WooCommerceCategoryDTO;
import com.example.demo.wordpress.category.WooCommerceCategoryMapper;
import com.example.demo.wordpress.course.WooCommerceProductDTO;
import com.example.demo.wordpress.course.WooCommerceProductMapper;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class WooCommerceService {
    private final WooCommerceClient wooCommerceClient;
    private final WooCommerceCategoryMapper wooCommerceCategoryMapper;
    private final WooCommerceProductMapper wooCommerceProductMapper;
    private static final Logger logger = LoggerFactory.getLogger(WooCommerceService.class);

    @Autowired
    public WooCommerceService(WooCommerceClient wooCommerceClient, WooCommerceProductMapper wooCommerceProductMapper, WooCommerceCategoryMapper wooCommerceCategoryMapper) {
        this.wooCommerceClient = wooCommerceClient;
        this.wooCommerceProductMapper = wooCommerceProductMapper;
        this.wooCommerceCategoryMapper = wooCommerceCategoryMapper;
    }

    public Integer createWooCommerceCategory(Category category) {
        WooCommerceCategoryDTO wooCommerceCategoryDTO = wooCommerceCategoryMapper.toWooCommerceDTO(category);
        System.out.println("esta es la categoria ques e create"+wooCommerceCategoryDTO);
        System.out.println("esta es la categoria "+category);



        ResponseEntity<JsonNode> response = wooCommerceClient.createCategory(wooCommerceCategoryDTO);
        System.out.println("response de create"+response);
        if (response.getStatusCode().is2xxSuccessful()) {
            JsonNode responseBody = response.getBody();
            assert responseBody != null;
            return responseBody.get("id").asInt();
        } else {
            JsonNode errorResponseBody = response.getBody();
            throw new WooCommerceException("Failed to create category in WooCommerce", errorResponseBody);
        }
    }

    public void updateWooCommerceCategory(Integer wooCategoryId, Category category) {
        WooCommerceCategoryDTO wooCommerceCategoryDTO = wooCommerceCategoryMapper.toWooCommerceDTO(category);
        System.out.println("esta es la categoria ques e mapeo"+wooCommerceCategoryDTO);
        ResponseEntity<JsonNode> response = wooCommerceClient.updateCategoryInWooCommerce(wooCategoryId.longValue(), wooCommerceCategoryDTO);

        if (!response.getStatusCode().is2xxSuccessful()) {
            JsonNode errorResponseBody = response.getBody();
            throw new WooCommerceException("Failed to update category in WooCommerce", errorResponseBody);
        }
    }

    public void deleteWooCommerceCategory(Integer wooCategoryId) {
        wooCommerceClient.deleteCategory(wooCategoryId.longValue());
    }

    public Integer createWooCommerceProduct(Course course) {
        WooCommerceProductDTO wooCommerceProductDTO = wooCommerceProductMapper.toWooCommerceDTO(course);
        ResponseEntity<JsonNode> response = wooCommerceClient.createProduct(wooCommerceProductDTO);

        if (response.getStatusCode().is2xxSuccessful()) {
            JsonNode responseBody = response.getBody();
            assert responseBody != null;
            return responseBody.get("id").asInt();
        } else {
            JsonNode errorResponseBody = response.getBody();
            throw new WooCommerceException("Failed to create product in WooCommerce", errorResponseBody);
        }
    }

    public void updateWooCommerceProduct(Integer wooProductId, Course course) {
        WooCommerceProductDTO wooCommerceProductDTO = wooCommerceProductMapper.toWooCommerceDTO(course);
        ResponseEntity<JsonNode> response = wooCommerceClient.updateProduct(wooProductId.longValue(), wooCommerceProductDTO);

        if (!response.getStatusCode().is2xxSuccessful()) {
            JsonNode errorResponseBody = response.getBody();
            throw new WooCommerceException("Failed to update product in WooCommerce", errorResponseBody);
        }
    }

    public void deleteWooCommerceProduct(Integer wooProductId) {
        wooCommerceClient.deleteProduct(wooProductId.longValue());
    }
}

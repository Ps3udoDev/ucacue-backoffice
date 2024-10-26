package com.example.demo.wordpress;

import com.example.demo.wordpress.category.WooCommerceCategoryDTO;
import com.example.demo.wordpress.course.WooCommerceProductDTO;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Component
public class WooCommerceClient {
    @Value("${woo.api.url}")
    private String wooApiUrl;

    @Value("${woo.consumer.key}")
    private String consumerKey;

    @Value("${woo.consumer.secret}")
    private String consumerSecret;

    private final RestTemplate restTemplate;

    public WooCommerceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Basic " + Base64.getEncoder().encodeToString((consumerKey + ":" + consumerSecret).getBytes()));
        return headers;
    }

    private void validateResponse(ResponseEntity<?> response) {
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Failed request to WooCommerce: " + response.getStatusCode());
        }
    }

    public WooCommerceCategoryDTO getCategoryFromWooCommerce(Long idWooCommerce) {
        String url = String.format("%s/products/categories/%d", wooApiUrl, idWooCommerce);
        HttpEntity<Void> entity = new HttpEntity<>(createHeaders());

        ResponseEntity<WooCommerceCategoryDTO> response = restTemplate.exchange(url, HttpMethod.GET, entity, WooCommerceCategoryDTO.class);
        validateResponse(response);

        return response.getBody();
    }

    public ResponseEntity<JsonNode> createCategory(WooCommerceCategoryDTO wooCommerceCategoryDTO) {
        String url = wooApiUrl + "/products/categories";
        HttpEntity<WooCommerceCategoryDTO> request = new HttpEntity<>(wooCommerceCategoryDTO, createHeaders());

        ResponseEntity<JsonNode> response = restTemplate.exchange(url, HttpMethod.POST, request, JsonNode.class);
        validateResponse(response);
        return response;
    }

    public ResponseEntity<JsonNode> updateCategoryInWooCommerce(Long idWooCommerce, WooCommerceCategoryDTO wooCommerceCategoryDTO) {
        String url = String.format("%s/products/categories/%d", wooApiUrl, idWooCommerce);
        HttpEntity<WooCommerceCategoryDTO> entity = new HttpEntity<>(wooCommerceCategoryDTO, createHeaders());

        ResponseEntity<JsonNode> response = restTemplate.exchange(url, HttpMethod.PUT, entity, JsonNode.class);
        validateResponse(response);
        return response;
    }

    public void deleteCategory(Long categoryId) {
        String url = wooApiUrl + "/products/categories/" + categoryId + "?force=true";
        HttpEntity<Void> request = new HttpEntity<>(createHeaders());

        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.DELETE, request, Void.class);
        validateResponse(response);
    }

    public ResponseEntity<JsonNode> createProduct(WooCommerceProductDTO productDTO) {
        String url = wooApiUrl + "/products";
        HttpEntity<WooCommerceProductDTO> request = new HttpEntity<>(productDTO, createHeaders());

        ResponseEntity<JsonNode> response = restTemplate.exchange(url, HttpMethod.POST, request, JsonNode.class);
        validateResponse(response);
        return response;
    }

    public ResponseEntity<JsonNode> updateProduct(Long productId, WooCommerceProductDTO productDTO) {
        String url = String.format("%s/products/%d", wooApiUrl, productId);
        HttpEntity<WooCommerceProductDTO> entity = new HttpEntity<>(productDTO, createHeaders());

        ResponseEntity<JsonNode> response = restTemplate.exchange(url, HttpMethod.PUT, entity, JsonNode.class);
        validateResponse(response);
        return response;
    }

    public void deleteProduct(Long productId) {
        String url = String.format("%s/products/%d?force=true", wooApiUrl, productId);
        HttpEntity<Void> request = new HttpEntity<>(createHeaders());

        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.DELETE, request, Void.class);
        validateResponse(response);
    }
}

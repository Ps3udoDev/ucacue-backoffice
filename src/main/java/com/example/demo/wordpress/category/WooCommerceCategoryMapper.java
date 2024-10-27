package com.example.demo.wordpress.category;

import com.example.demo.categories.Category;
import com.example.demo.wordpress.category.WooCommerceCategoryDTO;
import com.example.demo.wordpress.common.ImageDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class WooCommerceCategoryMapper {
    public WooCommerceCategoryDTO toWooCommerceDTO(Category category) {
        WooCommerceCategoryDTO categoryDTO = new WooCommerceCategoryDTO();
        categoryDTO.setName(category.getName());
        categoryDTO.setSlug(category.getInternalCode());
        categoryDTO.setDescription(category.getDescription());

        if (category.getImage() != null) {
            Integer imageId = (Integer) category.getImage().get("id");
            String imageUrl = category.getImage().get("src").toString();
            categoryDTO.setImage(new ImageDTO(imageId, imageUrl));
        }

        return categoryDTO;

    }
}

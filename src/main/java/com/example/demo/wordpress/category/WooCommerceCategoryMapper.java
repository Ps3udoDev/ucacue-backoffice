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

        List<ImageDTO> images = new ArrayList<>();
        if(category.getImage() != null && category.getImage().get("id") != null){
            Integer imageId = (Integer) category.getImage().get("id");
            String imageUrl = category.getImage().get("src").toString();
            images.add(new ImageDTO(imageId, imageUrl));
        } else if (category.getImage() != null) {
            String imageUrl = category.getImage().get("src").toString();
            images.add(new ImageDTO(null, imageUrl));
        }

        categoryDTO.setImages(images);

        return categoryDTO;

    }
}

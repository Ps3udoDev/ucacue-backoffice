package com.example.demo.wordpress.category;

import com.example.demo.wordpress.common.ImageDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WooCommerceCategoryDTO {
    private String name;
    private String slug;
    private String description;
    private ImageDTO image;
}

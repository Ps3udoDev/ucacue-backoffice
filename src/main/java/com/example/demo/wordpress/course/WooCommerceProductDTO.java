package com.example.demo.wordpress.course;

import com.example.demo.wordpress.course.types.CategoryIdDTO;
import com.example.demo.wordpress.common.ImageDTO;
import com.example.demo.wordpress.common.MetaDataDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WooCommerceProductDTO {
    private String name;
    private String slug;
    private String short_description;
    private String regular_price;
    private Boolean manage_stock;
    private Integer stock_quantity;
    private List<CategoryIdDTO> categories;
    private List<ImageDTO> images;
    private List<MetaDataDTO> meta_data;
    private String tax_status;
    private String tax_class;
}

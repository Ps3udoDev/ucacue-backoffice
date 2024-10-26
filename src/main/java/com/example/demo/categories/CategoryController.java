package com.example.demo.categories;

import com.example.demo.common.CommonResponse;
import com.example.demo.wordpress.WooCommerceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final WooCommerceService wooCommerceService;

    @GetMapping
    public ResponseEntity<List<CategoryOutDTO>> categories() {
        List<CategoryOutDTO> categories = categoryService.findAll();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryOutDTO> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.findOne(id);
        CategoryOutDTO categoryOutDTO = new ModelMapper().map(category, CategoryOutDTO.class);

        return new ResponseEntity<>(categoryOutDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CommonResponse<CategoryOutDTO>> createOneCategory(@RequestBody @Valid CategoryInDTO categoryInDTO) {
        CategoryOutDTO createdCategory = categoryService.create(categoryInDTO);

        CommonResponse<CategoryOutDTO> response = CommonResponse.<CategoryOutDTO>builder()
                .status("SUCCESS")
                .recordId(createdCategory.getId())
                .data(createdCategory)
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<CategoryOutDTO>> updateCategory(@PathVariable Long id, @RequestBody @Valid CategoryUpdateDTO categoryUpdateDTO) {
        CategoryOutDTO updatedCategory = categoryService.update(id, categoryUpdateDTO);

        CommonResponse<CategoryOutDTO> response = CommonResponse.<CategoryOutDTO>builder()
                .status("SUCCESS")
                .recordId(updatedCategory.getId())
                .data(updatedCategory)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<Void>> deleteCategory(@PathVariable Long id) {
        categoryService.delete(id);
        CommonResponse<Void> response = CommonResponse.<Void>builder()
                .status("SUCCESS")
                .recordId(null)
                .data(null)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

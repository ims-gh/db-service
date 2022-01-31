package com.ims.dbservice.controllers;

import com.ims.dbservice.dto.CategoryDTO;
import com.ims.dbservice.exceptions.ResponseHandler;
import com.ims.dbservice.models.Category;
import com.ims.dbservice.services.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/categories")
    public ResponseEntity<Object> getAllCategories(){
        return ResponseHandler
                .builder()
                .status(HttpStatus.OK)
                .data(categoryService.getAllCategories())
                .build();
    }

    @GetMapping("/category/{name}")
    public ResponseEntity<Object> getCategoryByName(@PathVariable String name){
        return ResponseHandler
                .builder()
                .status(HttpStatus.FOUND)
                .data(categoryService.getCategoryByCategoryName(name))
                .build();
    }

    @PostMapping("/category")
    public ResponseEntity<Object> addNewCategory(@RequestBody Category category){
        categoryService.addNewCategory(category);
        return ResponseHandler
                .builder()
                .status(HttpStatus.CREATED)
                .message("Category successfully created")
                .build();
    }

    @PutMapping("category/{name}")
    public ResponseEntity<Object> updateCategory(@PathVariable("name") String name,
                                                 @RequestBody CategoryDTO categoryDTO){
        categoryService.updateCategory(name, categoryDTO);
        return ResponseHandler
                .builder()
                .status(HttpStatus.OK)
                .message("Category successfully updated.")
                .build();
    }

    @DeleteMapping("/category/{name}")
    public ResponseEntity<Object> deleteCategory(@PathVariable String name){
        categoryService.deleteCategory(name);
        return ResponseHandler
                .builder()
                .status(HttpStatus.OK)
                .message("Category successfully deleted.")
                .build();
    }
}

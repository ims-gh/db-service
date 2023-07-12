package com.ims.ordermanagement.controllers;

import com.ims.ordermanagement.exceptions.ResponseHandler;
import com.ims.ordermanagement.models.dto.ProductDTO;
import com.ims.ordermanagement.models.entities.Product;
import com.ims.ordermanagement.services.impl.ProductServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "v1")
public class ProductController {

    @Autowired
    private ProductServiceImpl productServiceImpl;

    @ApiOperation(value = "Get all products")
    @GetMapping("/products")
    public ResponseEntity<Object> getAllProducts(){
        return ResponseHandler
                .builder()
                .status(HttpStatus.OK)
                .data(productServiceImpl.getAllProducts())
                .build();
    }

    @ApiOperation(value = "Get product by slug")
    @GetMapping("/product")
    public ResponseEntity<Object> getProductBySlug(@RequestParam String slug){
        return ResponseHandler
                .builder()
                .status(HttpStatus.FOUND)
                .data(productServiceImpl.getProductBySlug(slug))
                .build();
    }

    @ApiOperation(value = "Get products by categories")
    @GetMapping("/product/category")
    public ResponseEntity<Object> getProductByCategory(@RequestParam List<String> category){
        return ResponseHandler
                .builder()
                .status(HttpStatus.FOUND)
                .data(productServiceImpl.getProductByCategory(category))
                .build();
    }


    @ApiOperation(value = "Add a new product")
    @PostMapping("/product")
    public ResponseEntity<Object> addNewProduct(@RequestBody Product product){
        productServiceImpl.addNewProduct(product);
        return ResponseHandler
                .builder()
                .status(HttpStatus.CREATED)
                .data(product)
                .message("Product successfully created")
                .build();
    }

    @ApiOperation(value = "Update an existing product")
    @PatchMapping("/product/{slug}")
    public ResponseEntity<Object> updateProduct(@PathVariable("slug") String slug,
                                                 @RequestBody ProductDTO productDTO){
        productServiceImpl.updateProduct(slug, productDTO);
        return ResponseHandler
                .builder()
                .status(HttpStatus.OK)
                .data(productDTO)
                .message("Product successfully updated.")
                .build();
    }

    @ApiOperation(value = "Delete an existing product")
    @DeleteMapping("/product")
    public ResponseEntity<Object> deleteProduct(@RequestParam String slug){
        productServiceImpl.deleteProduct(slug);
        return ResponseHandler
                .builder()
                .status(HttpStatus.OK)
                .message("Product successfully deleted.")
                .build();
    }
}

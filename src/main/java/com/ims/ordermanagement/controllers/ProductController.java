package com.ims.ordermanagement.controllers;

import com.ims.ordermanagement.models.dto.ProductDTO;
import com.ims.ordermanagement.exceptions.ResponseHandler;
import com.ims.ordermanagement.models.entities.Product;
import com.ims.ordermanagement.services.impl.ProductServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "v1")
@AllArgsConstructor
public class ProductController {

    private final ProductServiceImpl productServiceImpl;

    @GetMapping("/products")
    public ResponseEntity<Object> getAllProducts(){
        return ResponseHandler
                .builder()
                .status(HttpStatus.OK)
                .data(productServiceImpl.getAllProducts())
                .build();
    }

    @GetMapping("/product")
    public ResponseEntity<Object> getProductByName(@RequestParam String slug){
        return ResponseHandler
                .builder()
                .status(HttpStatus.FOUND)
                .data(productServiceImpl.getProductBySlug(slug))
                .build();
    }

    @GetMapping("/product/category")
    public ResponseEntity<Object> getProductByCategory(@RequestParam List<String> category){
        return ResponseHandler
                .builder()
                .status(HttpStatus.FOUND)
                .data(productServiceImpl.getProductByCategory(category))
                .build();
    }



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

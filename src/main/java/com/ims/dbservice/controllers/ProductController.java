package com.ims.dbservice.controllers;

import com.ims.dbservice.dto.ProductDTO;
import com.ims.dbservice.exceptions.ResponseHandler;
import com.ims.dbservice.models.Product;
import com.ims.dbservice.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "v1")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<Object> getAllProducts(){
        return ResponseHandler
                .builder()
                .status(HttpStatus.OK)
                .data(productService.getAllProducts())
                .build();
    }

    @GetMapping("/product")
    public ResponseEntity<Object> getProductByName(@RequestParam String slug){
        return ResponseHandler
                .builder()
                .status(HttpStatus.FOUND)
                .data(productService.getProductBySlug(slug))
                .build();
    }

    @PostMapping("/product")
    public ResponseEntity<Object> addNewProduct(@RequestBody Product product){
        productService.addNewProduct(product);
        return ResponseHandler
                .builder()
                .status(HttpStatus.CREATED)
                .message("Product successfully created")
                .build();
    }

    @PatchMapping("/product")
    public ResponseEntity<Object> updateProduct(@RequestParam("slug") String slug,
                                                 @RequestBody ProductDTO productDTO){
        productService.updateProduct(slug, productDTO);
        return ResponseHandler
                .builder()
                .status(HttpStatus.OK)
                .message("Product successfully updated.")
                .build();
    }

    @DeleteMapping("/product")
    public ResponseEntity<Object> deleteProduct(@RequestParam String slug){
        productService.deleteProduct(slug);
        return ResponseHandler
                .builder()
                .status(HttpStatus.OK)
                .message("Product successfully deleted.")
                .build();
    }
}

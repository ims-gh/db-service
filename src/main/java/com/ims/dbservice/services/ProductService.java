package com.ims.dbservice.services;

import com.ims.dbservice.models.dto.ProductDTO;
import com.ims.dbservice.models.entities.Product;

import java.util.List;

public interface ProductService extends DBService {

    public List<Product> getAllProducts();

    public Product getProductBySlug(String slug);

    public void addNewProduct(Product product);

    public void updateProduct(String slug, ProductDTO productDTO);

    public void deleteProduct(String slug);

}

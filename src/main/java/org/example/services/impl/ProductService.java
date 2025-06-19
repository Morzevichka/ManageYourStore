package org.example.services.impl;

import org.example.dto.ProductSalesDto;
import org.example.entity.Category;
import org.example.entity.Product;

import java.time.LocalDate;
import java.util.List;

public interface ProductService {
    Product findProduct(Long id);

    void saveProduct(Product product);

    void deleteProduct(Product product);

    void updateProduct(Product product);

    List<Product> findAllProducts();

    int countProducts();

    List<Product> findAllProductWithCategory();

    List<Product> findAllWithCategoryAndOrderItems();

    Product findProductByName(String name);

    List<ProductSalesDto> productSalesBetween(LocalDate var1, LocalDate var2);
}

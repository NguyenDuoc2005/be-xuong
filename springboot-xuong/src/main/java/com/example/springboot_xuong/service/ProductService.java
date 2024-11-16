package com.example.springboot_xuong.service;

import com.example.springboot_xuong.entity.Product;
import com.example.springboot_xuong.request.ProductRequest;
import com.example.springboot_xuong.response.ProductResponse;
import org.springframework.data.domain.Page;

public interface ProductService {

    //gọi product
    Page<ProductResponse> findAllProductDetails(ProductRequest productRequest);

    //  Xóa Product theo id product
    void deleteProductById(Long id);

    //  thêm mới product
    Product addProduct(ProductRequest productRequest);

    // update product
    Product updateProduct(Long productId, Long brandId, ProductRequest productRequest);

    // tìm kiếm product và lọc
    Page<ProductResponse> searchProducts(ProductRequest productRequest);

}

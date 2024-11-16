package com.example.springboot_xuong.controller;

import com.example.springboot_xuong.request.ProductRequest;
import com.example.springboot_xuong.response.ProductResponse;
import com.example.springboot_xuong.entity.Product;
import com.example.springboot_xuong.service.impl.ProductBrandServiceImpl;
import com.example.springboot_xuong.service.impl.ProductServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductRestController {

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private ProductBrandServiceImpl productBrandService;

    // API lấy danh sách sản phẩm với phân trang
    @GetMapping
    public PagedModel<ProductResponse> getAllProducts(ProductRequest productRequest) {
        Page<ProductResponse> productPage = productService.findAllProductDetails(productRequest);

        PagedModel<ProductResponse> pagedModel = PagedModel.of(productPage.getContent(),
                new PagedModel.PageMetadata(productPage.getSize(), productPage.getNumber(), productPage.getTotalElements()));

        return pagedModel;
    }

    //delete productbrand theo id productid và brandid
    @DeleteMapping("/{productId}/{brandId}")
    public ResponseEntity<String> deleteProduct(@PathVariable("productId") Long productId,
                                                @PathVariable("brandId") Long brandId) {
        boolean isDeleted = productBrandService.deleteProductBrandById(productId, brandId);
        if (isDeleted) {
            return ResponseEntity.ok("Xóa thành công với ID product: " + productId + "& IDBrand: " + brandId);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
    }

    // API thêm mới product
    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@Valid @RequestBody ProductRequest productRequest) {
        try {
            Product product = productService.addProduct(productRequest);
            return ResponseEntity.ok("Thêm sản phẩm thành công với ID: " + product.getId());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // API sửa sản phẩm
    @PutMapping("/{id}/{brandId}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") Long id,
                                           @PathVariable("brandId") Long brandId,
                                           @Valid @RequestBody ProductRequest productRequest) {
        try {
            // Cập nhật sản phẩm với id và brandId
            Product updatedProduct = productService.updateProduct(id, brandId, productRequest);
            return ResponseEntity.ok("Sửa Sản phẩm thành công với ID: " + updatedProduct.getId());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //search
    @PostMapping("/search")
    public PagedModel<ProductResponse> searchProducts(@RequestBody ProductRequest productRequest) {
        Page<ProductResponse> productPage = productService.searchProducts(productRequest);
        PagedModel<ProductResponse> pagedModel = PagedModel.of(productPage.getContent(),
                new PagedModel.PageMetadata(productPage.getSize(), productPage.getNumber(), productPage.getTotalElements()));
        return pagedModel;
    }

}
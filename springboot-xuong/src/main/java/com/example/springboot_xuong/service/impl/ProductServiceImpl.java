package com.example.springboot_xuong.service.impl;

import com.example.springboot_xuong.request.ProductRequest;
import com.example.springboot_xuong.response.ProductResponse;
import com.example.springboot_xuong.entity.*;
import com.example.springboot_xuong.repository.*;
import com.example.springboot_xuong.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductBrandRepository productBrandRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Autowired
    private StatusRepository statusRepository;

    //gọi product
    @Override
    public Page<ProductResponse> findAllProductDetails(ProductRequest productRequest) {
        Pageable pageable = PageRequest.of(productRequest.getPage(), productRequest.getSize());
        return this.productRepository.findAllProductDetails(pageable);
    }

    //  Xóa Product theo id product
    @Override
    public void deleteProductById(Long id) {
        this.productRepository.deleteById(id);
    }

    //  thêm mới product
    @Override
    public Product addProduct(ProductRequest productRequest) {
        // Kiểm tra xem sản phẩm đã tồn tại chưa
        Product existingProduct = productRepository.findAll().stream()
                .filter(product -> product.getProductName().equals(productRequest.getProductName()) &&
                        product.getColor().equals(productRequest.getColor()) &&
                        product.getQuantity().equals(productRequest.getQuantity()) &&
                        product.getSellPrice().equals(productRequest.getSellPrice()) &&
                        product.getOriginPrice().equals(productRequest.getOriginPrice()) &&
                        product.getSubCategory().getSubCateName().equals(productRequest.getSubCategory()) &&
                        product.getStatus().getStatusName().equals(productRequest.getStatusName()))
                .findFirst()
                .orElse(null);

        Long productId;

        if (existingProduct != null) {
            productId = existingProduct.getId();
        } else {
            // Lấy brand từ DB
            Brand brand = brandRepository.findByBrandName(productRequest.getBrandName())
                    .orElseThrow(() -> new RuntimeException("Brand not found"));

            // Lấy subcategory từ DB
            SubCategory subCategory = subCategoryRepository.findBySubCateName(productRequest.getSubCategory())
                    .orElseThrow(() -> new RuntimeException("SubCategory not found"));

            // Lấy Status từ DB
            Status status = statusRepository.findByStatusName(productRequest.getStatusName())
                    .orElseThrow(() -> new RuntimeException("Status not found"));

            // Tạo đối tượng Product
            Product product = new Product();
            product.setProductName(productRequest.getProductName());
            product.setColor(productRequest.getColor());
            product.setQuantity(productRequest.getQuantity());
            product.setSellPrice(productRequest.getSellPrice());
            product.setOriginPrice(productRequest.getOriginPrice());
            product.setSubCategory(subCategory); // Set SubCategory vào Product
            product.setStatus(status);

            Product savedProduct = productRepository.save(product);
            productId = savedProduct.getId();
        }
        // Lấy brand từ DB
        Brand brand = brandRepository.findByBrandName(productRequest.getBrandName())
                .orElseThrow(() -> new RuntimeException("Brand not found"));

        // Tạo đối tượng ProductBrand và thiết lập quan hệ giữa Product và Brand
        ProductBrand productBrand = new ProductBrand();
        productBrand.setProductId(productId);
        productBrand.setBrandId(brand.getId());

        // Thêm ProductBrand vào danh sách productBrands của Product
        productBrandRepository.save(productBrand);

        return existingProduct != null ? existingProduct : productRepository.findById(productId).orElse(null);
    }

    // update product
    @Override
    public Product updateProduct(Long productId, Long brandId, ProductRequest productRequest) {

        // Kiểm tra xem sản phẩm có tồn tại không
        Product existingProduct = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

        // Lấy subcategory từ DB
        SubCategory subCategory = subCategoryRepository.findBySubCateName(productRequest.getSubCategory())
                .orElseThrow(() -> new RuntimeException("SubCategory not found"));

        // Lấy Status từ DB
        Status status = statusRepository.findByStatusName(productRequest.getStatusName())
                .orElseThrow(() -> new RuntimeException("Status not found"));

        // Cập nhật thông tin sản phẩm
        existingProduct.setProductName(productRequest.getProductName());
        existingProduct.setColor(productRequest.getColor());
        existingProduct.setQuantity(productRequest.getQuantity());
        existingProduct.setSellPrice(productRequest.getSellPrice());
        existingProduct.setOriginPrice(productRequest.getOriginPrice());
        existingProduct.setStatus(status);
        existingProduct.setSubCategory(subCategory);

        // Lấy brand từ DB
        Brand brand = brandRepository.findByBrandName(productRequest.getBrandName())
                .orElseThrow(() -> new RuntimeException("Brand not found"));

        // check và xóa
        ProductBrandId productBrandId = new ProductBrandId(productId, brandId);
        if (productBrandRepository.existsById(productBrandId)) {
            productBrandRepository.deleteByProductIdAndBrandId(productId, brandId);
            // Thêm ProductBrand mới
            ProductBrand productBrand = new ProductBrand();
            productBrand.setProductId(productId);
            productBrand.setBrandId(brand.getId());
            productBrandRepository.save(productBrand);
        }
        return productRepository.save(existingProduct);
    }

    // tìm kiếm product và lọc
    @Override
    public Page<ProductResponse> searchProducts(ProductRequest productRequest) {
        Pageable pageable = PageRequest.of(productRequest.getPage(), productRequest.getSize());
        return productRepository.searchProducts(productRequest, pageable);
    }

}

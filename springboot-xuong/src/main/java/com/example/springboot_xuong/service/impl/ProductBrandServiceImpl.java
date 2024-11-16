package com.example.springboot_xuong.service.impl;

import com.example.springboot_xuong.entity.ProductBrandId;
import com.example.springboot_xuong.repository.ProductBrandRepository;
import com.example.springboot_xuong.repository.ProductRepository;
import com.example.springboot_xuong.service.ProductBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductBrandServiceImpl implements ProductBrandService {

    @Autowired
    private ProductBrandRepository productBrandRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public boolean deleteProductBrandById(Long productId, Long brandId) {
        ProductBrandId productBrandId = new ProductBrandId(productId, brandId);
        // Kiểm tra sự tồn tại trước khi xóa
        if (productBrandRepository.existsById(productBrandId)) {
            productBrandRepository.deleteByProductIdAndBrandId(productId, brandId);
            if (productBrandRepository.existsByProductId(productId)) {
                System.out.println("tồn tại ");
            } else {
                productRepository.deleteById(productId);
            }
            return true;
        } else {
            return false;
        }
    }

}

package com.example.springboot_xuong.entity;

import java.io.Serializable;

public class ProductBrandId implements Serializable {

    private Long productId;

    private Long brandId;

    public ProductBrandId() {
    }

    public ProductBrandId(Long productId, Long brandId) {
        this.productId = productId;
        this.brandId = brandId;
    }

}

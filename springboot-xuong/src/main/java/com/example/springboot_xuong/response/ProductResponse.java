package com.example.springboot_xuong.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductResponse {

    private Long idProduct;

    private Long idBrand;

    private String productName;

    private String brandName;

    private String subCategory;

    private Double sellPrice;

    private String statusName;

    private String color;

    private Long quantity;

    private Double originPrice;

    public ProductResponse(Long idProduct, Long idBrand, String productName, String brandName, String subCategory,
                           Double sellPrice, String statusName, String color, Long quantity, Double originPrice) {
        this.idProduct = idProduct;
        this.idBrand = idBrand;
        this.productName = productName;
        this.brandName = brandName;
        this.subCategory = subCategory;
        this.sellPrice = sellPrice;
        this.statusName = statusName;
        this.color = color;
        this.quantity = quantity;
        this.originPrice = originPrice;
    }

}

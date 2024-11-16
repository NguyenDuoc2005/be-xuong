package com.example.springboot_xuong.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductRequest {

    private Long idProduct;

    private Long idBrand;

    @NotNull(message = "Tên sản phẩm không được null")
    @NotBlank(message = "Tên sản phẩm không được để trống")
    @Size(max = 255, message = "Tên sản phẩm không được vượt quá 255 ký tự")
    private String productName;

    @NotNull(message = "Tên hãng không được null")
    @NotBlank(message = "Tên hãng không được để trống")
    @Size(max = 255, message = "Tên hãng không được vượt quá 255 ký tự")
    private String brandName;

    @NotNull(message = "Danh mục con không được để null")
    @NotBlank(message = "Danh mục con không được để trống")
    private String subCategory;

    @NotNull(message = "Giá bán không được null")
    @Min(value = 1, message = "Giá bán phải lớn hơn 0")
    private Double sellPrice;

    @NotBlank(message = "Trạng thái không được để trống")
    @NotNull(message = "Trạng thái không được để null")
    private String statusName;

    @NotNull(message = "Màu sắc không được null")
    @NotBlank(message = "Màu sắc không được để trống")
    @Size(max = 255, message = "Màu sắc không được vượt quá 255 ký tự")
    private String color;

    @NotNull(message = "Số lượng không được null")
    @Min(value = 0, message = "Số lượng phải lớn hơn hoặc bằng 0")
    private Long quantity;

    @NotNull(message = "Giá gốc không được null")
    @Min(value = 1, message = "Giá gốc phải lớn hơn 0")
    private Double originPrice;

    private String name;

    private Double price;

    private Long brand;

    private Long category;

    private Long status;

    private int page = 0;

    private int size = 3;
}
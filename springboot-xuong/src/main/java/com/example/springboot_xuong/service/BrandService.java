package com.example.springboot_xuong.service;

import com.example.springboot_xuong.repository.BrandProjection;

import java.util.List;

public interface BrandService {
    List<BrandProjection> getAllBrands();
}

package com.example.springboot_xuong.service;

import com.example.springboot_xuong.repository.SubCategoryProjection;

import java.util.List;

public interface SubCategoryService {
    List<SubCategoryProjection> getAllSubCategory();
}

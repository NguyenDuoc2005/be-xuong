package com.example.springboot_xuong.service.impl;

import com.example.springboot_xuong.repository.SubCategoryProjection;
import com.example.springboot_xuong.repository.SubCategoryRepository;
import com.example.springboot_xuong.service.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubCategoryServiceImpl implements SubCategoryService {

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Override
    public List<SubCategoryProjection> getAllSubCategory() {
        return subCategoryRepository.findAllBy();
    }

}

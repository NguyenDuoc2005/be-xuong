package com.example.springboot_xuong.controller;

import com.example.springboot_xuong.repository.SubCategoryProjection;
import com.example.springboot_xuong.service.impl.SubCategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("api/subcategory")
@RestController
public class SubCategoryRestController {

    @Autowired
    private SubCategoryServiceImpl subCategoryService;

    @GetMapping
    public List<SubCategoryProjection> getAllSubCategory(){
        return subCategoryService.getAllSubCategory();
    }

}

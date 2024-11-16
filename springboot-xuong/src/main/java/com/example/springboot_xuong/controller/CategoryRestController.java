package com.example.springboot_xuong.controller;

import com.example.springboot_xuong.entity.Category;
import com.example.springboot_xuong.service.impl.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryRestController {

    @Autowired
    private CategoryServiceImpl categoryService;

    @GetMapping
    public List<Category> getAllCategory() {
        return categoryService.getALlCategory();
    }

}

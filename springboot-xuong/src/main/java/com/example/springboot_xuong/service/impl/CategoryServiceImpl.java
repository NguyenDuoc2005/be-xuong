package com.example.springboot_xuong.service.impl;

import com.example.springboot_xuong.entity.Category;
import com.example.springboot_xuong.repository.CategoryRepository;
import com.example.springboot_xuong.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getALlCategory() {
        return categoryRepository.findAll();
    }

}

package com.example.springboot_xuong.controller;

import com.example.springboot_xuong.repository.BrandProjection;
import com.example.springboot_xuong.service.impl.BrandServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/brands")
public class BrandRestController {

    @Autowired
    private BrandServiceImpl brandServiceImpl;

    @GetMapping
    public List<BrandProjection> getAllBrands() {
        return brandServiceImpl.getAllBrands();
    }

}

package com.example.springboot_xuong.service.impl;

import com.example.springboot_xuong.repository.BrandProjection;
import com.example.springboot_xuong.repository.BrandRepository;
import com.example.springboot_xuong.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandRepository brandRepository;

    @Override
    public List<BrandProjection> getAllBrands() {
        return brandRepository.findAllBy();
    }

}

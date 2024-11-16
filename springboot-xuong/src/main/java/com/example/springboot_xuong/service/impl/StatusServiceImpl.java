package com.example.springboot_xuong.service.impl;

import com.example.springboot_xuong.entity.Status;
import com.example.springboot_xuong.repository.StatusRepository;
import com.example.springboot_xuong.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusServiceImpl implements StatusService {

    @Autowired
    private StatusRepository statusRepository;

    @Override
    public List<Status> getAllStatuses() {
        return statusRepository.findAll();
    }

}

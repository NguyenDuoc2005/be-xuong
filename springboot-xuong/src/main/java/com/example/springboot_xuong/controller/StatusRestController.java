package com.example.springboot_xuong.controller;

import com.example.springboot_xuong.entity.Status;
import com.example.springboot_xuong.service.impl.StatusServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RequestMapping("/api/status")
@RestController
public class StatusRestController {

    @Autowired
    private StatusServiceImpl statusService;

    @GetMapping
    public List<Status> getAllStatus(){
        return statusService.getAllStatuses();
    }

}

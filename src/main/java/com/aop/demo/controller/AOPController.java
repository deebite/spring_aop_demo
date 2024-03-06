package com.aop.demo.controller;

import com.aop.demo.dto.UserDetailsDto;
import com.aop.demo.service.AOPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AOPController {

    @Autowired
    private AOPService aopService;

    @PostMapping("/user")
    public UserDetailsDto getUserDetails(@Validated @RequestBody UserDetailsDto userDetailsDto){
        return aopService.getUserDetails(userDetailsDto);
    }
}

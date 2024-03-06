package com.aop.demo.service.impl;

import com.aop.demo.dto.UserDetailsDto;
import com.aop.demo.service.AOPService;
import org.springframework.stereotype.Service;

@Service
public class AOPServiceImpl implements AOPService {
    @Override
    public UserDetailsDto getUserDetails(UserDetailsDto userDetailsDto) {
        UserDetailsDto user = new UserDetailsDto();
        user.setUserName(userDetailsDto.getUserName());
        user.setUserNumber(userDetailsDto.getUserNumber());
        user.setUserEmail(userDetailsDto.getUserEmail());
        user.setUserCountry(userDetailsDto.getUserCountry());
        return user;
    }
}

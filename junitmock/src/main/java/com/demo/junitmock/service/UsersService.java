package com.demo.junitmock.service;

import com.demo.junitmock.dto.UsersDto;
import com.demo.junitmock.exception.ValidationException;
import org.springframework.stereotype.Component;

import java.util.List;


public interface UsersService {

    UsersDto saveUser(UsersDto usersDto) throws ValidationException;

    void deleteUser(Integer userId);

    UsersDto findByLogin(String login);

    List<UsersDto> findAll();
}
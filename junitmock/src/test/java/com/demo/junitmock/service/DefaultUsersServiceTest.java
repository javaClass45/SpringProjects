package com.demo.junitmock.service;

import com.demo.junitmock.dto.UsersDto;
import com.demo.junitmock.entity.Users;
import com.demo.junitmock.exception.ValidationException;
import com.demo.junitmock.repository.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static com.demo.junitmock.prototype.UsersPrototypeTest.aUser;
import static com.demo.junitmock.prototype.UsersPrototypeTest.aUserDTO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DefaultUsersServiceTest {

    private  UsersRepository usersRepository;
    private  UsersConverter usersConverter;
    private UsersService usersService;

    @BeforeEach
    void setUp() {
        usersRepository = mock(UsersRepository.class);
        usersConverter = new UsersConverter();
        usersService = new DefaultUsersService(usersRepository, usersConverter);

    }


    @Test
    void saveUser() throws ValidationException {
        when(usersRepository.save(any())).thenReturn(aUser());
        UsersDto createdUser = usersService.saveUser(aUserDTO());
        assertThat(createdUser).isNotNull();
        assertThat(createdUser.getName()).isEqualTo(aUserDTO().getName());
    }

    @Test
    void saveUserThrowsValidationExceptionWhenLoginIsNull() {
        UsersDto usersDto = aUserDTO();
        usersDto.setLogin("");
        assertThrows(ValidationException.class,
                () -> usersService.saveUser(usersDto),
                "Login is empty");
    }

    @Test
    void deleteUser() {

    }

    @Test
    void findByLogin() {
        when(usersRepository.findByLogin(eq("test_login"))).thenReturn(aUser());
        UsersDto foundUser = usersService.findByLogin("test_login");
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getLogin()).isEqualTo("test_login");
    }

    @Test
    void findAll() {
        when(usersRepository.findAll()).thenReturn(Collections.singletonList(aUser()));

    }
}
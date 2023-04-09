package com.mockitobase.petproject.service;

import com.mockitobase.petproject.model.User;
import com.mockitobase.petproject.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS) // .PER_METHOD for static BeforeAll/AfterAll
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {


    private static final User PEDRO = new User("Pedro", "123");

    @Mock
    User user;
    UserRepository userRepository;
    private UserService userService;

    @BeforeAll
    void init() {
        System.out.println("BeforeAll: " + this );// in static context - "this" NOT WORKED!!!
    }


    @BeforeEach
    void prepare() {
        System.out.println("BeforeEach: " + this);
        userService = new UserService(user, userRepository);
    }

    @Test
    void usersEmptyIfNoUserAdded() {
        System.out.println("Test1: " + this);
        var users = userService.getAll();
        assertTrue(users.isEmpty(), () -> "User list should by empty");

    }

    @Test
    void usersSizeIfUserAdded() {
        System.out.println("Test2: " + this);
        userService.add(new User());
        userService.add(new User());

        var users = userService.getAll();
        assertEquals(2, users.size());
    }

    @Test
    void loginSuccessIfExist() {
        userService.add(PEDRO);
        Optional<User> mayBeUser = userService.login("Pedro","123");
        assertTrue(mayBeUser.isPresent());
        mayBeUser.ifPresent(user -> assertEquals(PEDRO, user));
    }

    @Test
    void logicFailIfPassIsNotCorrect() {
        userService.add(PEDRO);
        Optional<User> mayBeUser = userService.login("Pedro","111");
        assertTrue(mayBeUser.isEmpty());
        mayBeUser.ifPresent(user -> assertEquals(PEDRO, user));
    }

    @Test
    void logicFailIfUserDoesNotExist() {
        userService.add(PEDRO);
        Optional<User> mayBeUser = userService.login("notUser","notPass");
        assertTrue(mayBeUser.isEmpty());
        mayBeUser.ifPresent(user -> assertEquals(PEDRO, user));
    }



    @AfterEach
    void deleteDataDB() {

        System.out.println("After each: " + this);
    }

    @AfterAll
    void initAfter() {
        System.out.println("AfterAll: " + this );
    }
}
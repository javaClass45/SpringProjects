package com.mockitobase.petproject.service;

import com.mockitobase.petproject.model.User;
import com.mockitobase.petproject.paramresolver.UserServiceParamResolver;
import com.mockitobase.petproject.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;


@Tag("fast")
@Tag("user")
//@TestMethodOrder(MethodOrderer.Random.class)// anti-pattern
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // .PER_METHOD for static BeforeAll/AfterAll
@ExtendWith(MockitoExtension.class)
@ExtendWith({
        UserServiceParamResolver.class
})
public class UserServiceTest {


    private static final User PEDRO = new User(1,"Pedro", "123");
    private static final User TOMAS = new User(2,"Tomas", "321");

    @Mock
    private User user;
    private UserRepository userRepository;
    private UserService userService;

    public UserServiceTest(TestInfo testInfo) {
        System.out.println(testInfo);
    }

    @BeforeAll
    void init() {
        System.out.println("BeforeAll: " + this);// in static context - "this" NOT WORKED!!!
    }


    @BeforeEach
    void prepare(UserService userService) {
        System.out.println("BeforeEach: " + this);
//        userService = new UserService(user, userRepository);
        this.userService = userService;

    }

    @Test
    @DisplayName("users will be empty if no user added") //для красоты отбражения в списке выполненных
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
        assertThat(users).hasSize(2);

        assertEquals(2, users.size());
    }



    @Test
    void throwExceptionIfUsernameOrPasswordIsNull() {
//  1--------------------
        /*try {
            userService.login(null, "dummy");
            fail("login should throw exception on NULL username");
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }*/
//  2--------------------
//        assertThrows(IllegalArgumentException.class,
//                () -> userService.login(null, "dummy"));

//  3--------------------
        assertAll(
                () -> assertThrows(IllegalArgumentException.class,
                        () -> userService.login(null, "dummy")),
                () -> assertThrows(IllegalArgumentException.class,
                        () -> userService.login("dummy", null))
        );
    }



    @AfterEach
    void deleteDataDB() {

        System.out.println("After each: " + this);
    }

    @AfterAll
    void initAfter() {
        System.out.println("AfterAll: " + this);
    }

    @Nested
    @DisplayName("group test for user login functionality")
    @Tag("login")
    class LoginTest {
        @Test
        void loginFailIfPassIsNotCorrect() {
            userService.add(PEDRO);
            Optional<User> mayBeUser = userService.login("Pedro", "111");
            assertTrue(mayBeUser.isEmpty());
            mayBeUser.ifPresent(user -> assertEquals(PEDRO, user));
        }

        @Test
//        @Tag("login")
        void loginFailIfUserDoesNotExist() {
            userService.add(PEDRO);
            Optional<User> mayBeUser = userService.login("notUser", "notPass");
            assertTrue(mayBeUser.isEmpty());
            mayBeUser.ifPresent(user -> assertEquals(PEDRO, user));
        }

        @Test
//        @Tag("login")
        void loginFailIfPasswordIsNotCorrect() {
            userService.add(PEDRO, TOMAS);
            Map<Integer, User> users = userService.getAllConvertedById();

            assertAll(
                    () -> assertThat(users).containsKeys(PEDRO.getId(), TOMAS.getId()),
                    () -> assertThat(users).containsValues(TOMAS, PEDRO)
            );
        }

        @Test
//        @Tag("login")
        void loginSuccessIfExist() {
            userService.add(PEDRO);
            Optional<User> mayBeUser = userService.login("Pedro", "123");
            assertTrue(mayBeUser.isPresent());
            mayBeUser.ifPresent(user -> assertEquals(PEDRO, user));
        }

    }



}
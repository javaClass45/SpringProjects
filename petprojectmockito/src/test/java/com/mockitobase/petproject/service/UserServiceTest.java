package com.mockitobase.petproject.service;


import com.mockitobase.petproject.dao.UserDao;
import com.mockitobase.petproject.extention.ConditionalExtension;
import com.mockitobase.petproject.extention.GlobalExtension;
import com.mockitobase.petproject.extention.PostProcessingExtension;
import com.mockitobase.petproject.model.User;
import com.mockitobase.petproject.paramresolver.UserServiceParamResolver;
import com.mockitobase.petproject.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@Tag("fast")
@Tag("user")
//@TestMethodOrder(MethodOrderer.Random.class)// anti-pattern
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // .PER_METHOD for static BeforeAll/AfterAll
@ExtendWith(MockitoExtension.class)
@ExtendWith({
        UserServiceParamResolver.class,
        GlobalExtension.class,
        PostProcessingExtension.class,
        ConditionalExtension.class,
        MockitoExtension.class
//        ThrowableExtension.class
})
public class UserServiceTest {


    private static final User PEDRO = new User(1, "Pedro", "123");
    private static final User TOMAS = new User(2, "Tomas", "321");

    @Captor
    private ArgumentCaptor<Integer> argumentCaptor;

    @Mock(lenient = true)
    private User user;
    private UserDao userDao;


    @InjectMocks
//    private UserRepository userRepository;
    private UserService userService;

    public UserServiceTest(TestInfo testInfo) {
        System.out.println(testInfo);
    }

    @BeforeAll
    void init() {
        System.out.println("BeforeAll: " + this);// in static context - "this" NOT WORKED!!!
    }


    //    @BeforeEach // JUnit5
//    void prepare(UserService userService) {
//        System.out.println("BeforeEach: " + this);
////        userService = new UserService(user, userRepository);
//        this.userService = userService;
//
//    }

//    @BeforeEach //Mock
//    void prepare() {
//        System.out.println("BeforeEach: " + this);
//        this.userDao = mock(UserDao.class);
//        this.userService = new UserService(userDao);
//
//    }
//
//

    @BeforeEach
        //Spy
    void prepare() {
        System.out.println("BeforeEach: " + this);

        doReturn(true).when(userDao).delete(PEDRO.getId());
//        mock(UserDao.class, withSettings().lenient());
        this.userDao = spy(new UserDao());            // after @Mock & @InjectMocks
//        this.userService = new UserService(userDao);

    }


    @Test
    void throwExceptionIfDatabaseIsNotAvailable() {
        doThrow(RuntimeException.class).when(userDao).delete(PEDRO.getId());
        assertThrows(RuntimeException.class, () -> {
            userService.delete(PEDRO.getId());
        });
    }



    @Test
    void shouldDeleteExistedUser() {
        userService.add(PEDRO);
        doReturn(true).when(userDao).delete(PEDRO.getId());//Stub
        var deleteResult = userService.delete(PEDRO.getId());
        assertThat(deleteResult).isTrue();

    }

    @Test
    void shouldDeleteExistedUserAlter() {
        userService.add(PEDRO);
        doReturn(true).when(userDao).delete(PEDRO.getId());// for SPY version
//        doReturn(true).when(userDao).delete(any());
//        when(userDao.delete(PEDRO.getId())) // при использ-ии SPY тут будет Exception
//                .thenReturn(true)
//                .thenReturn(false);
        var deleteResult = userService.delete(PEDRO.getId());
        System.out.println(userService.delete(PEDRO.getId()));
        System.out.println(userService.delete(PEDRO.getId()));

        var argumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(userDao, times(3))
                .delete(argumentCaptor.capture());

        assertThat(argumentCaptor.getValue()).isEqualTo(1);// 1 - PEDRO's userId
        assertThat(deleteResult).isTrue();

    }


    @Test
    @DisplayName("users will be empty if no user added")
        //для красоты отбражения в списке выполненных
    void usersEmptyIfNoUserAdded() throws IOException {
        if (true) {
            throw new RuntimeException(); // passed
//            throw new IOException(); //   failed
        }
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
//        @Disabled("reason")
        void loginFailIfUserDoesNotExist() {
            userService.add(PEDRO);
            Optional<User> mayBeUser = userService.login("notUser", "notPass");
            assertTrue(mayBeUser.isEmpty());
            mayBeUser.ifPresent(user -> assertEquals(PEDRO, user));
        }

        @Test
//        @Tag("login")
//        @RepeatedTest(value = 3, name = RepeatedTest.SHORT_DISPLAY_NAME)
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


        @ParameterizedTest(name = "{arguments} test")
        @DisplayName("login param test")
//    @ArgumentsSource(ArgumentsProvider.class)// под него нужно писаь свой провайдер
//        @NullSource
//        @EmptySource
//    @NullAndEmptySource
//        @ValueSource(strings = {
//                "Pedro", "Tomas"
//        })
        // самый часто используемый @MethodSource
        @MethodSource("com.mockitobase.petproject.service.UserServiceTest#getArgForLoginTest")
//        @CsvFileSource(resources = "/login-test-data.csv", delimiter = ',', numLinesToSkip = 1)
        void loginParamTest(String username, String password, Optional<User> user) {
            userService.add(PEDRO, TOMAS);

            var maybeUser = userService.login(username, password);
            assertThat(maybeUser).isEqualTo(user);
        }


        @Test
        @Timeout(value = 200, unit = TimeUnit.MILLISECONDS)
        void checkLoginFunctionalityPerformance() {
            var result = assertTimeout(Duration.ofMillis(200L), () -> {
                Thread.sleep(300L);// error on 103ms
                return userService.login("Pedro", "123");
            });
        }

    }

    private static Stream<Arguments> getArgForLoginTest() {
        return Stream.of(
                Arguments.of("Pedro", "123", Optional.of(PEDRO)),
                Arguments.of("Tomas", "321", Optional.of(TOMAS)),
                Arguments.of("Tomas", "dummy", Optional.empty()),
                Arguments.of("dummy", "123", Optional.empty())
        );
    }

}
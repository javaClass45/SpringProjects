package com.mockitobase.petproject.mockclasspackage;

import com.mockitobase.petproject.model.Product;
import com.mockitobase.petproject.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MockTest {

//    @BeforeEach
//    public void init() {
//        MockitoAnnotations.openMocks(this);
//    }


    @Mock
    User user;

    @Spy
    Product item = Mockito.spy(new Product("MockTestItem", 10));
    User testUser = Mockito.spy(new User("TestMockUser", "123"));

    @Test
    public void whenNotUseMockAnnotation_thenCorrect() {
        User mockUser = mock(User.class);
        mockUser.setName("Neo");
    }







    @Test //todo "not worked"
    public void whenGetAny() {
//        User mockUser = mock(User.class);
//        when(mockUser.getItems(anyInt()).thenReturn("anyString");
//        assertEquals("Trinity", mockUser.getItems());
    }








}
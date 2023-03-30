package com.mockitobase.petproject.mockclasspackage;

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

    @Mock
    User user;

    @Spy
    List<String> mocklist = new ArrayList<String>();

    @Test
    public void whenNotUseMockAnnotation_thenCorrect() {
        User mockUser = mock(User.class);
        mockUser.setName("Neo");
    }

    @Test
    public void whenNotUseMockAnnotation() {
        user.setName("Trinity");
    }

    @Test
    public void whenMockAnnotation() {
        mocklist.add("Neo");
        mocklist.add("Trinity");
        //эти методы будут работать!
    }

    @Test
    public void whenMockListAnnotation() {
        //создаем правило: вернуть 10 при вызове метода size
        Mockito.when(mocklist.size() ).thenReturn(10);

        //тут вызывается метод и вернет 10!!
        assertEquals(10, mocklist.size());
    }

    @Test //todo "not worked"
    public void whenGetAny() {
        User mockUser = mock(User.class);
        when(mockUser.getItems(anyInt()).thenReturn("anyString");
        assertEquals("Trinity", mockUser.getItems());
    }

    @Test
    public void doReturnGetAny() {
        doReturn("Neo").when(mocklist).get(anyInt());
        assertEquals("Neo", mocklist.get(10));
    }


    @Test
    public void whenAddUserName() {
        Mockito.doReturn("Kirill").when(user).getName();
        assertEquals("Kirill", user.getName());
    }

    @Test
    public void whenAddMockUserName() {
        User mockUser = mock(User.class);
        when(mockUser.getName()).thenReturn("Kirill");
        assertEquals("Kirill", mockUser.getName());
    }


    @Test
    public void whenThrowExceptionIAE() {
        when(user.throwException()).thenThrow(new IllegalArgumentException());
        assertThrows(IllegalArgumentException.class, () -> {
            user.throwException();
        });
    }


    @Test
    public void whenThrowException() {
        doThrow(IllegalArgumentException.class).when(user).throwException();
        assertThrows(IllegalArgumentException.class, () -> {
            user.throwException();
        });
    }


}
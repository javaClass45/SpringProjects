package com.mockitobase.petproject.mockclasspackage;

import com.mockitobase.petproject.model.Product;
import com.mockitobase.petproject.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.atLeastOnce;

@ExtendWith(MockitoExtension.class)
class MockTest {

//    @BeforeEach
//    public void init() {
//        MockitoAnnotations.openMocks(this);
//    }


    @Mock
    User user;
    List<String> mockList = mock(List.class);

    @Spy
    Product item = Mockito.spy(new Product("MockTestItem", 10));
    User testUser = Mockito.spy(new User("TestMockUser", "123"));


    @Test
    public void whenNotUseMockAnnotation_thenCorrect() {
        User mockUser = mock(User.class);
        mockUser.setName("Neo");
    }


    @Test
    public void whenMockAnnotation() {
        when(mockList.get(6)).thenReturn("TestPass");
        //вызов метода
        String name = mockList.get(6);
        //проверяем вызывался ли метод
        Mockito.verify(mockList, atLeastOnce()).get(6);
    }


    @Test
    public void whenMockAssertions() {
        //задаем поведение метода (нужно только для демонстрации)
        Mockito.when(mockList.size()).thenThrow(IllegalStateException.class);

        //проверяем бросится ли IllegalStateException при вызове метода size
        assertThrows(IllegalStateException.class, () -> mockList.size());
    }

    @Test
    void givenStaticMethodWithNoArgs() {
        try (MockedStatic<StaticUtils> utils = Mockito.mockStatic(StaticUtils.class)) {
            utils.when(StaticUtils::name).thenReturn("Hellou!!");
            assertEquals("Hellou!!", StaticUtils.name());
        }
    }



}
package com.mockitobase.petproject.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductTest {


    @Mock
    Product product;

    @Spy
    Product item = Mockito.spy(new Product("MockTestItem", 10));

    @Test
    void getId() {
        Mockito.when(product.getId()).thenReturn(111);
        assertEquals(111, product.getId());

    }

    @Test
    void setId() {
        item.setId(anyInt());

    }

    @Test
    void getItem() {
        Mockito.doReturn("TestName").when(product).getItem();
        assertEquals("TestName", product.getItem());
    }

    @Test
    void setItem() {
       item.setItem("TestName");
    }

    @Test
    void getQuantity() {
        Mockito.doReturn(15).when(product).getQuantity();
        assertEquals(15, product.getQuantity());
    }

    @Test
    void setQuantity() {
        item.setQuantity(111);
    }

    @Test
    void getUser() {
        User mockUser = mock(User.class);
        when(mockUser.getName()).thenReturn("TestName");
        assertEquals("TestName", mockUser.getName());
    }

    @Test
    void setUser() {
        product.setUser(mock(User.class));
    }
}
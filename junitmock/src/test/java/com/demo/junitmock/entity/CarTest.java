package com.demo.junitmock.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CarTest {

    @Mock //без этой аннотации setUp() не сработает
            Car car;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

    }


    @Test
    void connectTest() {
        assertThrows(Exception.class, () -> {
            car.connect();
        });

    }

    @Test
    void getOwner() {
        when(car.getOwner()).thenReturn("NAL");
        assertEquals("NAL", car.getOwner());// а потому что -> when/then
    }
//Ожидалось, что будет выброшено исключение java.io.FileNotFoundException, но ничего не выброшено.

    @Test
    void verification() {
        when(car.getOwner()).thenReturn("NAL");
        assertEquals("NAL", car.getOwner());// а потому что -> when/then
        verify(car).getOwner();
    }




    @Test
    void create() {


    }

    @Test
    void testInt() {

    }

    @Test
    void getManufacturer() {

    }
}
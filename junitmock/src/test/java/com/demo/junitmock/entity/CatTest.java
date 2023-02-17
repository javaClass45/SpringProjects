package com.demo.junitmock.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CatTest {

    @Mock //без этой аннотации setUp() не сработает
            Cat cat;


    @BeforeEach
    void setUp() {

        cat = Mockito.mock(Cat.class);// инициализация Моковой глобальной переменной
        cat.setNumber("AE7908CH");
        cat.setYear(LocalDate.of(2008, 05, 01));
        cat.setOwner("I am");
        cat.setManufacturer("Zaz");
    }

    @Test
    void create() {

//        assertEquals(LocalDate.of(2008,05,01), car.getYear());

    }


    @Test
    void testInt() {
//        Car car = Mockito.mock(Car.class);
        when(cat.testInt(4)).thenReturn(10);
        assertEquals(10, cat.testInt(4));
    }

    @Test
    void getOwner() {
//        Car car = Mockito.mock(Car.class);
        when(cat.getOwner()).thenReturn("NAL");
        assertEquals("NAL", cat.getOwner());// а потому что -> when/then
    }


    @Test
    void verification() {
//        Car car = Mockito.mock(Car.class);
        cat.getManufacturer();
        verify(cat).getManufacturer();//проверили вызывался ли метод
    }



    @Test
    void verificationTestInt() {
//        Car car = Mockito.mock(Car.class);
        cat.testInt(4);
        verify(cat).testInt(4);//проверили вызывался ли метод
    }


    @Test
    void verificationGet() {
//        Car car = Mockito.mock(Car.class);
        assertEquals(null, cat.getOwner());
        verify(cat).getOwner();//interview
    }






}
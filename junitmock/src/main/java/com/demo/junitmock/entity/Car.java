package com.demo.junitmock.entity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;

public class Car {

    private String manufacturer;
    private String number;
    private LocalDate year;
    private String owner;

    public Car(String manufacturer, String number, LocalDate year, String owner) {
        this.manufacturer = manufacturer;
        this.number = number;
        this.year = year;
        this.owner = owner;
    }


    public int testInt(int a) {
        return a + 4;
    }

    public String  connect() throws IOException {
            throw new FileNotFoundException("connection error");
    }





    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDate getYear() {
        return year;
    }

    public void setYear(LocalDate year) {
        this.year = year;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}

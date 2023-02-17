package com.demo.junitmock.entity;

import java.time.LocalDate;

public class Cat {

    private String manufacturer;
    private String number;
    private LocalDate year;
    private String owner;

    public Cat(String manufacturer, String number, LocalDate year, String owner) {
        this.manufacturer = manufacturer;
        this.number = number;
        this.year = year;
        this.owner = owner;
    }


    public int testInt(int a) {
        return a + 4;
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

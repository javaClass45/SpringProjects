package com.mockitobase.petproject.mockclasspackage;




public class User {

   private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Exception throwException() {
        throw new IllegalArgumentException();
    }

}

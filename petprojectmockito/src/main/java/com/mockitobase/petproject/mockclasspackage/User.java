package com.mockitobase.petproject.mockclasspackage;




public class User {

   private String name;

    private String[] array = {"Neo","Trinity","Morpheus","Niobe"};


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Exception throwException() {
        throw new IllegalArgumentException();
    }

    public String[] getArray() {
        return array;
    }

    public void setArray(String[] array) {
        this.array = array;
    }
}

package com.mockitobase.petproject.model;


import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "massiv")
public class Massiv {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "users_id")
    private int users_id;

    @Column(name = "name")
    private String item;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(table = "users", name = "id")
    @JsonBackReference
    private User user;

    public Massiv() {
    }

    public Massiv(User user) {
        this.user = user;
    }

    public int getUsers_id() {
        return users_id;
    }

    public void setUsers_id(int users_id) {
        this.users_id = users_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

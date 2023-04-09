package com.mockitobase.petproject.service;


import com.mockitobase.petproject.model.User;
import com.mockitobase.petproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    private final User user;
    private final UserRepository userRepository;

    private final List<User> users = new ArrayList<>();


    @Autowired
    public UserService(User user, UserRepository userRepository) {
        this.user = user;
        this.userRepository = userRepository;
    }


    public List<User> getAll() {
//        return userRepository.findAll(); todo вспомнить как через Optional(null)
        return users;
    }

    public boolean add(User user) {
        return users.add(user);
    }

    public Optional<User> login(String name, String pass) {
        return users.stream()
                .filter(user -> user.getName().equals(name))
                .filter(user -> user.getPassword().equals(pass))
                .findFirst();


    }

}

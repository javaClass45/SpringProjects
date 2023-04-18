package com.mockitobase.petproject.service;


import com.mockitobase.petproject.dao.UserDao;
import com.mockitobase.petproject.model.User;

import java.util.*;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

//@Service
public class UserService {
//    private final User user;
//    private final UserRepository userRepository;
    private final UserDao userDao;


    private final List<User> users = new ArrayList<>();


//    @Autowired
//    public UserService(User user/*, UserRepository userRepository*/) {
//        this.user = user;
//        this.userRepository = userRepository;
//    }

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean delete(Integer userId) {
//        Integer userIdLockal = 25;
        return userDao.delete(userId);
    }

    public List<User> getAll() {
//        Optional<List<User>> userList = Optional.of(userRepository.findAll());
//        return userList.orElse(users);

        return users;
    }

    public void add(User... users) {
        this.users.addAll(Arrays.asList(users));
    }

    public Optional<User> login(String name, String pass) {
        if (name == null || pass == null) throw new IllegalArgumentException("username or pass is NULL");
        return users.stream()
                .filter(user -> user.getName().equals(name))
                .filter(user -> user.getPassword().equals(pass))
                .findFirst();


    }

    public Map<Integer, User> getAllConvertedById() {
        return users.stream()
                .collect(toMap(User::getId, identity()));
    }
}

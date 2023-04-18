package com.mockitobase.petproject.dao;

import org.springframework.context.annotation.Lazy;

import java.util.HashMap;
import java.util.Map;

public class UserDaoSpy extends UserDao {

    private final UserDao userDao;
    private Map<Integer, Boolean> answers = new HashMap<>();

    public UserDaoSpy(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @Lazy
    public boolean delete(Integer userId) {
        return answers.getOrDefault(userId,userDao.delete(userId));

    }
}

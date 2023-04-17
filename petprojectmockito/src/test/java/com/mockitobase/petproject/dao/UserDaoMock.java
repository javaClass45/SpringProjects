package com.mockitobase.petproject.dao;

import java.util.HashMap;
import java.util.Map;

public class UserDaoMock extends UserDao {

    private Map<Integer, Boolean> answers = new HashMap<>();



    @Override
    public boolean delete(Integer userId) {
        return answers.getOrDefault(userId,Boolean.FALSE);

    }
}

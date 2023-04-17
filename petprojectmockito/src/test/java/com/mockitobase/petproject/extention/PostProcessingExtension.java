package com.mockitobase.petproject.extention;

import com.mockitobase.petproject.dao.UserDao;
import com.mockitobase.petproject.model.User;
import com.mockitobase.petproject.service.UserService;
import lombok.Getter;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;

import java.lang.reflect.Field;

public class PostProcessingExtension implements TestInstancePostProcessor {

    private User user;

    @Override
    public void postProcessTestInstance(Object testInstance, ExtensionContext context)
            throws Exception {
        System.out.println("---PostProcessor---");
        var declaredFields = testInstance.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(Getter.class)) {
                field.set(testInstance, new UserService(new UserDao()));
            }
        }

    }
}

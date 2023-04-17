package com.mockitobase.petproject.paramresolver;

import com.mockitobase.petproject.dao.UserDao;
import com.mockitobase.petproject.model.User;
import com.mockitobase.petproject.repository.UserRepository;
import com.mockitobase.petproject.service.UserService;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceParamResolver implements ParameterResolver {

    private final User user;

    public UserServiceParamResolver() {
        user = new User();
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType() == UserService.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return new UserService(new UserDao());
    }

}

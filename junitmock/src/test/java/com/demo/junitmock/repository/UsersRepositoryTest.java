package com.demo.junitmock.repository;

import com.demo.junitmock.entity.Users;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.demo.junitmock.prototype.UsersPrototypeTest.aUser;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
class UsersRepositoryTest {

    @Autowired
    private UsersRepository usersRepository;


    @Test
    void findByLogin() {
        usersRepository.save(aUser());
        Users foundUser = usersRepository.findByLogin(aUser().getLogin());
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getName()).isEqualTo(aUser().getName());

     }




}
package my.springREST.pro.security;

import my.springREST.pro.model.Person;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;



public class PersonDetails implements UserDetails {

    private final Person person;

    public PersonDetails(Person person) {
        this.person = person;
    }

    //коллекция прав person
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
                return Collections.singletonList(new SimpleGrantedAuthority(person.getRole()));
    }

    @Override
    public String getPassword() {
        return this.person.getPassword();
    }

    @Override
    public String getUsername() {
        return this.person.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    //чтобы получать данные аут-го пользователя
    public Person getPerson() {
        return this.person;//this!!!!!!!!!!!!!!!!!!
    }
}

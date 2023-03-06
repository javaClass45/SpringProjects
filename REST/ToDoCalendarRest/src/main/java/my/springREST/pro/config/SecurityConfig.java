package my.springREST.pro.config;

import my.springREST.pro.services.PersonDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//для настройки аутентификации и авторизации
@EnableWebSecurity // конфигурационный класс для СпрингСецурити
@EnableGlobalMethodSecurity(prePostEnabled = true) //для авторизации на уровне методов
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PersonDetailService personDetailService;
    private final JWTFilter jwtFilter;

    @Autowired
    public SecurityConfig(PersonDetailService personDetailService, JWTFilter jwtFilter) {
        this.personDetailService = personDetailService;
        this.jwtFilter = jwtFilter;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //конфигурируем сам Спринг-сецурити какая страница отвечает за вход, какая за ошибки
        //конфигурируем авторизацию (правила конфигурации чиитаются сверху-вниз)
        http.csrf().disable()
                .authorizeRequests()//.csrf().disable()//временно отключаем токен(для данного урока)
                //.antMatchers("/admin").hasRole("ADMIN")//TODO эту строчку убираем если используем @PreAuthorize
                .antMatchers("/auth/login", "/auth/regPage",
                        "/error", "/index").permitAll() // смотрим какой запрос пришел, не авторизованых пускаем на эту страницу
                .anyRequest().hasAnyRole("USER", "ADMIN") // ко всем остальным страницам доступ получат юзер и админ
                //.anyRequest().authenticated() // для всех других запросов извольте аутетиф-ся
                .and() // после настройки авторизации переходим к настройкам утентиф-ии
                .formLogin().loginPage("/auth/login")
                .loginProcessingUrl("/process_login")
                .defaultSuccessUrl("/hello", true)
                .failureUrl("/auth/login?error")   //с параметром - ошибка
                .and()
                .logout()
                .clearAuthentication(true)
                .logoutUrl("/logout")
                .logoutSuccessUrl("/auth/login")
                .invalidateHttpSession(true)
                .and()
                .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

    //настраивает aутентификацию
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(personDetailService)
                .passwordEncoder(getPasswordEncoder());
    }

    @Bean // шифруем пароль с помощью алгоритма BCrypt
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();      //NoOpPasswordEncoder.getInstance();
    }

    @Bean // при пересоздании токена проверяем совпадение логина и пароля
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}

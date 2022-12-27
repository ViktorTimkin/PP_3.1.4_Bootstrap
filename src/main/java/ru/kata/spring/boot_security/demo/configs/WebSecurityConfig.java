package ru.kata.spring.boot_security.demo.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.kata.spring.boot_security.demo.service.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private SuccessUserHandler successUserHandler;
    private BCryptPasswordEncoder passwordEncoder;
    private UserService userService;

    public WebSecurityConfig(SuccessUserHandler successUserHandler,
                             BCryptPasswordEncoder passwordEncoder,
                             UserService userService) {
        this.successUserHandler = successUserHandler;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/login").not().fullyAuthenticated()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**").hasAnyRole("ADMIN", "USER")
                .antMatchers("/").permitAll()
                .antMatchers("/registration").permitAll()
                .antMatchers("/index").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().successHandler(successUserHandler)
                .defaultSuccessUrl("/")
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .logoutSuccessUrl("/")
                .and().csrf().disable();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        authenticationProvider.setUserDetailsService(userService);
        return authenticationProvider;
    }

    /*@Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(encoder);
    }*/

}
package com.diplom.apteka.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Bean
    protected PasswordEncoder  passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers(
//                            "/", "/home",
                            "/registration").permitAll()
                .antMatchers("/tablets/**","/", "/home",
                        "/buyings/**",
                        "/cart/**",
                        "/fabrications/**",
                        "/medevices/**",
                        "/purchase/**",
                        "/warehouse/**"
                ).hasAnyAuthority("ROLE_EMPLOYEE")
                    .antMatchers("/**").hasAnyAuthority("ROLE_ADMIN")
                    .antMatchers( "/tablets/**","/", "/home",
                            "/buyings/**",
                            "/cart/**",
                            "/fabrications/**",
                            "/medevices/**",
                            "/purchase/**",
                            "/warehouse/**").hasAnyAuthority("ROLE_EMPLOYEE", "ROLE_ADMIN")
                    .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .loginPage("/login")
                    .permitAll()
                .and()
                    .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout", "POST"))
                    .invalidateHttpSession(true) //test (вроде робит)
                    .clearAuthentication(true) //test (вроде робит)
                    .deleteCookies("JSESSIONID") //test (вроде робит)
                    .permitAll()
                .and()    //TEST (вроде робит)
                .httpBasic(); //TEST  (вроде робит)
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder())
//                .passwordEncoder(NoOpPasswordEncoder.getInstance())
                .usersByUsernameQuery("select username, password, 'true' from employee where username = ?")
                .authoritiesByUsernameQuery("select e.username, ur.authority from employee e inner join authorities ur on (e.roleid = ur.roleID) where e.username = ?");

    }

    // Данный метод используется тк на странице авторизации сначала перекидывает
    // либо на css.style либо на error данный метод игнорирует эти "переходы"
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**","/js/**","/fonts/**","/images/**", "/javax.faces.resource/**", "/favicon.ico", "/resources/**", "/error");
    }

//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/javax.faces.resource/**");
//    }

    //    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance();
//    }

    //    @Bean
//    @Override
//    public UserDetailsService userDetailsService() {
//        UserDetails user =
//                User.withDefaultPasswordEncoder()
//                        .username("u")
//                        .password("1")
//                        .roles("USER")
//                        .build();
//
//        return new InMemoryUserDetailsManager(user);
//    }
}

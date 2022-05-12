package com.furkanbegen.polling.config;

import com.furkanbegen.polling.repository.UserRepository;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  private final UserRepository userRepository;
  private final DataSource dataSource;
  private final UserDetailsService userDetailService;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailService)
        .passwordEncoder(encoder())
        .and()
        .jdbcAuthentication()
        .dataSource(dataSource)
        .usersByUsernameQuery("SELECT email, password, enabled  FROM users WHERE email=?")
        .authoritiesByUsernameQuery(
            "SELECT users.email as email, roles.name as role"
                + " FROM users"
                + " INNER JOIN users_roles ON users.id = users_roles.user_id"
                + " INNER JOIN roles ON roles.id = users_roles.roles_id"
                + " WHERE users.email = ?");
  }

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        .authorizeRequests()
        .antMatchers("/login")
        .permitAll()
        .and()
        .formLogin()
        .permitAll()
        .and()
        .csrf()
        .disable();
  }

  @Bean
  public PasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }
}

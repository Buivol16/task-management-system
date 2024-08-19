package pl.denys.taskmanager.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import pl.denys.taskmanager.config.security.authenticationmanager.AppUsernamePasswordAuthenticationManager;
import pl.denys.taskmanager.config.security.userdetailsmanager.AppUserDetailsManager;
import pl.denys.taskmanager.facade.user.UserFacade;
import pl.denys.taskmanager.repository.role.RoleRepository;
import pl.denys.taskmanager.repository.user.UserRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
  @Bean
  public DefaultSecurityFilterChain httpSecurity(HttpSecurity httpSecurity, UserFacade userFacade)
      throws Exception {
    httpSecurity
        .authorizeHttpRequests(
            (request) ->
                request.requestMatchers("/register").permitAll().anyRequest().authenticated())
        .formLogin(
            (login) ->
                login
                    .loginPage("/login")
                    .defaultSuccessUrl("/")
                    .passwordParameter("password")
                    .usernameParameter("username")
                    .permitAll())
        .authenticationManager(authenticationManager(userFacade))
        .logout((logout) -> logout.logoutUrl("/logout").permitAll());

    return httpSecurity.build();
  }


  //todo make a file
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new PasswordEncoder() {
      final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10);

      @Override
      public String encode(CharSequence rawPassword) {
        return bCryptPasswordEncoder.encode(rawPassword);
      }

      @Override
      public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return bCryptPasswordEncoder.matches(rawPassword, encodedPassword);
      }
    };
  }

  @Bean
  public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
    var dao = new DaoAuthenticationProvider();
    dao.setPasswordEncoder(passwordEncoder());
    dao.setUserDetailsService(userDetailsService);
    return dao;
  }

  @Bean
  public AuthenticationManager authenticationManager(UserFacade userFacade) {
    return new AppUsernamePasswordAuthenticationManager(userFacade);
  }

  @Bean
  public UserDetailsService userDetailsService(
      UserRepository userRepository, RoleRepository roleRepository) {
    return new AppUserDetailsManager(userRepository, roleRepository);
  }
}

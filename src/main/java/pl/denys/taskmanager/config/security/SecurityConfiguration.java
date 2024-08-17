package pl.denys.taskmanager.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.DefaultSecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
  @Bean
  public DefaultSecurityFilterChain httpSecurity(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        .authorizeHttpRequests((request) -> request.anyRequest().authenticated())
        .formLogin(
            (login) ->
                login
                    .loginPage("/login")
                    .defaultSuccessUrl("/ping", true)
                    .passwordParameter("password")
                    .usernameParameter("username")
                    .permitAll())
        .logout((logout) -> logout.logoutUrl("/logout").permitAll());

    return httpSecurity.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new PasswordEncoder() {
      BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10);

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
  protected UserDetailsService userDetailsService() {
    UserDetails annaSmithUser =
        User.builder().username("annasmith").password(passwordEncoder().encode("password")).build();

    return new InMemoryUserDetailsManager(annaSmithUser);
  }
}

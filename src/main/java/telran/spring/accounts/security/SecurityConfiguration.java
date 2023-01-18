package telran.spring.accounts.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.*;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Value("${app.username.admin:admin}")
    String admin;
    @Value("${app.password.admin:${ADMIN_PASSWORD}}")
    String adminPassword;

    @Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeHttpRequests(requests ->
                        requests.requestMatchers(HttpMethod.GET, "/{username}").hasAnyRole("USER", "ADMIN")
                                .anyRequest().hasRole("ADMIN")
                )
                .httpBasic();
        return http.build();

    }

    @Bean
    PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsManager userDetailsService(PasswordEncoder bCryptPasswordEncoder) {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername(admin)
                .password(bCryptPasswordEncoder.encode(adminPassword))
                .roles("ADMIN")
                .build());
        return manager;
    }
}

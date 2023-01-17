package telran.spring.calculator.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.server.ResponseStatusException;
import telran.spring.calculator.dto.Account;

@RestController
@RequestMapping("accounts")
public class AccountingController {
    PasswordEncoder encoder;
    UserDetailsManager manager;

    public AccountingController(PasswordEncoder encoder, UserDetailsManager manager) {
        this.encoder = encoder;
        this.manager = manager;
    }

    @PostMapping
    String addUser(@RequestBody Account account) {
        if (manager.userExists(account.username)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("User with name %s already exists", account.username));
        }
        manager.createUser(User.withUsername(account.username)
                .password(encoder.encode(account.password))
                .roles(account.role).build());
        return String.format("user %s has been added", account.username);
    }

    @PutMapping
    String updateUser(@RequestBody Account account) {
        if (!manager.userExists(account.username)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("User with name %s doesn't exist", account.username));
        }
        manager.updateUser(User.withUsername(account.username)
                .password(encoder.encode(account.password))
                .roles(account.role).build());
        return String.format("user %s has been updated", account.username);
    }

    @DeleteMapping("/{username}")
    String deleteUser(@PathVariable("username") String username) {
        if (!manager.userExists(username)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("User with name %s doesn't exist", username));
        }
        manager.deleteUser(username);
        return String.format("user %s has been deleted", username);
    }

    @GetMapping("/{username}")
    boolean userExists(@PathVariable("username") String username) {
        return manager.userExists(username);
    }
}

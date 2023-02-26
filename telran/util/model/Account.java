package telran.util.model;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Account {
    String username;
    String password;
    String expiration;
    String[] roles;

    public Account(String username, String password, String expiration, String[] roles) {
        this.username = username;
        this.password = password;
        this.expiration = expiration;
        this.roles = roles;
    }
}

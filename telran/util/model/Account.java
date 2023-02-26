package telran.util.model;

import lombok.Getter;


@Getter
public class Account {
    String username;
    String password;
    String expiration;
    String[] role;

    public Account(String username, String password, String expiration, String[] role) {
        this.username = username;
        this.password = password;
        this.expiration = expiration;
        this.role = role;
    }
}

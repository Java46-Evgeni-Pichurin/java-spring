package telran.spring.accounts.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import telran.spring.accounts.controller.AccountingController;
import telran.spring.accounts.dto.Account;

import java.util.Map;

@Service
public class AccountingManager {
    PasswordEncoder encoder;
    UserDetailsManager manager;
    AccountingService service;

    Logger LOG = LoggerFactory.getLogger(AccountingController.class);
    @Value("${app.message.wrong.operation}")
    String wrongOperationMessage;

    public AccountingManager(PasswordEncoder encoder, UserDetailsManager manager, AccountingService service) {
        this.encoder = encoder;
        this.manager = manager;
        this.service = service;
    }

    public String add(Account account) {
        if (service.isExist(account.username)) {
            LOG.error(wrongOperationMessage + " " + account.username);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("User with name %s already exists", account.username));
        }
        service.add(account);
        manager.createUser(User.withUsername(account.username)
                .password(encoder.encode(account.password))
                .roles(account.role).build());
        LOG.debug("POST request for adding user with name {}", account.username);
        return String.format("user %s has been added", account.username);
    }

    public String update(Account account) {
        if (!service.isExist(account.username)) {
            LOG.error(wrongOperationMessage + " " + account.username);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("User with name %s doesn't exist", account.username));
        }
        service.updateAccount(account);
        manager.updateUser(User.withUsername(account.username)
                .password(encoder.encode(account.password))
                .roles(account.role).build());
        LOG.debug("PUT request for updating user with name {}", account.username);
        return String.format("user %s has been updated", account.username);
    }

    public String delete(String username) {
        if (!service.isExist(username)) {
            LOG.error(wrongOperationMessage + " " + username);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("User with name %s doesn't exist", username));
        }
        service.deleteAccount(username);
        manager.deleteUser(username);
        LOG.debug("DELETE request for removing user with name {}", username);
        return String.format("user %s has been deleted", username);
    }

    public Boolean exists(String username) {
        LOG.debug("GET request for getting user with name {}", username);
        return service.isExist(username);
    }

    public Map<String, Account> getAll() {
        LOG.debug("GET request for getting all users");
        return service.getAll();
    }

    public void restore() {
        service.readAccounts("accountData");
        service.getAll().values().forEach(acc -> manager.createUser(User.withUsername(acc.username)
                .password(encoder.encode(acc.password))
                .roles(acc.role).build()));
        LOG.debug("Current number of users: {}", service.getAll().size());
    }

    public void save() {
        service.writeAccounts("accountData");
        LOG.info("Account data file has been updated");
    }
}

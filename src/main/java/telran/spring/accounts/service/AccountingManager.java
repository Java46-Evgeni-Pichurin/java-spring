package telran.spring.accounts.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
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

    public boolean add(Account account) {
        if (!service.isExist(account.username)) {
            service.add(account);
            manager.createUser(User.withUsername(account.username)
                    .password(encoder.encode(account.password))
                    .roles(account.role).build());
            return true;
        }
        return false;
    }

    public boolean update(Account account) {
        if (service.isExist(account.username)) {
            service.updateAccount(account);
            manager.updateUser(User.withUsername(account.username)
                    .password(encoder.encode(account.password))
                    .roles(account.role).build());
            return true;
        }
        return false;
    }

    public boolean delete(String username) {
        if (service.isExist(username)) {
            service.deleteAccount(username);
            manager.deleteUser(username);
            return true;
        }
        return false;
    }

    public boolean exists(String username) {
        return service.isExist(username);
    }

    public Map<String, Account> getAll() {
        return service.getAll();
    }

    public int restore(String path) {
        service.readAccounts(path);
        service.getAll().values().forEach(acc -> manager.createUser(User.withUsername(acc.username)
                .password(encoder.encode(acc.password))
                .roles(acc.role).build()));
        return service.getAll().size();
    }

    public void save(String path) {
        service.writeAccounts(path);
    }
}

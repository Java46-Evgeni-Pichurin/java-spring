package telran.spring.company.service.accounts;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import telran.spring.company.model.Account;

import java.util.Map;

@Service
public class AccountingManager {
    PasswordEncoder encoder;
    UserDetailsManager manager;
    AccountingService service;

    public AccountingManager(PasswordEncoder encoder, UserDetailsManager manager, AccountingService service) {
        this.encoder = encoder;
        this.manager = manager;
        this.service = service;
    }

    public boolean add(Account account) {
        if (!service.isExist(account.getUsername())) {
            service.add(account);
            manager.createUser(User.withUsername(account.getUsername())
                    .password(encoder.encode(account.getPassword()))
                    .roles(account.getRole()).build());
            return true;
        }
        return false;
    }

    public boolean update(Account account) {
        if (service.isExist(account.getUsername())) {
            service.updateAccount(account);
            manager.updateUser(User.withUsername(account.getUsername())
                    .password(encoder.encode(account.getPassword()))
                    .roles(account.getRole()).build());
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
        service.getAll().values().forEach(acc -> manager.createUser(User.withUsername(acc.getUsername())
                .password(encoder.encode(acc.getPassword()))
                .roles(acc.getRole()).build()));
        return service.getAll().size();
    }

    public void save(String path) {
        service.writeAccounts(path);
    }
}

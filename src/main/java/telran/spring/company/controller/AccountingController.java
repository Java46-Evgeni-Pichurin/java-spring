package telran.spring.company.controller;

import jakarta.annotation.*;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.server.ResponseStatusException;
import telran.spring.company.model.Account;
import telran.spring.company.service.accounts.AccountingManager;

import java.util.Map;

@RestController
@RequestMapping("accounts")
public class AccountingController {

    Logger LOG = LoggerFactory.getLogger(AccountingController.class);

    @Value("${app.message.wrong.operation}")
    String wrongOperationMessage;
    @Value("${file.path.name.accounts:accountData}")
    String filePath;
    AccountingManager accountingManager;

    public AccountingController(AccountingManager accountingManager) {
        this.accountingManager = accountingManager;
    }

    @PostMapping
    String addUser(@RequestBody @Valid Account account) {
        if (!accountingManager.add(account)) {
            LOG.error(wrongOperationMessage + " " + account.username);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("User with name %s already exists", account.username));
        }
        LOG.debug("POST request for adding user with name {}", account.username);
        return String.format("user %s has been added", account.username);
    }

    @PutMapping
    String updateUser(@RequestBody @Valid Account account) {
        if (!accountingManager.update(account)) {
            LOG.error(wrongOperationMessage + " " + account.username);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("User with name %s doesn't exist", account.username));
        }
        LOG.debug("PUT request for updating user with name {}", account.username);
        return "User has been updated";
    }

    @DeleteMapping("/{username}")
    String deleteUser(@PathVariable("username") String username) {
        if (!accountingManager.delete(username)) {
            LOG.error(wrongOperationMessage + " " + username);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("User with name %s doesn't exist", username));
        }
        LOG.debug("DELETE request for removing user with name {}", username);
        return "User has been removed";
    }

    @GetMapping("/{username}")
    boolean userExists(@PathVariable("username") String username) {
        LOG.debug("GET request for getting user with name {}", username);
        return accountingManager.exists(username);
    }

    @GetMapping("/all")
    Map<String, Account> allUsers() {
        LOG.debug("GET request for getting all users");
        return accountingManager.getAll();
    }

    @PostConstruct
    void restoreAccounts() {
        LOG.debug("Current number of accounts: {}", accountingManager.restore(filePath));
    }

    @PreDestroy
    void saveAccounts() {
        accountingManager.save(filePath);
        LOG.info("Account data file has been updated");
    }
}

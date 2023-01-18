package telran.spring.accounts.controller;

import jakarta.annotation.*;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import telran.spring.accounts.dto.Account;
import telran.spring.accounts.service.AccountingManager;

import java.util.Map;

@RestController
@RequestMapping("accounts")
public class AccountingController {
    AccountingManager accountingManager;

    public AccountingController(AccountingManager accountingManager) {
        this.accountingManager = accountingManager;
    }

    @PostMapping
    String addUser(@RequestBody @Valid Account account) {
        return accountingManager.add(account);
    }

    @PutMapping
    String updateUser(@RequestBody @Valid Account account) {
        return accountingManager.update(account);
    }

    @DeleteMapping("/{username}")
    String deleteUser(@PathVariable("username") String username) {
        return accountingManager.delete(username);
    }

    @GetMapping("/{username}")
    boolean userExists(@PathVariable("username") String username) {
        return accountingManager.exists(username);
    }

    @GetMapping("/all")
    Map<String, Account> allUsers() {
        return accountingManager.getAll();
    }

    @PostConstruct
    void restoreAccounts() {
        accountingManager.restore();
    }

    @PreDestroy
    void saveAccounts() {
        accountingManager.save();
    }
}

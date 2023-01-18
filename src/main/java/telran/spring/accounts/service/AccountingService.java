package telran.spring.accounts.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import telran.spring.accounts.dto.Account;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Service
public class AccountingService implements AccountingServiceInterface {
    Map<String, Account> accounts = new HashMap<>();
    ObjectMapper mapper = new ObjectMapper();
    Logger LOG = LoggerFactory.getLogger(AccountingService.class);
    @Value("${app.message.wrong.io}")
    String ioMessage;

    @Override
    public Boolean add(Account account) {
        return accounts.putIfAbsent(account.username, account) == null;
    }

    @Override
    public Boolean deleteAccount(String username) {
        if (!accounts.containsKey(username)) {
            return false;
        }
        accounts.remove(username);
        LOG.info("User {} has been removed", username);
        return true;
    }

    @Override
    public Boolean updateAccount(Account account) {
        String name = account.username;
        Account curAcc = accounts.get(name);
        LOG.info("User {} has been updated", account.username);
        return accounts.replace(name, curAcc, account);
    }

    @Override
    public Boolean isExist(String username) {
        return accounts.containsKey(username);
    }

    @Override
    public void readAccounts(String path) {
        try {
            TypeReference<HashMap<String, Account>> typeRef = new TypeReference<>() {};
            accounts = mapper.readValue(Paths.get(path).toFile(), typeRef);
            LOG.info("Reading file: {}", path);
        } catch (Exception e) {
            LOG.error(ioMessage + " " + e.getMessage());
        }
    }

    @Override
    public void writeAccounts(String path) {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(Paths.get(path).toFile(), accounts);
            LOG.info("Writing to file:");
        } catch (Exception e) {
            LOG.error(ioMessage + " " + e.getMessage());
        }
    }

    @Override
    public HashMap<String, Account> getAll() {
        return (HashMap<String, Account>) accounts;
    }
}
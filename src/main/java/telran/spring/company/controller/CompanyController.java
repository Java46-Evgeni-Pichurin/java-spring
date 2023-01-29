package telran.spring.company.controller;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.validation.Valid;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import telran.spring.company.model.Employee;
import telran.spring.company.service.employees.CompanyService;

import java.time.Month;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("employees")
public class CompanyController {

    Logger LOG = LoggerFactory.getLogger(CompanyController.class);
    @Value("#{'${file.path.company}'.split(',')}")
    String[] companyPaths;
    @Value("${app.message.wrong.operation}")
    String wrongOperationMessage;
    CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping
    String addEmployee(@RequestBody @Valid Employee employee) {
        LOG.debug("POST request for adding employee with name: {} {}", employee.firstName, employee.lastName);
        try {
            return String.format("Employee with id: %s has been added", companyService.addEmployee(employee).get().getId());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping
    String updateEmployee(@RequestBody @Valid Employee employee) {
        LOG.debug("PUT request for updating employee with name: {} {}", employee.firstName, employee.lastName);
        try {
            return String.format("Employee with id: %s has been updated", companyService.updateEmployee(employee).get().getId());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/{id}")
    String deleteEmployee(@PathVariable("id") Integer id) {
        Employee removedEmployee;
        try {
            removedEmployee = companyService.deleteEmployee(id).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        if (removedEmployee == null) {
            LOG.error(wrongOperationMessage + " " + id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Employee with id %d doesn't exist", id));
        }
        LOG.debug("DELETE request for removing employee with name: {} {}", removedEmployee.firstName, removedEmployee.lastName);
        return String.format("Employee with id: %s has been removed", removedEmployee.id);
    }

    @GetMapping("/{id}")
    boolean isExists(@PathVariable("id") Integer id) {
        LOG.debug("GET request for getting employee with id {}", id);
        try {
            return companyService.isExist(id).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/all")
    Map<Integer, Employee> getAll() {
        LOG.debug("GET request for getting all employees");
        try {
            return companyService.getAll().get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/salary/{salaryFrom}/{salaryTo}")
    String getBySalaryRange(@PathVariable("salaryFrom") Integer salaryFrom, @PathVariable("salaryTo") Integer salaryTo) {
        LOG.debug("GET request for getting employees with salary range [{}-{}]", salaryFrom, salaryTo);
        try {
            return String.format("Employees with salary in range [%d-%d]: %s", salaryFrom, salaryTo, companyService.employeesBySalary(salaryFrom, salaryTo).get()
                    .stream()
                    .map(e -> e.getFirstName() + " " + e.getLastName()).toList());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/age/{ageFrom}/{ageTo}")
    String getByAgeRange(@PathVariable("ageFrom") Integer ageFrom, @PathVariable("ageTo") Integer ageTo) {
        LOG.debug("GET request for getting employees with age in range [{}-{}]", ageFrom, ageTo);
        try {
            return String.format("Employees with age in range [%d-%d]: %s", ageFrom, ageTo, companyService.employeesByAge(ageFrom, ageTo).get()
                    .stream()
                    .map(e -> e.getFirstName() + " " + e.getLastName()).toList());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/month/{month}")
    String getByBirthMonth(@PathVariable("month") Integer month) {
        LOG.debug("GET request for getting employees born in {}", Month.of(month));
        try {
            return String.format("Employees born in %s: %s",Month.of(month), companyService.employeesByBirthMonth(month).get()
                    .stream()
                    .map(e -> e.getFirstName() + " " + e.getLastName()).toList());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @PostConstruct
    void restoreEmployees() {
        companyService.readEmployees(companyPaths[0], companyPaths[1], companyPaths[2], companyPaths[3]);
        try {
            LOG.debug("Current number of employees: {}", companyService.getAll().get().size());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @PreDestroy
    void saveAccounts() {
        companyService.writeEmployees(companyPaths[0], companyPaths[1], companyPaths[2], companyPaths[3]);
        LOG.info("Employee data file has been updated");
    }
}
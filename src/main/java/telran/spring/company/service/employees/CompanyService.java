package telran.spring.company.service.employees;

import telran.spring.company.model.Employee;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface CompanyService {

    
    CompletableFuture<Employee> addEmployee(Employee employee);

    CompletableFuture<Employee> deleteEmployee(int id);

    CompletableFuture<Employee> updateEmployee(Employee employee);

    CompletableFuture<Boolean> isExist(int id);

    void readEmployees(String companyPath, String byMonthPath, String byAgePath, String bySalaryPath);

    void writeEmployees(String companyPath, String byMonthPath, String byAgePath, String bySalaryPath);

    CompletableFuture<List<Employee>> employeesByBirthMonth(int numberOfMonth);

    CompletableFuture<List<Employee>> employeesBySalary(int salaryFrom, int salaryTo);

    CompletableFuture<List<Employee>> employeesByAge(int ageFrom, int ageTo);

    CompletableFuture<Map<Integer, Employee>> getAll();
}

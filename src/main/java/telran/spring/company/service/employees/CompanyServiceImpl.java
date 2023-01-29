package telran.spring.company.service.employees;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import telran.spring.company.model.Employee;

import java.nio.file.Paths;
import java.time.*;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CompanyServiceImpl implements CompanyService {

    private static final int MIN_ID = 100_000_000;
    private static final int MAX_ID = 999_999_999;
    Map<Integer, Employee> employees = new ConcurrentHashMap<>();
    Map<Integer, Map<Integer, Employee>> employeesByBirthMonth = new HashMap<>();
    TreeMap<Integer, Map<Integer, Employee>> employeesBySalary = new TreeMap<>();
    TreeMap<Integer, Map<Integer, Employee>> employeesByAge = new TreeMap<>();
    ObjectMapper mapper = new ObjectMapper();
    Logger LOG = LoggerFactory.getLogger(CompanyServiceImpl.class);
    @Value("${app.message.wrong.io}")
    String ioMessage;
    LocalDate lastUpdate;

    @Override
    @Async
    public CompletableFuture<Employee> addEmployee(Employee employee) {
        int id;
        do {
            id = (int) ((Math.random() * (MAX_ID - MIN_ID)) + MIN_ID);
        } while (employees.containsKey(id));
        employee.setId(id);
        LOG.info("Employee with ID: {} has been added", id);
        employees.put(id, employee);
        employeesByBirthMonth.computeIfAbsent(LocalDate.parse(employee.getBirthDay()).getMonthValue(),
                v -> new HashMap<>()).put(id, employee);
        employeesBySalary.computeIfAbsent(employee.getSalary(), v -> new HashMap<>()).put(id, employee);
        employeesByAge.computeIfAbsent(Period.between(LocalDate.parse(employee.getBirthDay()), lastUpdate).getYears(),
                v -> new HashMap<>()).put(id, employee);
        return CompletableFuture.completedFuture(employee);
    }

    @Override
    @Async
    public CompletableFuture<Employee> deleteEmployee(int id) {
        try {
            LOG.info("Employee with ID: {} has been removed", id);
            employeesByBirthMonth.computeIfAbsent(LocalDate.parse(employees.get(id).getBirthDay()).getMonthValue(),
                    v -> new HashMap<>()).remove(id);
            employeesBySalary.computeIfAbsent(employees.get(id).getSalary(), v -> new HashMap<>()).remove(id);
            employeesByAge.computeIfAbsent(Period.between(lastUpdate, LocalDate.parse(employees.get(id).getBirthDay())).getYears(),
                    v -> new HashMap<>()).remove(id);
            return CompletableFuture.completedFuture(employees.remove(id));
        } catch (Exception e) {
            LOG.warn("No such ID: {}. Can't be removed", id);
            throw new NoSuchElementException(e);
        }
    }

    @Override
    @Async
    public CompletableFuture<Employee> updateEmployee(Employee employee) {
        try {
            LOG.info("Employee with ID: {} has been updated", employee.getId());
            employeesByBirthMonth.computeIfAbsent(LocalDate.parse(employee.getBirthDay()).getMonthValue(),
                    v -> new HashMap<>()).replace(employee.getId(), employee);
            employeesBySalary.computeIfAbsent(employee.getSalary(),
                    v -> new HashMap<>()).replace(employee.getId(), employee);
            employeesByAge.computeIfAbsent(Period.between(lastUpdate, LocalDate.parse(employee.getBirthDay())).getYears(),
                    v -> new HashMap<>()).replace(employee.getId(), employee);
            return CompletableFuture.completedFuture(employees.replace(employee.getId(), employee));
        } catch (Exception e) {
            LOG.warn("No such ID: {}. Can't be updated", employee.getId());
            throw new NoSuchElementException(e);
        }
    }

    @Override
    @Async
    public CompletableFuture<Boolean> isExist(int id) {
        return CompletableFuture.completedFuture(employees.containsKey(id));
    }

    @Override
    public void readEmployees(String companyPath, String byMonthPath, String byAgePath, String bySalaryPath) {
        try {
            lastUpdate = LocalDate.now();
            TypeReference<HashMap<Integer, Employee>> typeRef = new TypeReference<>() {};
            employees = mapper.readValue(Paths.get(companyPath).toFile(), typeRef);
            LOG.info("Reading file: {}", companyPath);
            TypeReference<TreeMap<Integer, Map<Integer, Employee>>> typeRefTreeMap = new TypeReference<>() {};
            employeesByBirthMonth = mapper.readValue(Paths.get(byMonthPath).toFile(), typeRefTreeMap);
            LOG.info("Reading file: {}", byMonthPath);
            employeesByAge = mapper.readValue(Paths.get(byAgePath).toFile(), typeRefTreeMap);
            LOG.info("Reading file: {}", byAgePath);
            employeesBySalary = mapper.readValue(Paths.get(bySalaryPath).toFile(), typeRefTreeMap);
            LOG.info("Reading file: {}", bySalaryPath);
        } catch (Exception e) {
            LOG.warn(ioMessage + " " + e.getMessage());
        }
    }

    @Override
    public void writeEmployees(String companyPath, String byMonthPath, String byAgePath, String bySalaryPath) {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(Paths.get(companyPath).toFile(), employees);
            LOG.info("Writing to file: {}", companyPath);
            mapper.writerWithDefaultPrettyPrinter().writeValue(Paths.get(byMonthPath).toFile(), employeesByBirthMonth);
            LOG.info("Writing to file: {}", byMonthPath);
            mapper.writerWithDefaultPrettyPrinter().writeValue(Paths.get(byAgePath).toFile(), employeesByAge);
            LOG.info("Writing to file: {}", byAgePath);
            mapper.writerWithDefaultPrettyPrinter().writeValue(Paths.get(bySalaryPath).toFile(), employeesBySalary);
            LOG.info("Writing to file: {}", bySalaryPath);
        } catch (Exception e) {
            LOG.error(ioMessage + " " + e.getMessage());
        }
    }

    @Override
    @Async
    public CompletableFuture<List<Employee>> employeesByBirthMonth(int numberOfMonth) {
        if (numberOfMonth < 1 || numberOfMonth > 12) {
            LOG.warn("Wrong number of the given month: {}. Must be [1-12]", numberOfMonth);
            throw new IllegalArgumentException();
        }
        return CompletableFuture.completedFuture(employeesByBirthMonth.getOrDefault(numberOfMonth, Collections.emptyMap())
                .values()
                .stream()
                .toList());
    }

    @Override
    @Async
    public CompletableFuture<List<Employee>> employeesBySalary(int salaryFrom, int salaryTo) {
        if (salaryFrom > salaryTo) {
            LOG.warn("Wrong salary params. \"Salary from\" greater than \"salary to\"");
            throw new IllegalArgumentException();
        }
        return CompletableFuture.completedFuture(employeesBySalary.subMap(salaryFrom, true, salaryTo, true)
                .values()
                .stream()
                .flatMap(map -> map.values().stream())
                .toList());
    }

    @Override
    @Async
    public CompletableFuture<List<Employee>> employeesByAge(int ageFrom, int ageTo) {
        if (ageFrom > ageTo) {
            LOG.warn("Wrong age params. \"Age from\" greater than \"age to\"");
            throw new IllegalArgumentException();
        }
        return CompletableFuture.completedFuture(employeesByAge.subMap(ageFrom, true, ageTo, true)
                .values()
                .stream()
                .flatMap(map -> map.values().stream())
                .toList());
    }

    @Override
    @Async
    public CompletableFuture<Map<Integer, Employee>> getAll() {
        return CompletableFuture.completedFuture(employees);
    }
}

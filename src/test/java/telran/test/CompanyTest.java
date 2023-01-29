package telran.test;

import static org.junit.jupiter.api.Assertions.*;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import telran.spring.company.model.Employee;
import telran.spring.company.service.employees.CompanyService;

@SpringBootTest
public class CompanyTest {
    static Logger LOG = LoggerFactory.getLogger(CompanyTest.class);
    //@Autowired
    CompanyService companyService;
    Employee.EmployeeBuilder employeeBuilder;

    @BeforeEach
    void buildEmployee() {
        employeeBuilder = Employee.builder()
                .firstName("Mikky")
                .lastName("Mokky")
                .salary(7000)
                .birthDay("2000-10-10");
    }

    @Test
    @Order(1)
    @SneakyThrows
    void addEmployee_Success() {
        Employee employee = employeeBuilder.build();
        assertEquals(employee, companyService.addEmployee(employee));
    }

    @Test
    @Order(2)
    @SneakyThrows
    void updateEmployee_Success() {
        Employee employee = employeeBuilder.build();
        //int id = companyService.addEmployee(employee).getId();
    }
}

package telran.spring.calculator.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import telran.spring.calculator.TestObjects;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class DatesSimpleOperationTest {
    @Autowired
    DatesSimpleOperation service;

    @BeforeEach
    void restoreData() {
        TestObjects.restoreDateDaysData();
    }

    @Test
    void datesSimpleRightRequestOperationTest1() {
        assertTrue(service.execute(TestObjects.dateDaysData).contains("2020-10-17"));
    }
    @Test
    void datesSimpleRightRequestOperationTest2() {
        TestObjects.dateDaysData.additionalData = "before";
        assertTrue(service.execute(TestObjects.dateDaysData).contains("2020-10-03"));
    }
    @Test
    void datesSimpleBadRequestOperationTest1() {
        TestObjects.dateDaysData.date = "2020/10/10";
        assertTrue(service.execute(TestObjects.dateDaysData).contains("Wrong Date Format"));
    }
}

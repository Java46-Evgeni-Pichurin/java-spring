package telran.spring.calculator.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import telran.spring.calculator.TestObjects;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class DatesBetweenOperationTest {
    @Autowired
    DatesBetweenOperation service;

    @BeforeEach
    void restoreData() {
        TestObjects.restoreDateData();
    }

    @Test
    void datesBetweenRightRequestOperationTest() {
        assertTrue(service.execute(TestObjects.datesData).contains("7"));
    }
    @Test
    void datesBetweenBadRequestOperationTest() {
        TestObjects.datesData.additionalData = "any text";
        assertTrue(service.execute(TestObjects.datesData).contains("Mismatching operation"));
    }
}

package telran.spring.calculator.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import telran.spring.calculator.TestObjects;

@SpringBootTest
class ArithmeticSimpleOperationTest {
    @Autowired
    ArithmeticSimpleOperation service;

    @BeforeEach
    void restoreData() {
        TestObjects.restoreArithmeticData();
    }

    @Test
    void arithmeticOperationRightRequestTest() {
        assertTrue(service.execute(TestObjects.arithmeticData).contains("10"));
    }
    @Test
    void arithmeticOperationBadRequestTest() {
        TestObjects.arithmeticData.additionalData = "";
        assertTrue(service.execute(TestObjects.arithmeticData).contains("Wrong arithmetic operation"));
    }
}

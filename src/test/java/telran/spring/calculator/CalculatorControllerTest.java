package telran.spring.calculator;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import telran.spring.calculator.controller.CalculatorController;
import telran.spring.calculator.dto.*;
import telran.spring.calculator.security.SecurityConfiguration;

@WebMvcTest(CalculatorController.class)
@Import(SecurityConfiguration.class)
@WithMockUser(roles = {"USER", "ADMIN"})
class CalculatorControllerTest {
    @Autowired
    MockMvc mockMvc;
    ObjectMapper mapper = new ObjectMapper();

    @Test
    void operationResultRightDto() throws Exception {
        ArithmeticOperationData data = new ArithmeticOperationData();
        data.operationName = "arithmetic-simple";
        data.additionalData = "*";
        data.operand1 = 20.0;
        data.operand2 = 30.0;
        String dataJSON = mapper.writeValueAsString(data);
        mockMvc.perform(post("http://localhost:8080/calculator")
                .contentType(MediaType.APPLICATION_JSON)
                .content(dataJSON)).andExpect(status().isOk());
    }

    @Test
    void operationResultWrongDataArithmetic() throws Exception {
        ArithmeticOperationData data = new ArithmeticOperationData();
        data.operationName = "arithmetic-simple";
        data.additionalData = "*";
        data.operand1 = 20.0;

        runTestBadRequest(data);

    }

    @Test
    void operationResultWrongDataDates() throws Exception {
        DatesOperationData data = new DatesOperationData();
        data.operationName = "dates-simple";
        data.dateFrom = "100000";
        runTestBadRequest(data);

    }

    @Test
    void operationResultWrongDataDatesSimple() throws Exception {
        DateDaysOperationData data = new DateDaysOperationData();
        data.operationName = "dates-simple";
        data.days = -20;
        runTestBadRequest(data);

    }

    @Test
    void getTypesRequestTest() throws Exception {
        mockMvc.perform(get("http://localhost:8080/calculator"))
                .andExpect(status().isOk());
    }

    private void runTestBadRequest(OperationData data) throws Exception {
        String dataJSON = mapper.writeValueAsString(data);
        mockMvc.perform(post("http://localhost:8080/calculator")
                .contentType(MediaType.APPLICATION_JSON)
                .content(dataJSON)).andExpect(status().isBadRequest());
    }

}

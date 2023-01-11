package telran.spring.calculator.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import telran.spring.calculator.TestObjects;

import java.util.stream.IntStream;

@WebMvcTest(CalculatorController.class)
class CalculatorControllerTest {
    private static final int NUM_OF_REQUESTS = 1_000;
    private static final int NUM_OF_CLIENTS = 500;
    @Autowired
    MockMvc mockMvc;
    ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void restoreData() {
        TestObjects.restoreArithmeticData();
    }

    @Test
    void rightDataControllerTest() throws Exception {
        sendPostRequest();
    }

    @Test
    void postPerformTest() {
        Runnable runnable = () ->
                IntStream.range(0, NUM_OF_REQUESTS).forEach(r -> {
                    try {
                        sendPostRequest();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
        IntStream.range(0, NUM_OF_CLIENTS).forEach(request -> new Thread(runnable).start());
    }

    synchronized private void sendPostRequest() throws Exception {
        String messageJSON = mapper.writeValueAsString(TestObjects.arithmeticData);
        mockMvc.perform(post("http://localhost:8080/calculator")
                .contentType(MediaType.APPLICATION_JSON)
                .content(messageJSON)).andExpect(status().isOk());
    }


//    @Test
//    void wrongDataControllerTest() throws Exception {
//        TestObjects.arithmeticData.operand2 = null;
//        String messageJSON = mapper.writeValueAsString(TestObjects.arithmeticData);
//        mockMvc.perform(post("http://localhost:8080/calculator")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(messageJSON)).andExpect(status().isBadRequest());
//    }
}

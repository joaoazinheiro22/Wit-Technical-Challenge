package com.example.controller;

import com.example.CalculationResponse;
import com.example.listener.ResponseListener;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class CalculationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private KafkaTemplate<String, Object> kafkaTemplate;

    @MockitoBean
    private ResponseListener responseListener;

    @Test
    void testSumEndpoint() throws Exception {
        CalculationResponse mockResponse = CalculationResponse.success(new BigDecimal("5"), "test-id");
        when(responseListener.getResponse(anyString())).thenReturn(mockResponse);

        mockMvc.perform(get("/sum")
                        .param("a", "2")
                        .param("b", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(5))
                .andExpect(jsonPath("$.requestId").value("test-id"))
                .andExpect(jsonPath("$.errorMessage").isEmpty());
    }

    @Test
    void testSubtractionEndpoint() throws Exception {
        CalculationResponse mockResponse = CalculationResponse.success(new BigDecimal("2"), "test-id");
        when(responseListener.getResponse(anyString())).thenReturn(mockResponse);

        mockMvc.perform(get("/subtraction")
                        .param("a", "5")
                        .param("b", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(2))
                .andExpect(jsonPath("$.requestId").value("test-id"));
    }

    @Test
    void testMultiplicationEndpoint() throws Exception {
        CalculationResponse mockResponse = CalculationResponse.success(new BigDecimal("15"), "test-id");
        when(responseListener.getResponse(anyString())).thenReturn(mockResponse);

        mockMvc.perform(get("/multiplication")
                        .param("a", "3")
                        .param("b", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(15))
                .andExpect(jsonPath("$.requestId").value("test-id"));
    }

    @Test
    void testDivisionEndpoint() throws Exception {
        CalculationResponse mockResponse = CalculationResponse.success(new BigDecimal("2.5"), "test-id");
        when(responseListener.getResponse(anyString())).thenReturn(mockResponse);

        mockMvc.perform(get("/division")
                        .param("a", "5")
                        .param("b", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(2.5))
                .andExpect(jsonPath("$.requestId").value("test-id"));
    }

    @Test
    void testDivisionByZeroError() throws Exception {
        CalculationResponse mockResponse = CalculationResponse.error("test-id", "Cannot divide by zero");
        when(responseListener.getResponse(anyString())).thenReturn(mockResponse);

        mockMvc.perform(get("/division")
                        .param("a", "5")
                        .param("b", "0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").isEmpty())
                .andExpect(jsonPath("$.errorMessage").value("Cannot divide by zero"))
                .andExpect(jsonPath("$.requestId").value("test-id"));
    }

    @Test
    void testResponseListenerTimeout() throws Exception {
        when(responseListener.getResponse(anyString())).thenThrow(new RuntimeException("Timeout à espera da resposta"));

        mockMvc.perform(get("/sum")
                        .param("a", "2")
                        .param("b", "3"))
                .andExpect(status().isInternalServerError());
    }
}

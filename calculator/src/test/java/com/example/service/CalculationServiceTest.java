package com.example.service;

import com.example.CalculationRequest;
import com.example.CalculationResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class CalculationServiceTest {

    private CalculationService calculationService;

    @BeforeEach
    void setUp() {
        calculationService = new CalculationService();
    }

    @Test
    void testSum() {
        CalculationRequest request = new CalculationRequest("sum", new BigDecimal("5"), new BigDecimal("3"), "1");
        CalculationResponse response = calculationService.calculate(request);

        assertEquals(new BigDecimal("8"), response.getResult());
        assertNull(response.getErrorMessage());
        assertEquals("1", response.getRequestId());
    }

    @Test
    void testSumWithDecimals() {
        CalculationRequest request = new CalculationRequest("sum", new BigDecimal("2.5"), new BigDecimal("3.7"), "2");
        CalculationResponse response = calculationService.calculate(request);

        assertEquals(new BigDecimal("6.2"), response.getResult());
        assertNull(response.getErrorMessage());
    }

    @Test
    void testSubtract() {
        CalculationRequest request = new CalculationRequest("subtract", new BigDecimal("10"), new BigDecimal("4"), "3");
        CalculationResponse response = calculationService.calculate(request);

        assertEquals(new BigDecimal("6"), response.getResult());
        assertNull(response.getErrorMessage());
    }

    @Test
    void testSubtractNegativeResult() {
        CalculationRequest request = new CalculationRequest("subtract", new BigDecimal("3"), new BigDecimal("5"), "4");
        CalculationResponse response = calculationService.calculate(request);

        assertEquals(new BigDecimal("-2"), response.getResult());
        assertNull(response.getErrorMessage());
    }

    @Test
    void testMultiply() {
        CalculationRequest request = new CalculationRequest("multiply", new BigDecimal("6"), new BigDecimal("7"), "5");
        CalculationResponse response = calculationService.calculate(request);

        assertEquals(new BigDecimal("42"), response.getResult());
        assertNull(response.getErrorMessage());
    }

    @Test
    void testMultiplyWithZero() {
        CalculationRequest request = new CalculationRequest("multiply", new BigDecimal("5"), BigDecimal.ZERO, "6");
        CalculationResponse response = calculationService.calculate(request);

        assertEquals(BigDecimal.ZERO, response.getResult());
        assertNull(response.getErrorMessage());
    }

    @Test
    void testDivide() {
        CalculationRequest request = new CalculationRequest("divide", new BigDecimal("8"), new BigDecimal("2"), "7");
        CalculationResponse response = calculationService.calculate(request);

        assertEquals(new BigDecimal("4"), response.getResult());
        assertNull(response.getErrorMessage());
    }

    @Test
    void testDivideWithCleanResult() {
        CalculationRequest request = new CalculationRequest("divide", new BigDecimal("3"), new BigDecimal("2"), "8");
        CalculationResponse response = calculationService.calculate(request);

        assertEquals(new BigDecimal("1.5"), response.getResult());
        assertNull(response.getErrorMessage());
    }

    @Test
    void testDivideByZero() {
        CalculationRequest request = new CalculationRequest("divide", new BigDecimal("5"), BigDecimal.ZERO, "9");
        CalculationResponse response = calculationService.calculate(request);

        assertNull(response.getResult());
        assertEquals("Cannot divide by zero", response.getErrorMessage());
        assertEquals("9", response.getRequestId());
    }

    @Test
    void testUnsupportedOperation() {
        CalculationRequest request = new CalculationRequest("power", new BigDecimal("5"), new BigDecimal("3"), "10");
        CalculationResponse response = calculationService.calculate(request);

        assertNull(response.getResult());
        assertEquals("Unsupported operation: power", response.getErrorMessage());
        assertEquals("10", response.getRequestId());
    }

    @Test
    void testOperationCaseInsensitive() {
        CalculationRequest request = new CalculationRequest("SUM", new BigDecimal("10"), new BigDecimal("5"), "11");
        CalculationResponse response = calculationService.calculate(request);

        assertEquals(new BigDecimal("15"), response.getResult());
        assertNull(response.getErrorMessage());
    }

    @Test
    void testLargeNumbers() {
        CalculationRequest request = new CalculationRequest("sum",
                new BigDecimal("999999999999999999"),
                new BigDecimal("1"),
                "12");
        CalculationResponse response = calculationService.calculate(request);

        assertEquals(new BigDecimal("1000000000000000000"), response.getResult());
        assertNull(response.getErrorMessage());
    }

    @Test
    void testPrecisionDivision() {
        CalculationRequest request = new CalculationRequest("divide", new BigDecimal("1"), new BigDecimal("3"), "13");
        CalculationResponse response = calculationService.calculate(request);

        assertNotNull(response.getResult());
        assertEquals("0.3333333333", response.getResult().toString());
        assertNull(response.getErrorMessage());
    }
}

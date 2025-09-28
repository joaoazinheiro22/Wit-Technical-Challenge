package com.example;

import java.math.BigDecimal;

public class CalculationResponse {
    private BigDecimal result;
    private String requestId;
    private String errorMessage;

    // Default constructor
    public CalculationResponse() {}

    // Private constructor
    private CalculationResponse(BigDecimal result, String requestId, String errorMessage) {
        this.result = result;
        this.requestId = requestId;
        this.errorMessage = errorMessage;
    }

    // Static factory method for successful calculation
    public static CalculationResponse success(BigDecimal result, String requestId) {
        return new CalculationResponse(result, requestId, null);
    }

    // Static factory method for error cases
    public static CalculationResponse error(String requestId, String errorMessage) {
        return new CalculationResponse(null, requestId, errorMessage);
    }

    // Getters and setters
    public BigDecimal getResult() {
        return result;
    }

    public void setResult(BigDecimal result) {
        this.result = result;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}

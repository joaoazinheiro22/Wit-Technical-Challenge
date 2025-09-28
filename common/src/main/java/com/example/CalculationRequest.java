package com.example;

import java.math.BigDecimal;

public class CalculationRequest {

    private String operation;
    private BigDecimal a;
    private BigDecimal b;
    private String requestId;

    public CalculationRequest() {
    }

    public CalculationRequest(String operation, BigDecimal a, BigDecimal b, String requestId) {
        this.operation = operation;
        this.a = a;
        this.b = b;
        this.requestId = requestId;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public BigDecimal getA() {
        return a;
    }

    public void setA(BigDecimal a) {
        this.a = a;
    }

    public BigDecimal getB() {
        return b;
    }

    public void setB(BigDecimal b) {
        this.b = b;
    }

    public String getRequestId() {
        return requestId;
    }

}

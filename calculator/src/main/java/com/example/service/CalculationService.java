package com.example.service;

import com.example.CalculationRequest;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import com.example.CalculationResponse;

@Service
public class CalculationService {

    public CalculationResponse calculate(CalculationRequest request) {
        try {
            BigDecimal result;
            switch (request.getOperation().toLowerCase()) {
                case "sum":
                    result = request.getA().add(request.getB());
                    break;
                case "subtract":
                    result = request.getA().subtract(request.getB());
                    break;
                case "multiply":
                    result = request.getA().multiply(request.getB());
                    break;
                case "divide":
                    if (request.getB().compareTo(BigDecimal.ZERO) == 0) {
                        return CalculationResponse.error(request.getRequestId(), "Cannot divide by zero");
                    }
                    result = request.getA().divide(request.getB(), 10, BigDecimal.ROUND_HALF_UP).stripTrailingZeros();
                    break;
                default:
                    return CalculationResponse.error(request.getRequestId(), "Unsupported operation: " + request.getOperation());
            }
            return CalculationResponse.success(result, request.getRequestId());
        } catch (ArithmeticException e) {
            return CalculationResponse.error(request.getRequestId(), "Arithmetic error: " + e.getMessage());
        }
    }
}

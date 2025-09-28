package com.example.controller;

import com.example.CalculationRequest;
import com.example.CalculationResponse;
import com.example.listener.ResponseListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
public class CalculatorController {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    private ResponseListener responseListener;

    @GetMapping("/sum")
    public ResponseEntity<CalculationResponse> sum(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        return performCalculation("sum", a, b);
    }

    @GetMapping("/subtraction")
    public ResponseEntity<CalculationResponse> subtraction(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        return performCalculation("subtract", a, b);
    }

    @GetMapping("/multiplication")
    public ResponseEntity<CalculationResponse> multiplication(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        return performCalculation("multiply", a, b);
    }

    @GetMapping("/division")
    public ResponseEntity<CalculationResponse> division(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        return performCalculation("divide", a, b);
    }

    private ResponseEntity<CalculationResponse> performCalculation(String operation, BigDecimal a, BigDecimal b) {
        try {
            String requestId = UUID.randomUUID().toString();
            CalculationRequest request = new CalculationRequest(operation, a, b, requestId);

            kafkaTemplate.send("calc-requests", requestId, request);
            CalculationResponse response = responseListener.getResponse(requestId);

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}

package com.example.listener;

import com.example.CalculationRequest;
import com.example.CalculationResponse;
import com.example.service.CalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class CalculatorListener {

    @Autowired
    private CalculationService calculationService;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaListener(topics = "calc-requests", groupId = "calculator-group")
    public void processCalculationRequest(CalculationRequest request) {

        CalculationResponse response = calculationService.calculate(request);

        kafkaTemplate.send("calc-responses", request.getRequestId(), response);
    }
}

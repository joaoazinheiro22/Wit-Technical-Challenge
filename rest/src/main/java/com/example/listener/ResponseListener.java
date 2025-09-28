package com.example.listener;

import com.example.CalculationResponse;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ResponseListener {

    private final Map<String, CalculationResponse> responses = new ConcurrentHashMap<>();

    @KafkaListener(topics = "calc-responses", groupId = "orchestrator-group",
            containerFactory = "responseKafkaListenerContainerFactory")
    public void listenResponse(CalculationResponse response) {
        responses.put(response.getRequestId(), response);
    }


    public CalculationResponse getResponse(String requestId) {

        for (int i = 0; i < 50; i++) {
            CalculationResponse r = responses.remove(requestId);
            if (r != null) return r;
            try { Thread.sleep(100); } catch (InterruptedException ignored) {}
        }
        throw new RuntimeException("Timeout à espera da resposta");
    }
}


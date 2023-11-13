package com.GuardiansOfHoneyfruit.project.domain.region.message;

import com.GuardiansOfHoneyfruit.project.domain.region.dto.RiskConversionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegionMessageService {

    private final RabbitTemplate rabbitTemplate;

    public void sendRiskOfRegionRequest(final RiskConversionRequest request){
        rabbitTemplate.convertAndSend("data_processing_exchange", "data_processing_queue", request);
    }

}

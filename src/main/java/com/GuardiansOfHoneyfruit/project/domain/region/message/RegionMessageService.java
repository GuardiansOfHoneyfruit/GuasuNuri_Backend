package com.GuardiansOfHoneyfruit.project.domain.region.message;

import com.GuardiansOfHoneyfruit.project.domain.region.dto.RiskConversionRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegionMessageService {

    private final RabbitTemplate rabbitTemplate;

    public void sendDangerOfRegionRequest(final RiskConversionRequest request){
        rabbitTemplate.convertAndSend("data_processing_exchange", "data.processing.request", request);
    }

}

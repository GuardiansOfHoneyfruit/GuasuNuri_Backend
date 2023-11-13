package com.GuardiansOfHoneyfruit.project.domain.region.message;

import com.GuardiansOfHoneyfruit.project.domain.region.dto.RiskConversionResponse;
import com.GuardiansOfHoneyfruit.project.domain.region.service.DegreeOfRiskService;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class RegionMessageListener {

    private final DegreeOfRiskService degreeOfRiskService;

    @RabbitListener(queues = "data_processing_response_queue", ackMode = "MANUAL")
    private void receiveMessage(RiskConversionResponse response, Message message, Channel channel) throws IOException{
        try{
            degreeOfRiskService.updateRiskOfDegreeToRedis(response);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false); // 메시지 승인
        } catch (Exception e){
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false); // 메시지 거부, 글로벌 핸들링
        }
    }

}

package com.GuardiansOfHoneyfruit.project.global.config.rabbitMq.service;

import com.GuardiansOfHoneyfruit.project.global.config.rabbitMq.dto.MessageDto;

public interface ProducerService {
    void sendMessage(MessageDto dto);
}

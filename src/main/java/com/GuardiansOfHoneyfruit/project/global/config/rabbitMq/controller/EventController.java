package com.GuardiansOfHoneyfruit.project.global.config.rabbitMq.controller;

import com.GuardiansOfHoneyfruit.project.global.common.code.CommonCode;
import com.GuardiansOfHoneyfruit.project.global.common.dto.Response;
import com.GuardiansOfHoneyfruit.project.global.config.rabbitMq.dto.MessageDto;
import com.GuardiansOfHoneyfruit.project.global.config.rabbitMq.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/producer")
public class EventController {

    @Autowired
    private ProducerService producerService;


    @PostMapping("/send")
    public ResponseEntity<?> sendMessage(@RequestBody MessageDto dto){
        String result = "";
        producerService.sendMessage(dto);
        Response response = Response.of(CommonCode.GOOD_REQUEST, dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}

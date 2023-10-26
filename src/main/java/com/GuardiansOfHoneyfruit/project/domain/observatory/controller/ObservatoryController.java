package com.GuardiansOfHoneyfruit.project.domain.observatory.controller;

import com.GuardiansOfHoneyfruit.project.global.common.code.CommonCode;
import com.GuardiansOfHoneyfruit.project.global.common.dto.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/observatory")
@RequiredArgsConstructor
public class ObservatoryController {

    private final JobLauncher jobLauncher;
    private final Job weatherJob;

    @GetMapping("/asos")
    public ResponseEntity<Response> startWeatherJob(){
        try{
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            JobExecution jobExecution = jobLauncher.run(weatherJob, jobParameters);
            log.info("------");
            return ResponseEntity.ok(Response.of(CommonCode.GOOD_REQUEST));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(Response.of(CommonCode.BAD_REQUEST));
        }
    }

}

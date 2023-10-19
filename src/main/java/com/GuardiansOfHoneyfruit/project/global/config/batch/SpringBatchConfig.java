package com.GuardiansOfHoneyfruit.project.global.config.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SpringBatchConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job helloJob() {
        return jobBuilderFactory.get("helloJob") // helloJob 생성
                .start(helloStep1()) // helloStep1 시작
                .next(helloStep2()) // helloStep1이 끝나면 시작
                .build();
    }
    @Bean
    public Step helloStep1() { // helloStep1 생성
        return stepBuilderFactory.get("helloStep1")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("==================");
                    System.out.println("step1 was executed");
                    System.out.println("==================");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }


    @Bean
    public Step helloStep2() { // helloStep2 생성
        return stepBuilderFactory.get("helloStep2")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("==================");
                    System.out.println("step2 was executed");
                    System.out.println("==================");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }


}

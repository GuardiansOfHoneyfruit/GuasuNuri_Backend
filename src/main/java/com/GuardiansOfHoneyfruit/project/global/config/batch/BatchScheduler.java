package com.GuardiansOfHoneyfruit.project.global.config.batch;


import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BatchScheduler {

    private final JobLauncher jobLauncher;
    private final Job weatherJob;


    @Scheduled(cron = "0 0 * * * ?")
    public void runWeatherBatch(){
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            jobLauncher.run(weatherJob, jobParameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

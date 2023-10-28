package com.GuardiansOfHoneyfruit.project.domain.observatory.batch;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WeatherStepListener implements StepExecutionListener {

    private static final Logger log = LoggerFactory.getLogger(WeatherStepListener.class);

    @Override
    public void beforeStep(StepExecution stepExecution) {
        log.info("Starting weatherStep with parameters: {}", stepExecution.getJobParameters());
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        log.info("Finished weatherStep with status: {}. Read count: {}, Write count: {}, Skip count: {}",
                stepExecution.getStatus(),
                stepExecution.getReadCount(),
                stepExecution.getWriteCount(),
                stepExecution.getSkipCount());
        return stepExecution.getExitStatus();
    }
}

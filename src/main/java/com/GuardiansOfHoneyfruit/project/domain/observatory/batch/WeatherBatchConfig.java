package com.GuardiansOfHoneyfruit.project.domain.observatory.batch;

import com.GuardiansOfHoneyfruit.project.domain.asos.dao.AsosFindDao;
import com.GuardiansOfHoneyfruit.project.domain.asos.dao.AsosRepository;
import com.GuardiansOfHoneyfruit.project.domain.asos.domain.Asos;
import com.GuardiansOfHoneyfruit.project.domain.asos.dto.AsosEntityDto;
import com.GuardiansOfHoneyfruit.project.domain.asos.service.AsosParsingService;
import com.GuardiansOfHoneyfruit.project.domain.observatory.dao.ObservatoryFindDao;
import com.GuardiansOfHoneyfruit.project.domain.observatory.dao.ObservatoryRepository;
import com.GuardiansOfHoneyfruit.project.domain.observatory.domain.Observatory;
import com.GuardiansOfHoneyfruit.project.domain.observatory.dto.ObservatoryRegionDto;
import com.GuardiansOfHoneyfruit.project.domain.region.domain.Region;
import com.GuardiansOfHoneyfruit.project.domain.region.dto.RiskConversionRequest;
import com.GuardiansOfHoneyfruit.project.domain.region.message.RegionMessageService;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class WeatherBatchConfig {

    private final EntityManagerFactory entityManagerFactory;
    private final RestTemplate restTemplate;
    private final AsosParsingService asosParsingService;
    private final AsosRepository asosRepository;
    private final RegionMessageService regionMessageService;
    private final AsosFindDao asosFindDao;
    private final int chunkSize = 100;
    private final int convertChunkSize = 20;

    @Value("${api.keys.weather.key}")
    private String apiKey;

    private final String API_URL_NOW = "https://apihub.kma.go.kr/api/typ01/url/kma_sfcdd.php?stn=%s&authKey=%s";
    @Bean
    public Job weatherJob(final JobRepository jobRepository, final Step weatherStep, final Step weatherConvertStep) {
        return new JobBuilder("weatherJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(weatherStep)
                .next(weatherConvertStep)
                .build();
    }

    @Bean(name = "weatherStep")
    public Step weatherStep(final JobRepository jobRepository, final PlatformTransactionManager transactionManager) {
        return new StepBuilder("weatherStep", jobRepository)
                .<Observatory, List<Asos>>chunk(this.chunkSize, transactionManager)
                .reader(observatoryReader())
                .processor(asosProcessor())
                .writer(asosListWriter())
                .listener(new WeatherStepListener())
                .build();
    }

    @Bean(name = "weatherConvertStep")
    public Step weatherConvertStep(final JobRepository jobRepository,
                                   final PlatformTransactionManager transactionManager,
                                   final ItemProcessor<Region, RiskConversionRequest> weatherDataProcessor,
                                   final ItemWriter<RiskConversionRequest> weatherDataWriter) {
        return new StepBuilder("weatherConvertStep", jobRepository)
                .<Region, RiskConversionRequest>chunk(this.convertChunkSize, transactionManager)
                .reader(regionReader())
                .processor(weatherDataProcessor)
                .writer(weatherDataWriter)
                .listener(new WeatherStepListener())
                .build();
    }

    @Bean
    public ItemProcessor<Region, RiskConversionRequest> weatherDataProcessor() {
        return region -> {
            Long observatoryId = region.getObservatory().getObservatoryId();
            return RiskConversionRequest.fromAsosEntity(region, findLatestAsosByObservatoryId(observatoryId));
        };
    }

    @Bean
    public ItemProcessor<Observatory, List<Asos>> asosProcessor() {
        return observatory -> {
            log.info("Processing observatory: {}", observatory);
            List<Asos> asosList = new ArrayList<>();

            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

            String formattedUrl = String.format(API_URL_NOW, observatory.getObservatoryId(), apiKey);
            ResponseEntity<String> response = restTemplate.exchange(formattedUrl, HttpMethod.GET, entity, String.class);
            String jsonResponse = response.getBody();
            List<AsosEntityDto> asosData = asosParsingService.parse(jsonResponse, LocalDate.now().getYear());

            for (AsosEntityDto dto : asosData) {
                asosList.add(observatory.buildAsos(dto));
            }
            return asosList;
        };
    }

    @Bean
    public ItemWriter<List<Asos>> asosListWriter() {
        return list -> {
            List<Asos> allAsoses = new ArrayList<>();
            for (List<Asos> asosList : list) {
                allAsoses.addAll(asosList);
                asosRepository.saveAll(allAsoses);
            }
        };
    }

    @Bean
    public JpaPagingItemReader<Observatory> observatoryReader() {
        JpaPagingItemReader<Observatory> reader = new JpaPagingItemReader<>();
        reader.setEntityManagerFactory(entityManagerFactory);
        reader.setPageSize(chunkSize);
        reader.setQueryString("SELECT o FROM Observatory o");
        return reader;
    }

    @Bean
    public JpaPagingItemReader<Region> regionReader() {
        JpaPagingItemReader<Region> reader = new JpaPagingItemReader<>();
        reader.setEntityManagerFactory(entityManagerFactory);
        reader.setPageSize(convertChunkSize);
        reader.setQueryString("SELECT r FROM Region r");
        return reader;
    }

    @Bean
    public ItemWriter<RiskConversionRequest> weatherDataWriter() {
        return requests -> {
            for (RiskConversionRequest request : requests) {
                log.info(request + "번 지역 변환 요청");
                regionMessageService.sendDangerOfRegionRequest(request);
            }
        };
    }

    private AsosEntityDto findLatestAsosByObservatoryId(Long observatoryId){
        return asosFindDao.findLatestAsosObservatoryId(observatoryId);
    }

}

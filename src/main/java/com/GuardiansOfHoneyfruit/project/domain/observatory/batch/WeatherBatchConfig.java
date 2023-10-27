package com.GuardiansOfHoneyfruit.project.domain.observatory.batch;

import com.GuardiansOfHoneyfruit.project.domain.asos.dao.AsosRepository;
import com.GuardiansOfHoneyfruit.project.domain.asos.domain.Asos;
import com.GuardiansOfHoneyfruit.project.domain.asos.dto.AsosEntityDto;
import com.GuardiansOfHoneyfruit.project.domain.asos.service.AsosParsingService;
import com.GuardiansOfHoneyfruit.project.domain.observatory.dao.ObservatoryFindDao;
import com.GuardiansOfHoneyfruit.project.domain.observatory.domain.Observatory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
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

    private final ObservatoryFindDao observatoryFindDao;
    private final RestTemplate restTemplate;
    private final AsosParsingService asosParsingService;
    private final AsosRepository asosRepository;

    private final int chunkSize = 100;

    @Value("${api.keys.weather.key}")
    private String apiKey;

    private final String API_URL_NOW = "https://apihub.kma.go.kr/api/typ01/url/kma_sfcdd.php?stn=%s&authKey=%s";
    @Bean
    public Job weatherJob(final JobRepository jobRepository, final Step weatherStep){
        return new JobBuilder("job", jobRepository)
                .start(weatherStep)
                .build();
    }

    @Bean(name = "weatherStep")
    public Step weatherStep(final JobRepository jobRepository, final PlatformTransactionManager transactionManager){
        return new StepBuilder("step", jobRepository)
                .<Observatory, List<Asos>>chunk(this.chunkSize, transactionManager)
                .reader(this.observatoryReader())
                .processor(this.asosProcessor())
                .writer(this.asosListWriter())
                .build();
    }

    @Bean
    public ItemProcessor<Observatory, List<Asos>> asosProcessor() {
        return observatory -> {
            List<Asos> asosList = new ArrayList<>();

            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

            String formattedUrl = String.format(API_URL_NOW, observatory.getObservatoryId(), apiKey);
            String jsonResponse = restTemplate.exchange(formattedUrl, HttpMethod.GET, entity, String.class).getBody();
            List<AsosEntityDto> asosData = asosParsingService.parse(jsonResponse, LocalDate.now().getYear());

            for (AsosEntityDto dto : asosData) {
                asosList.add(observatory.buildAsos(dto));
            }

            return asosList;
        };
    }


    private ListItemReader<Observatory> observatoryReader(){
        return new ListItemReader<>(observatoryFindDao.findAll());
    }

    @Bean
    public ItemWriter<List<Asos>> asosListWriter() {
        return list -> {
            List<Asos> allAsoses = new ArrayList<>();
            for (List<Asos> asosList : list) {
                allAsoses.addAll(asosList);
            }
            asosRepository.saveAll(allAsoses);
        };
    }

}

package com.GuardiansOfHoneyfruit.project.domain.observatory.batch;

import com.GuardiansOfHoneyfruit.project.domain.asos.dao.AsosRepository;
import com.GuardiansOfHoneyfruit.project.domain.asos.domain.Asos;
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
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class WeatherBatchConfig {

    private final ObservatoryFindDao observatoryFindDao;
    private final AsosRepository asosRepository;
    private final RestTemplate restTemplate;

    private int chunkSize = 100;

    @Value("${api.keys.weather.key}")
    private String apiKey;

    private final String API_URL = "https://apihub.kma.go.kr/api/typ01/url/kma_sfcdd3.php";

    @Bean
    public Job job(final JobRepository jobRepository, final Step weatherStep){
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
                .writer(this.asosWriter(asosRepository))
                .build();
    }

    @Bean
    public ItemProcessor<Observatory, List<Asos>> asosProcessor(){
        return observatory -> {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

            String startDate = "20210101";
            String endDate = "20231024";

            String formattedUrl = String.format(API_URL, startDate, endDate, observatory.getObservatoryId(), apiKey);
            String jsonResponse = restTemplate.exchange(formattedUrl, HttpMethod.GET, entity, String.class).getBody();

            List<Asos> asosData =

            return asosData;
        };
    }

    private ListItemReader<Observatory> observatoryReader(){
        return new ListItemReader<>(observatoryFindDao.findAll());
    }

    @Bean
    public ItemWriter<List<Asos>> asosWriter(AsosRepository asosRepository){
        return listOfAsos -> {
            listOfAsos.forEach(asos -> {
                if(!asos.isEmpty()){
                    asosRepository.saveAll(asos);
                }
            });
        }
    }

}

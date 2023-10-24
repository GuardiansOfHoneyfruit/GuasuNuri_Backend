package com.GuardiansOfHoneyfruit.project.domain.soil.batch;

import com.GuardiansOfHoneyfruit.project.domain.region.dao.RegionFindDao;
import com.GuardiansOfHoneyfruit.project.domain.region.domain.Region;
import com.GuardiansOfHoneyfruit.project.domain.soil.dao.SoilRepository;
import com.GuardiansOfHoneyfruit.project.domain.soil.domain.Soil;
import com.GuardiansOfHoneyfruit.project.domain.soil.dto.SoilResponseDto;
import com.GuardiansOfHoneyfruit.project.domain.soil.dto.XMLResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.*;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class SoilBatchConfiguration extends DefaultBatchConfiguration {

    private final RegionFindDao regionFindDao;
    private final SoilRepository soilRepository;
    private int chunkSize = 1000;
    @Value("${api.keys.data-kr.encoding}")
    private String serviceKey;

    private final String API_URL = "http://apis.data.go.kr/1390802/SoilEnviron/SoilExam";

    @Bean
    public Job job(final JobRepository jobRepository, final Step step) {
        return new JobBuilder("job", jobRepository)
                .start(step)
                .build();
    }

    @Bean
    public Step soilStep(final JobRepository jobRepository, final PlatformTransactionManager transactionManager) {
        return new StepBuilder("step", jobRepository)
                .<Region, List<Soil>>chunk(this.chunkSize, transactionManager)
                .reader(this.regionReader())
                .processor(this.soilProcessor())
                .writer(this.soilWriter(soilRepository))
                .build();
    }

    @Bean
    public ItemProcessor<Region, List<Soil>> soilProcessor() {
        return region -> {
            // HttpHeaders 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Accept", "*/*;q=0.9");

            // 1. API 요청을 통해 데이터 가져오기
            RestTemplate restTemplate = new RestTemplate();

            String apiUrl = API_URL + "/getSoilExamList?" +
                    "serviceKey=" + serviceKey +
                    "&Page_Size=200" +
                    "&Page_No=1" +
                    "&BJD_Code=" + region.getRegionId();

            URI uri = new URI(apiUrl);
            System.out.println(uri);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);

            String xmlResponse = response.getBody();
            System.out.println(xmlResponse);
            // 2. XML 응답을 DTO로 변환
            List<SoilResponseDto> soilResponseDtoList = new ArrayList<>();
            try {
                JAXBContext jaxbContext = JAXBContext.newInstance(XMLResponseDto.class);
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                StringReader reader = new StringReader(xmlResponse);
                XMLResponseDto responseDto = (XMLResponseDto) unmarshaller.unmarshal(reader);
                soilResponseDtoList = responseDto.getBody().getItems();
            } catch (JAXBException e) {
                log.error("Error parsing XML response: " + e.getMessage());
                e.printStackTrace();
            }
            log.info(soilResponseDtoList.size() + "개");
            if (soilResponseDtoList.isEmpty()) {
                return Collections.emptyList();
            }

            return soilResponseDtoList.stream().map(SoilResponseDto::toEntity).collect(Collectors.toList());
        };
    }



    @Bean
    public ItemWriter<List<Soil>> soilWriter(SoilRepository soilRepository) {
        return listOfSoils -> {
            log.info(listOfSoils.toString());
            listOfSoils.forEach(soils -> {
                if (!soils.isEmpty()) {
                    soilRepository.saveAll(soils);
                }
            });
        };
    }



    private ListItemReader<Region> regionReader(){
        return new ListItemReader<>(this.regionFindDao.findAll());
    }

}
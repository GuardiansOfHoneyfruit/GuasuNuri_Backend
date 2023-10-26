package com.GuardiansOfHoneyfruit.project.domain.soil.batch;

import com.GuardiansOfHoneyfruit.project.domain.region.dao.PnuFindDao;
import com.GuardiansOfHoneyfruit.project.domain.region.domain.Pnu;
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
import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class SoilBatchConfiguration extends DefaultBatchConfiguration {

    private final PnuFindDao pnuFindDao;
    private final int chunkSize = 1000;
    @Value("${api.keys.data-kr.encoding}")
    private String serviceKey;

    private final String API_URL = "http://apis.data.go.kr/1390802/SoilEnviron/SoilExam";

    @Bean
    public Job soilJob(final JobRepository jobRepository, final Step soilStep) {
        return new JobBuilder("job", jobRepository)
                .start(soilStep)
                .build();
    }

    @Bean(name = "soilStep")
    public Step soilStep(final JobRepository jobRepository, final PlatformTransactionManager transactionManager) {
        return new StepBuilder("step", jobRepository)
                .<Pnu, Pnu>chunk(this.chunkSize, transactionManager)  // 변경됨
                .reader(this.pnuReader())
                .processor(this.soilProcessor())
                .writer(this.pnuWriter())  // 변경됨
                .build();
    }

    @Bean
    public ItemProcessor<Pnu, Pnu> soilProcessor() {
        return pnu -> {
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
                    "&BJD_Code=" + pnu.getPnuCode();

            URI uri = new URI(apiUrl);

            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
            String xmlResponse = response.getBody();
            // 2. XML 응답을 DTO로 변환
            List<SoilResponseDto> soilResponseDtoList = new ArrayList<>();
            try {
                JAXBContext jaxbContext = JAXBContext.newInstance(XMLResponseDto.class);
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                StringReader reader = new StringReader(xmlResponse);
                XMLResponseDto responseDto = (XMLResponseDto) unmarshaller.unmarshal(reader);
                soilResponseDtoList = responseDto.getBody().getItems();
            } catch (JAXBException e) {
                e.printStackTrace();
            }
            if (soilResponseDtoList.isEmpty()) {
                return null;
            }

            for (SoilResponseDto dto : soilResponseDtoList) {
                pnu.addSoil(dto);
            }
            return pnu;
        };
    }

    @Bean
    public ItemWriter<Pnu> pnuWriter() {
        return pnus -> {

        };
    }

    private ListItemReader<Pnu> pnuReader(){
        return new ListItemReader<>(pnuFindDao.findAll());
    }

}
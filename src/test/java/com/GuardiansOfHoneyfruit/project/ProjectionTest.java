package com.GuardiansOfHoneyfruit.project;

import com.GuardiansOfHoneyfruit.project.domain.region.dao.RegionRepository;
import com.GuardiansOfHoneyfruit.project.domain.region.dto.RegionRecord;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ProjectionTest {

    @Autowired
    RegionRepository regionRepository;

    @Test
    @DisplayName("record 프로젝션을 통해 지역명과 지역코드를 추출")
    void 프로젝션_지역명_지역코드_확인(){
        //when
        List<RegionRecord> regionRecords = regionRepository.findAllRegionNameAndRegionCode();

        //then
        assertThat(regionRecords.size()).isEqualTo(14);
        assertThat(regionRecords.get(0).regionCode()).isEqualTo("43111");
        assertThat(regionRecords.get(0).regionName()).isEqualTo("청주시");
    }
}

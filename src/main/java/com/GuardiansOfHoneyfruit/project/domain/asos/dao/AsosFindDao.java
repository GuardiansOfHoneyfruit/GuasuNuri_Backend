package com.GuardiansOfHoneyfruit.project.domain.asos.dao;

import com.GuardiansOfHoneyfruit.project.domain.asos.dto.AsosEntityDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AsosFindDao {

    private final AsosRepository asosRepository;

    public AsosEntityDto findLatestAsosObservatoryId(Long observatoryId){
        return AsosEntityDto.from(asosRepository.findTopByObservatoryIdOrderByCreatedAtDescNative(observatoryId));
    }

}

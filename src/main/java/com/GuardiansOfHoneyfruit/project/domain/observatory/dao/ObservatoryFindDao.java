package com.GuardiansOfHoneyfruit.project.domain.observatory.dao;

import com.GuardiansOfHoneyfruit.project.domain.observatory.domain.Observatory;
import com.GuardiansOfHoneyfruit.project.domain.observatory.exception.ObservatoryNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ObservatoryFindDao {

    private final ObservatoryRepository observatoryRepository;

    public Observatory findById(Long targetId){
        Observatory observatory = observatoryRepository.findById(targetId)
                .orElseThrow(() -> new ObservatoryNotFoundException(targetId));
        return observatory;
    }

    public List<Observatory> findAll(){
        return observatoryRepository.findAll();
    }

}

package com.GuardiansOfHoneyfruit.project.domain.region.dao;

import com.GuardiansOfHoneyfruit.project.domain.region.domain.Pnu;
import com.GuardiansOfHoneyfruit.project.domain.region.exception.PnuNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PnuFindDao {

    private final PnuRepository pnuRepository;

    public Pnu findById(String pnuCd){
        Pnu pnu = pnuRepository.findById(pnuCd)
                .orElseThrow(() -> new PnuNotFoundException(pnuCd));
        return pnu;
    }

    public List<Pnu> findAll(){
        return pnuRepository.findAll();
    }
}

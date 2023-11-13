package com.GuardiansOfHoneyfruit.project.domain.region.controller;

import com.GuardiansOfHoneyfruit.project.domain.region.dao.RegionFindDao;
import com.GuardiansOfHoneyfruit.project.global.common.dto.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/regions")
@RequiredArgsConstructor
public class RegionController {

    private final RegionFindDao regionFindDao;

    @GetMapping
    public ResponseEntity<Response> getAllRegionName(){
        Response response = regionFindDao.findAllRegionName();
        return ResponseEntity.ok(response);
    }
}

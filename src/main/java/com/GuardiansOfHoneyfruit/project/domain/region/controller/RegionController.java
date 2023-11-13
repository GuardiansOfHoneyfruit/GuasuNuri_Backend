package com.GuardiansOfHoneyfruit.project.domain.region.controller;

import com.GuardiansOfHoneyfruit.project.domain.region.dao.RegionFindDao;
import com.GuardiansOfHoneyfruit.project.domain.region.service.RegionDangerService;
import com.GuardiansOfHoneyfruit.project.global.common.dto.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/regions")
@RequiredArgsConstructor
public class RegionController {

    private final RegionFindDao regionFindDao;
    private final RegionDangerService regionDangerService;

    @GetMapping
    public ResponseEntity<Response> getAllRegionsName(){
        Response response = regionFindDao.findAllRegionName();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/dangers")
    public ResponseEntity<Response> getAllDangerLevel(){
        Response response = regionDangerService.getAllDangerLevel();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{regionCode}/dangers")
    public ResponseEntity<Response> getDegreeOfRiskByRegionCode(@PathVariable @Valid final String regionCode){
        Response response = regionDangerService.getDangerLevelAtSingleRegion(regionCode);
        return ResponseEntity.ok(response);
    }
}

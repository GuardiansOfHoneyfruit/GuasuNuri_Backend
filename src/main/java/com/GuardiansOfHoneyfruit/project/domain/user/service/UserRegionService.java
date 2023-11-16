package com.GuardiansOfHoneyfruit.project.domain.user.service;

import com.GuardiansOfHoneyfruit.project.domain.region.dao.RegionFindDao;
import com.GuardiansOfHoneyfruit.project.domain.region.service.RegionDangerService;
import com.GuardiansOfHoneyfruit.project.domain.user.code.UserCode;
import com.GuardiansOfHoneyfruit.project.domain.user.dao.UserFindDao;
import com.GuardiansOfHoneyfruit.project.global.common.dto.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRegionService {

    private final RegionDangerService regionDangerService;

    public Response getUserRegionDangerLevel(final OAuth2User oAuth2User){
        try {
            return regionDangerService.getDangerLevelAtSingleRegion(oAuth2User.getAttributes().get("region").toString());
        } catch (NullPointerException e){
            return Response.of(UserCode.USER_REGION_IS_NULL);
        }
    }

}

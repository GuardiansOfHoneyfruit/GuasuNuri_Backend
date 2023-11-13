package com.GuardiansOfHoneyfruit.project.domain.user.service;

import com.GuardiansOfHoneyfruit.project.domain.region.dao.RegionFindDao;
import com.GuardiansOfHoneyfruit.project.domain.region.domain.Region;
import com.GuardiansOfHoneyfruit.project.domain.user.code.UserCode;
import com.GuardiansOfHoneyfruit.project.domain.user.dao.UserFindDao;
import com.GuardiansOfHoneyfruit.project.domain.user.domain.User;
import com.GuardiansOfHoneyfruit.project.domain.user.dto.UserRegionCreateResponse;
import com.GuardiansOfHoneyfruit.project.domain.user.dto.UserRegionUpdateRequest;
import com.GuardiansOfHoneyfruit.project.global.common.dto.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserUpdateService {

    private final UserFindDao userFindDao;
    private final RegionFindDao regionFindDao;
    private static final String USER_UUID_ATTRIBUTE = "userUuid";
    @Transactional
    public Response createUserRegion(final UserRegionUpdateRequest request, final String userUuid){
        User user = userFindDao.findByUserUuId(userUuid);
        Region regionReference = regionFindDao.findRegionReference(request.getRegionCode());
        user.updateRegion(regionReference);
        return Response.of(UserCode.USER_REGION_SETTING_SUCCESS, UserRegionCreateResponse.fromUserUuid(user.getUserUuid()));
    }

    @Transactional
    public Response updateUserRegion(final UserRegionUpdateRequest request, final String userUuid){
        User user = userFindDao.findByUserUuId(userUuid);
        Region regionReference = regionFindDao.findRegionReference(request.getRegionCode());
        user.updateRegion(regionReference);
        return Response.of(UserCode.USER_REGION_UPDATE_SUCCESS);
    }

}

package com.GuardiansOfHoneyfruit.project.domain.user.controller;

import com.GuardiansOfHoneyfruit.project.domain.user.dao.UserFindDao;
import com.GuardiansOfHoneyfruit.project.domain.user.dto.UserRegionUpdateRequest;
import com.GuardiansOfHoneyfruit.project.domain.user.service.UserRegionService;
import com.GuardiansOfHoneyfruit.project.domain.user.service.UserUpdateService;
import com.GuardiansOfHoneyfruit.project.global.common.code.CommonCode;
import com.GuardiansOfHoneyfruit.project.global.common.dto.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserFindDao userFindDao;
    private final UserRegionService userRegionService;
    private final UserUpdateService userUpdateService;

    @GetMapping("")
    public ResponseEntity<Response> getUserInfo(@AuthenticationPrincipal OAuth2User oAuth2User){
        Response response = Response.of(CommonCode.GOOD_REQUEST, userFindDao.findByUserUuId(oAuth2User.getAttributes().get("userUuid").toString()));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/regions")
    public ResponseEntity<Response> getUserRegionDangerLevel(@AuthenticationPrincipal OAuth2User oAuth2User){
        Response response = userRegionService.getUserRegionDangerLevel(oAuth2User);
        return ResponseEntity.status(response.getStatus().getHttpStatus()).body(response);
    }

    @PostMapping("/regions")
    public ResponseEntity<Response> createUserRegion(@RequestBody UserRegionUpdateRequest request, @AuthenticationPrincipal OAuth2User user){
        user.getAttributes().get("userUuid");
        Response response = userUpdateService.createUserRegion(request, user.getName());
        return ResponseEntity.status(response.getStatus().getHttpStatus()).body(response);
    }

    @PatchMapping("/regions")
    public ResponseEntity<Response> updateUserRegion(@RequestBody UserRegionUpdateRequest request, @AuthenticationPrincipal OAuth2User user){
        Response response = userUpdateService.updateUserRegion(request, user.getName());
        return ResponseEntity.status(response.getStatus().getHttpStatus()).body(response);
    }

}

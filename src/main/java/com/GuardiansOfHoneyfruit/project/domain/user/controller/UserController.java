package com.GuardiansOfHoneyfruit.project.domain.user.controller;

import com.GuardiansOfHoneyfruit.project.domain.user.dao.UserFindDao;
import com.GuardiansOfHoneyfruit.project.domain.user.dto.UserRegionUpdateRequest;
import com.GuardiansOfHoneyfruit.project.domain.user.service.UserUpdateService;
import com.GuardiansOfHoneyfruit.project.global.common.code.CommonCode;
import com.GuardiansOfHoneyfruit.project.global.common.dto.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserFindDao userFindDao;
    private final UserUpdateService userUpdateService;

    @GetMapping("")
    public ResponseEntity<Response>getUserInfo(UserDetails userUuid){
        Response response = Response.of(CommonCode.GOOD_REQUEST, userFindDao.findByUserUuId(userUuid.getUsername()));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/regions")
    public ResponseEntity<Response> createUserRegion(@RequestBody UserRegionUpdateRequest request, OAuth2User user){
        Response response = userUpdateService.createUserRegion(request, user);
        return ResponseEntity.status(response.getStatus().getHttpStatus()).body(response);
    }

    @PatchMapping("/regions")
    public ResponseEntity<Response> updateUserRegion(@RequestBody UserRegionUpdateRequest request, OAuth2User user){
        Response response = userUpdateService.updateUserRegion(request, user);
        return ResponseEntity.status(response.getStatus().getHttpStatus()).body(response);
    }

}

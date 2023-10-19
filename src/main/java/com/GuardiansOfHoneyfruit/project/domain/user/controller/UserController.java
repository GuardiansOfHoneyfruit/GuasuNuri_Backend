package com.GuardiansOfHoneyfruit.project.domain.user.controller;

import com.GuardiansOfHoneyfruit.project.domain.user.dao.UserFindDao;
import com.GuardiansOfHoneyfruit.project.global.common.code.CommonCode;
import com.GuardiansOfHoneyfruit.project.global.common.dto.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserFindDao userFindDao;

    @GetMapping("/{userUuid}")
    public ResponseEntity<Response>getUserInfo(String userUuid){
        Response response = Response.of(CommonCode.GOOD_REQUEST, userFindDao.findByUserUuId(userUuid));
        return ResponseEntity.ok(response);
    }

}

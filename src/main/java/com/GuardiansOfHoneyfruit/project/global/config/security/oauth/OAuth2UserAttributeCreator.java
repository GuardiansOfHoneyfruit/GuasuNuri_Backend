package com.GuardiansOfHoneyfruit.project.global.config.security.oauth;

import com.GuardiansOfHoneyfruit.project.domain.user.domain.User;

import java.util.HashMap;
import java.util.Map;

public class OAuth2UserAttributeCreator {
    public static Map<String, Object> createAttribute(User user) {
        Map<String, Object> attribute = new HashMap<>();
        attribute.put("region", user.getRegion());
        attribute.put("userId", user.getUserId());
        attribute.put("userUuId", user.getUserUuid());
        attribute.put("email", user.getEmail());
        attribute.put("role", user.getRole());
        return attribute;
    }
}

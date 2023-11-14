package com.GuardiansOfHoneyfruit.project.global.config.security.oauth;

import com.GuardiansOfHoneyfruit.project.domain.user.domain.User;

import java.util.HashMap;
import java.util.Map;

public class OAuth2UserAttributeCreator {

    private static final String USER_UUID_ATTRIBUTE_NAME = "userUuid";
    private static final String USER_ID_ATTRIBUTE_NAME = "userId";
    private static final String USER_EMAIL_ATTRIBUTE_NAME = "email";
    private static final String USER_ROLE_ATTRIBUTE_NAME = "ROLE";
    private static final String USER_REGION_ATTRIBUTE_NAME = "region";

    public static Map<String, Object> createAttribute(User user) {
        Map<String, Object> attribute = new HashMap<>();
        attribute.put(USER_ID_ATTRIBUTE_NAME, user.getUserId());
        attribute.put(USER_UUID_ATTRIBUTE_NAME, user.getUserUuid());
        attribute.put(USER_EMAIL_ATTRIBUTE_NAME, user.getEmail());
        attribute.put(USER_ROLE_ATTRIBUTE_NAME, user.getRole());
        attribute.put(USER_REGION_ATTRIBUTE_NAME, user.getRegion().getRegionCode());
        return attribute;
    }
}

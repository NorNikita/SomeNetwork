package com.task.user.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.user.dto.CaptchaDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Slf4j
@Component
public class CaptchaUtil {

    @Value("${recaptcha.pulic}")
    private String publicKey;

    @Value("${recaptcha.secret}")
    private String privateKey;

    private static final String baseUrl = "https://www.google.com/recaptcha/api/siteverify";

    public boolean verificateCaptcha(String recaptchaToken) throws JsonProcessingException {
        log.info("verificate captcha, recaptchaTocken: {} ", recaptchaToken);

        String url = String.format(baseUrl + "?secret=%s&response=%s", privateKey, recaptchaToken);
        RestTemplate restTemplate = new RestTemplate();
        CaptchaDTO captchaDTO = restTemplate.postForObject(url, Collections.emptyList(), CaptchaDTO.class);

        log.info("response captchaDTO: {}", new ObjectMapper().writeValueAsString(captchaDTO));
        return captchaDTO.isSuccess();
    }

}

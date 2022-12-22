package com.oauthLogin.exam.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oauthLogin.exam.entity.OauthToken;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;


import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class MemberController {

   @GetMapping("/auth/kakao/callback")
    public String kakaoCallback(String code) {
       // POST 방식으로 key=value 데이터 요청 (카카오 쪽으로)
       // Retrofit2
       // OkHttp
       // RestTemplate
       RestTemplate rt = new RestTemplate();

       // HttpHeader 오브젝트 생성
       HttpHeaders headers = new HttpHeaders();
       headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        // REST API ID
       final String CLIENT_ID = "";
       // redirect uri
       final String REDIRECT_URI = "";

       // HttpBody 오브젝트 생성
       MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
       params.add("grant_type", "authorization_code");
       params.add("client_id", CLIENT_ID);
       params.add("redirect_uri", REDIRECT_URI);
       params.add("code", code);


       // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
       HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
               new HttpEntity<>(params, headers);

       // Http 요청하기 - POST 방식으로 - 그리고 response 변수의 응답을 받음 - 토큰 받기
       ResponseEntity response = rt.exchange(
              "https://kauth.kakao.com/oauth/token",
              HttpMethod.POST,
              kakaoTokenRequest,
               String.class
       );

       // Gson, Json Simple, ObjectMapper
      ObjectMapper objectMapper = new ObjectMapper();
      OauthToken oauthToken = null;
      try {
         oauthToken = objectMapper.readValue(response.getBody().toString(), OauthToken.class);
      } catch (JsonMappingException e) {
         e.printStackTrace();
      } catch (JsonProcessingException e) {
         e.printStackTrace();
      }

      System.out.println("kakao_access_token: " + oauthToken.getAccess_token());

        return response.getBody().toString();
    }

}

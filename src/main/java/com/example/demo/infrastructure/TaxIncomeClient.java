package com.example.demo.infrastructure;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.http.HttpClient;

@Component
public class TaxIncomeClient {

    public JSONObject callDataApi(String userId, String regNo) {
        return callScrapingApi(userId, regNo);
    }

    public JSONObject callScrapingApi(String userId, String regNo) {
        JSONObject result = new JSONObject();
        try {
            String apiUrl = "https://codetest-v4.3o3.co.kr/scrap";

            JSONObject body = new JSONObject();
            body.put("name", userId);
            body.put("regNo", regNo);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("X-API-KEY", "G9fSpitmtAeKzYJC7X0SwA==");

            HttpEntity<String> requestEntity = new HttpEntity<>(body.toString(), headers);

            SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
            factory.setConnectTimeout(20000);

            RestTemplate restTemplate = new RestTemplate(factory);
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                result = new JSONObject(responseEntity.getBody());
            } else {
                System.out.println("Error: " + responseEntity.getStatusCode());
            }
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return result;
    }

}

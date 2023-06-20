package com.lel.bookmingle.service;

import com.lel.bookmingle.dto.response.RecommendationApiResponse;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class RecommendationService {

    private final RestTemplate restTemplate;

    public RecommendationService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public RecommendationApiResponse getRecommendationList(List<String> userBooks) {
        try {
            String url = "http://127.0.0.1:5000/recommendations";

            Map<String, List<String>> map = new HashMap<>();
            map.put("books", userBooks);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            HttpEntity<Map<String, List<String>>> entity = new HttpEntity<>(map, httpHeaders);

            ResponseEntity<RecommendationApiResponse> response = restTemplate.postForEntity(url, entity, RecommendationApiResponse.class);

            if (!response.getStatusCode().equals(HttpStatus.OK) || Objects.isNull(response.getBody())) {
                return new RecommendationApiResponse(Collections.emptyList());
            }

            return response.getBody();

        } catch (Exception ignore) {
            return new RecommendationApiResponse(Collections.emptyList());
        }
    }

}

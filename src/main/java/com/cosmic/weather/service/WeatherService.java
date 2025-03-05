package com.cosmic.weather.service;

import com.cosmic.weather.model.WeatherResponse;
import com.cosmic.weather.model.ForecastResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

// actual API call
@Service
public class WeatherService {

    private final RestTemplate restTemplate;
    private final String apiKey;
    private final String baseUrl;

    @Autowired
    public WeatherService(
            RestTemplate restTemplate,
            @Value("${weather.api.key}") String apiKey,
            @Value("${weather.api.base-url}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
        this.baseUrl = baseUrl;
    }

    public WeatherResponse getCurrentWeather(String location) {
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl + "/current.json")
                .queryParam("key", apiKey)
                .queryParam("q", location)
                .queryParam("aqi", "no")
                .toUriString();

        return restTemplate.getForObject(url, WeatherResponse.class);
    }

    public ForecastResponse getForecast(String location, int days) {
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl + "/forecast.json")
                .queryParam("key", apiKey)
                .queryParam("q", location)
                .queryParam("days", days)
                .queryParam("aqi", "no")
                .toUriString();

        return restTemplate.getForObject(url, ForecastResponse.class);
    }

    public WeatherResponse getLocationByIp() {
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl + "/ip.json")
                .queryParam("key", apiKey)
                .queryParam("q", "auto")  // This tells the API to use the requester's IP
                .toUriString();

        return restTemplate.getForObject(url, WeatherResponse.class);
    }
}
package com.cosmic.weather;

import com.cosmic.weather.model.WeatherResponse;
import com.cosmic.weather.service.WeatherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

class WeatherServiceTest {

    private WeatherService weatherService;
    private MockRestServiceServer mockServer;
    private RestTemplate restTemplate;

    private static final String API_KEY = "test-api-key";
    private static final String BASE_URL = "http://api.weatherapi.com/v1";

    @BeforeEach
    void setUp() {
        restTemplate = new RestTemplate();
        mockServer = MockRestServiceServer.createServer(restTemplate);
        weatherService = new WeatherService(restTemplate, API_KEY, BASE_URL);
    }

    @Test
    void getCurrentWeather_shouldReturnWeatherData_whenValidLocationProvided() {
        // Arrange
        String location = "London";
        String expectedResponseBody =
                "{\"location\":{\"name\":\"London\",\"region\":\"City of London\",\"country\":\"United Kingdom\"," +
                        "\"lat\":51.52,\"lon\":-0.11,\"localtime\":\"2023-10-25 12:00\"}," +
                        "\"current\":{\"temp_c\":15.0,\"temp_f\":59.0,\"is_day\":1,\"condition\":{\"text\":\"Partly cloudy\",\"icon\":\"//cdn.weatherapi.com/weather/64x64/day/116.png\"}," +
                        "\"wind_mph\":5.6,\"wind_kph\":9.0,\"wind_dir\":\"SW\",\"humidity\":76,\"feelslike_c\":14.5,\"feelslike_f\":58.1}}";

        String expectedUrl = BASE_URL + "/current.json?key=" + API_KEY + "&q=" + location + "&aqi=no";

        mockServer.expect(requestTo(expectedUrl))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(expectedResponseBody, MediaType.APPLICATION_JSON));

        // Act
        WeatherResponse response = weatherService.getCurrentWeather(location);

        // Assert
        mockServer.verify();
        assertNotNull(response);
        assertEquals("London", response.getLocation().getName());
        assertEquals("United Kingdom", response.getLocation().getCountry());
        assertEquals(15.0, response.getCurrent().getTempC());
        assertEquals("Partly cloudy", response.getCurrent().getCondition().getText());
    }

    @Test
    void getCurrentWeather_shouldThrowException_whenApiReturnsError() {
        // Arrange
        String location = "InvalidLocation";
        String expectedUrl = BASE_URL + "/current.json?key=" + API_KEY + "&q=" + location + "&aqi=no";

        mockServer.expect(requestTo(expectedUrl))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.BAD_REQUEST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("{\"error\":{\"code\":1006,\"message\":\"No matching location found.\"}}"));

        // Act & Assert
        assertThrows(Exception.class, () -> weatherService.getCurrentWeather(location));
        mockServer.verify();
    }
}
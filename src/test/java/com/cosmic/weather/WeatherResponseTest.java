package com.cosmic.weather;

import com.cosmic.weather.model.WeatherResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WeatherResponseTest {

    @Test
    void weatherResponse_shouldHaveCorrectValues_whenInitialized() {
        // Arrange
        WeatherResponse weatherResponse = createTestWeatherResponse();

        // Assert
        assertEquals("London", weatherResponse.getLocation().getName());
        assertEquals("UK", weatherResponse.getLocation().getCountry());
        assertEquals(15.0, weatherResponse.getCurrent().getTempC());
        assertEquals("Partly cloudy", weatherResponse.getCurrent().getCondition().getText());
    }

    private WeatherResponse createTestWeatherResponse() {
        WeatherResponse weatherResponse = new WeatherResponse();
        weatherResponse.setLocation(createTestLocation());
        weatherResponse.setCurrent(createTestCurrent());
        return weatherResponse;
    }

    private WeatherResponse.Location createTestLocation() {
        WeatherResponse.Location location = new WeatherResponse.Location();
        location.setName("London");
        location.setCountry("UK");
        location.setLocalTime("Europe/London");
        location.setLat(51.5074);
        location.setLon(-0.1278);
        location.setRegion("Greater London");
        return location;
    }

    private WeatherResponse.Current createTestCurrent() {
        WeatherResponse.Current current = new WeatherResponse.Current();
        current.setTempC(15.0);
        current.setTempF(59.0);
        current.setIsDay(1);
        current.setWindMph(6.2);
        current.setWindKph(10.0);
        current.setWindDir("N");
        current.setHumidity(65);
        current.setFeelsLikeC(14.0);
        current.setFeelsLikeF(57.2);
        current.setCondition(createTestCondition());
        return current;
    }

    private WeatherResponse.Condition createTestCondition() {
        WeatherResponse.Condition condition = new WeatherResponse.Condition();
        condition.setText("Partly cloudy");
        condition.setIcon("//cdn.weatherapi.com/weather/64x64/day/116.png");
        return condition;
    }
}
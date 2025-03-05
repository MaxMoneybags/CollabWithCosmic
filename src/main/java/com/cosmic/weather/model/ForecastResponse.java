package com.cosmic.weather.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ForecastResponse {
    private WeatherResponse.Location location;
    private WeatherResponse.Current current;
    private Forecast forecast;

    @Data
    public static class Forecast {
        @JsonProperty("forecastday")
        private List<ForecastDay> forecastDays;
    }

    @Data
    public static class ForecastDay {
        private String date;
        @JsonProperty("date_epoch")
        private long dateEpoch;
        private Day day;
        private Astro astro;
        private List<Hour> hour;
    }

    @Data
    public static class Day {
        @JsonProperty("maxtemp_c")
        private double maxTempC;
        @JsonProperty("maxtemp_f")
        private double maxTempF;
        @JsonProperty("mintemp_c")
        private double minTempC;
        @JsonProperty("mintemp_f")
        private double minTempF;
        @JsonProperty("avgtemp_c")
        private double avgTempC;
        @JsonProperty("avgtemp_f")
        private double avgTempF;
        @JsonProperty("daily_chance_of_rain")
        private int dailyChanceOfRain;
        private WeatherResponse.Condition condition;
    }

    @Data
    public static class Astro {
        private String sunrise;
        private String sunset;
        private String moonrise;
        private String moonset;
        @JsonProperty("moon_phase")
        private String moonPhase;
    }

    @Data
    public static class Hour {
        @JsonProperty("time_epoch")
        private long timeEpoch;
        private String time;
        @JsonProperty("temp_c")
        private double tempC;
        @JsonProperty("temp_f")
        private double tempF;
        private WeatherResponse.Condition condition;
    }
}
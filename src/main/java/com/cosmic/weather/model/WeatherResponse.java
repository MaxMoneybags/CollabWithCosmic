package com.cosmic.weather.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class WeatherResponse {
    private Location location;
    private Current current;
    
    @Data
    public static class Location {
        private String name;
        private String region;
        private String country;
        private double lat;
        private double lon;
        @JsonProperty("localtime")
        private String localTime;

        public String getFormattedLocalTime() {
            try {
                DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd H:mm");
                LocalDateTime dateTime = LocalDateTime.parse(localTime, inputFormatter);
                DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a");
                return dateTime.format(outputFormatter);
            } catch (Exception e) {
                // Return original string if parsing fails
                return localTime;
            }
        }
    }
    
    @Data
    public static class Current {
        @JsonProperty("temp_c")
        private double tempC;
        @JsonProperty("temp_f")
        private double tempF;
        @JsonProperty("is_day")
        private int isDay;
        private Condition condition;
        @JsonProperty("wind_mph")
        private double windMph;
        @JsonProperty("wind_kph")
        private double windKph;
        @JsonProperty("wind_dir")
        private String windDir;
        @JsonProperty("humidity")
        private int humidity;
        @JsonProperty("feelslike_c")
        private double feelsLikeC;
        @JsonProperty("feelslike_f")
        private double feelsLikeF;
    }
    
    @Data
    public static class Condition {
        private String text;
        private String icon;
        private int code;
    }
}
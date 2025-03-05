package com.cosmic.weather.controller;

import com.cosmic.weather.model.WeatherResponse;
import com.cosmic.weather.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WeatherController {

    private final WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/weather")
    public String getWeather(@RequestParam(defaultValue = "London") String location, Model model) {
        try {
            WeatherResponse weatherResponse = weatherService.getCurrentWeather(location);
            model.addAttribute("weather", weatherResponse);
            model.addAttribute("location", location);
        } catch (Exception e) {
            model.addAttribute("error", "Failed to retrieve weather data: " + e.getMessage());
        }
        return "weather";
    }
}
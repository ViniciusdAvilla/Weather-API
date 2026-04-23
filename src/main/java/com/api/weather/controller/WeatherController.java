package com.api.weather.controller;

import com.api.weather.dto.geocoding.GeocodingResult;
import com.api.weather.dto.response.WeatherSummaryResponse;
import com.api.weather.service.WeatherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/api/weather")
    public WeatherSummaryResponse getWeather(@RequestParam String city) {
        return weatherService.getWeatherByCity(city);
    }

    @GetMapping("/api/cities")
    public List<GeocodingResult> listCities(@RequestParam String name) {
        return weatherService.listCities(name);
    }
}

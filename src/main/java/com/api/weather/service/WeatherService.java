package com.api.weather.service;

import com.api.weather.client.OpenMeteoClient;
import com.api.weather.dto.geocoding.GeocodingResponse;
import com.api.weather.dto.geocoding.GeocodingResult;
import com.api.weather.dto.response.WeatherSummaryResponse;
import com.api.weather.dto.weather.CurrentWeatherResponse;
import com.api.weather.exception.CityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class WeatherService {

    private final OpenMeteoClient openMeteoClient;

    public WeatherService(OpenMeteoClient openMeteoClient) {
        this.openMeteoClient = openMeteoClient;
    }

    public WeatherSummaryResponse getWeatherByCity(String city) {
        GeocodingResponse geocodingResponse = openMeteoClient.getCoordinates(city);

        if (geocodingResponse == null ||
                geocodingResponse.getResults() == null ||
                geocodingResponse.getResults().isEmpty()) {
            throw new CityNotFoundException("Cidade não encontrada: " + city);
        }

        GeocodingResult result = geocodingResponse.getResults().get(0);

        CurrentWeatherResponse weatherResponse =
                openMeteoClient.getCurrentWeather(result.getLatitude(), result.getLongitude());

        WeatherSummaryResponse response = new WeatherSummaryResponse();
        response.setCity(result.getName());
        response.setCountry(result.getCountry());
        response.setTime(weatherResponse.getCurrentWeather().getTime());
        response.setTemperature(weatherResponse.getCurrentWeather().getTemperature());
        response.setWindspeed(weatherResponse.getCurrentWeather().getWindspeed());
        response.setWeatherCode(weatherResponse.getCurrentWeather().getWeathercode());

        return response;
    }

    public List<GeocodingResult> listCities(String name) {
        GeocodingResponse response = openMeteoClient.searchCities(name);

        if (response == null || response.getResults() == null) {
            return Collections.emptyList();
        }

        return response.getResults();
    }
}

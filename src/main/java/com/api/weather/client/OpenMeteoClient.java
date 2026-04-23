package com.api.weather.client;

import com.api.weather.dto.geocoding.GeocodingResponse;
import com.api.weather.dto.weather.CurrentWeatherResponse;
import com.api.weather.exception.ExternalApiException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class OpenMeteoClient {

    private final RestTemplate restTemplate = new RestTemplate();

    public GeocodingResponse getCoordinates(String city) {
        try {
            String url = UriComponentsBuilder.fromUriString("https://geocoding-api.open-meteo.com/v1/search")
                    .queryParam("name", city)
                    .queryParam("count", 1)
                    .queryParam("language", "pt")
                    .queryParam("format", "json")
                    .toUriString();

            return restTemplate.getForObject(url, GeocodingResponse.class);
        } catch (Exception e) {
            throw new ExternalApiException("Erro ao consultar geocoding da Open-Meteo.");
        }
    }

    public GeocodingResponse searchCities(String name) {
        try {
            String url = UriComponentsBuilder.fromUriString("https://geocoding-api.open-meteo.com/v1/search")
                    .queryParam("name", name)
                    .queryParam("count", 10)
                    .queryParam("language", "pt")
                    .queryParam("format", "json")
                    .toUriString();

            return restTemplate.getForObject(url, GeocodingResponse.class);
        } catch (Exception e) {
            throw new ExternalApiException("Erro ao buscar cidades.");
        }
    }

    public CurrentWeatherResponse getCurrentWeather(Double latitude, Double longitude) {
        try {
            String url = UriComponentsBuilder.fromUriString("https://api.open-meteo.com/v1/forecast")
                    .queryParam("latitude", latitude)
                    .queryParam("longitude", longitude)
                    .queryParam("current_weather", true)
                    .toUriString();

            return restTemplate.getForObject(url, CurrentWeatherResponse.class);
        } catch (Exception e) {
            throw new ExternalApiException("Erro ao consultar clima atual da Open-Meteo.");
        }
    }
}
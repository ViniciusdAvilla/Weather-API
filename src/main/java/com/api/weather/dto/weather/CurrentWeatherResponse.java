package com.api.weather.dto.weather;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CurrentWeatherResponse {

    @JsonProperty("current_weather")
    private CurrentWeather currentWeather;

    public CurrentWeather getCurrentWeather() {
        return currentWeather;
    }

    public void setCurrentWeather(CurrentWeather currentWeather) {
        this.currentWeather = currentWeather;
    }
}

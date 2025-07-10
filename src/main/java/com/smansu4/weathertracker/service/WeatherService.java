package com.smansu4.weathertracker.service;


import com.smansu4.weathertracker.exception.CityNotFoundException;
import com.smansu4.weathertracker.exception.WeatherApiException;
import com.smansu4.weathertracker.model.WeatherResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public WeatherResponse getWeatherForCity(String city) {
        String url = String.format(
                "https://api.openweathermap.org/data/2.5/weather?q=%s&units=metric&appid=%s",
                city,
                apiKey
                );

        log.info("Retrieving weather for city: {}", city);

        try {
            return restTemplate.getForObject(url, WeatherResponse.class);
        } catch (HttpClientErrorException.NotFound e) {
            log.warn("City not found: {}", city);
            throw new CityNotFoundException(city);
        } catch (HttpClientErrorException e) {
            log.error("API error for city {}: {}", city, e.getMessage());
            throw new WeatherApiException("Weather API error: " + e.getStatusCode());
        } catch (Exception e) {
            log.error("Unexpected error while fetching weather: {}", e.getMessage());
            throw new WeatherApiException("Unexpected error occurred");
        }
    }
}

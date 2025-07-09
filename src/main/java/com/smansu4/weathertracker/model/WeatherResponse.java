package com.smansu4.weathertracker.model;

import lombok.Data;

@Data
public class WeatherResponse {

    private String name;
    private Main main;
    private Weather[] weather;

    @Data
    public static class Weather {
        private String main;
        private String description;
    }

    @Data
    public static class Main {
        private double temp;
        private int humidity;
    }
}

package com.smansu4.weathertracker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WeatherTrackerApplication {

    private static final Logger log = LoggerFactory.getLogger(WeatherTrackerApplication.class);

    public static void main(String[] args) {
        log.info("✅ Weather Tracker application is starting up!");
        SpringApplication.run(WeatherTrackerApplication.class, args);
    }

}

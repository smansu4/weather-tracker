# Weather Tracker API

This project retrieves weather data from OpenWeatherMap.org

AWS services used:
- AWS CloudWatch
- AWS ElasticBeanstalk

## OpenWeatherMap
- Obtain an api key from: https://openweathermap.org/api
  - once logged in, go to the dashboard and copy API key
- The free tier allows for ~60 requests/min


To test locally:
- Set the api key env variable 
- Run: `mvn spring-boot:run`
- Navigate to : `http://localhost:8080/weather/Chicago`


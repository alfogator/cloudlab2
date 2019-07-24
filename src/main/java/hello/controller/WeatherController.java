package hello.controller;

import hello.model.Event;
import hello.value.weatherresponse.WeatherResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import hello.value.weather.*;
import org.springframework.web.client.RestTemplate;

import javax.websocket.server.PathParam;

@RestController
public class WeatherController {

    private static final Logger log = LoggerFactory.getLogger(WeatherController.class);

    // GET /weather?city1=Milano,IT&city2=Venezia,IT
    @GetMapping("/weather")
    WeatherResponse getWeather(@RequestParam("city1") String city1, @RequestParam("city2") String city2) {
        RestTemplate restTemplate = new RestTemplate();

        final String appId = "8b6391440882b410dfd2c3e4018000c1";

        String url1 = "https://api.openweathermap.org/data/2.5/weather?q=" + city1 + "&appId=" + appId;
        String url2 = "https://api.openweathermap.org/data/2.5/weather?q=" + city2 + "&appId=" + appId;

        log.debug("Fetch url1: " + url1);
        log.debug("Fetch url2: " + url2);

        OpenWeather weather1 = restTemplate.getForObject(url1, OpenWeather.class);
        OpenWeather weather2 = restTemplate.getForObject(url2, OpenWeather.class);

        WeatherResponse response = new WeatherResponse();
        response.setFirstcity(weather1.getName());
        response.setSecondcity(weather2.getName());
        response.setFirsttemp(weather1.getMain().getTemp() - 273.15);
        response.setSecondtemp(weather2.getMain().getTemp() - 273.15);

        return response;
    }
}

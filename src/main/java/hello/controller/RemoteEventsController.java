package hello.controller;

import hello.errors.InvalidCountryCodeException;
import hello.model.Event;
import hello.repository.EventsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class RemoteEventsController {
    private static final Logger log = LoggerFactory.getLogger(RemoteEventsController.class);

    private Map<String, String> urls = new HashMap<String, String>();

    @Autowired
    EventsRepository eventsRepository;

    @GetMapping("/remote/events")
    public List<Event> fetchRemoteEvents() {
        RestTemplate restTemplate = new RestTemplate();

        // Single
        restTemplate.getForObject("http://localhost:8080/events/1", Event.class);

        // restTemplate.getForObject("http://localhost:8080/events", List<Event>.class);

        ResponseEntity<List<Event>> response = restTemplate.exchange(
                "http://localhost:8080/events/",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Event>>() {
                });
        List<Event> events = response.getBody();

        return events;
    }

    // /events/1
    // GET /remote/events/it/1
    @GetMapping("/remote/events/{country}")
    public List<Event> fetchCountryEvents(@PathVariable String country) {
        RestTemplate restTemplate = new RestTemplate();

        urls.put("us", "https://alfo.cloud-lab.it/hello/events");
        urls.put("fr", "https://pasquale.cloud-lab.it/hello/events");


        String url = urls.get(country);
        log.info("Using url " + url);

        if (url != null) {
            ResponseEntity<List<Event>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Event>>() {
                    });
            List<Event> events = response.getBody();
            return events;
        } else {
            throw new InvalidCountryCodeException();
        }
    }
}

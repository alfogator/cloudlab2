package hello.controller;

import hello.errors.EventNotFoundException;
import hello.model.Event;
import hello.repository.EventsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class EventsController {

    private static final Logger log = LoggerFactory.getLogger(EventsController.class);

    @Autowired
    EventsRepository eventsRepository;

    @GetMapping("/events")
    public List<Event> fetchEvents() {
        return eventsRepository.findAll();
    }

    @GetMapping("/events/{id}")
    public Event fetchEvent(@PathVariable long id) {
        Optional<Event> event = eventsRepository.findById(id);

            if (!event.isPresent())
                throw new EventNotFoundException();

        return event.get();
    }

    @DeleteMapping("/events/{id}")
    public void deleteEvent(@PathVariable long id) {
        eventsRepository.deleteById(id);
    }

    @PostMapping("/events")
    public ResponseEntity<Object> createEvent(@RequestBody Event event) {
        Event savedEvent = eventsRepository.save(event);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedEvent.getId()).toUri();

        return ResponseEntity.created(location).build();

    }

    @PutMapping("/events/{id}")
    public ResponseEntity<Object> updateEvent(@RequestBody Event event, @PathVariable long id) {

        Optional<Event> eventOptional = eventsRepository.findById(id);

        if (!eventOptional.isPresent())
            return ResponseEntity.notFound().build();

        event.setId(id);

        eventsRepository.save(event);

        return ResponseEntity.noContent().build();
    }

}


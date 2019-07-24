package hello.controller;

import hello.errors.VenueNotFoundException;
import hello.model.Venue;
import hello.repository.VenuesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class VenuesController {

    @Autowired
    VenuesRepository venuesRepository;

    @GetMapping("/venues")
    public List<Venue> fetchEvents() {
        return venuesRepository.findAll();
    }

    @GetMapping("/venues/{id}")
    public Venue fetchEvent(@PathVariable long id) {
        Optional<Venue> event = venuesRepository.findById(id);

        if (!event.isPresent())
            throw new VenueNotFoundException();

        return event.get();
    }

    @DeleteMapping("/venues/{id}")
    public void deleteEvent(@PathVariable long id) {
        venuesRepository.deleteById(id);
    }

    @PostMapping("/venues")
    public ResponseEntity<Object> createEvent(@RequestBody Venue event) {
        Venue savedEvent = venuesRepository.save(event);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedEvent.getId()).toUri();

        return ResponseEntity.created(location).build();

    }

    @PutMapping("/venues/{id}")
    public ResponseEntity<Object> updateEvent(@RequestBody Venue event, @PathVariable long id) {

        Optional<Venue> eventOptional = venuesRepository.findById(id);

        if (!eventOptional.isPresent())
            return ResponseEntity.notFound().build();

        event.setId(id);

        venuesRepository.save(event);

        return ResponseEntity.noContent().build();
    }

}

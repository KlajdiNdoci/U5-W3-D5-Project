package KlajdiNdoci.U5W3D5Project.controllers;

import KlajdiNdoci.U5W3D5Project.entities.Event;
import KlajdiNdoci.U5W3D5Project.exceptions.BadRequestException;
import KlajdiNdoci.U5W3D5Project.payloads.NewEventDTO;
import KlajdiNdoci.U5W3D5Project.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {
    @Autowired
    private EventService eventService;

    @GetMapping("")
    public Page<Event> getEvents(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 @RequestParam(defaultValue = "id") String orderBy) {
        return eventService.getEvents(page, size, orderBy);
    }

    @GetMapping("/{id}")
    public Event findById(@PathVariable long id) {
        return eventService.findById(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable long id) {
        eventService.findByIdAndDelete(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Event findByIdAndUpdate(@PathVariable long id, @RequestBody @Validated NewEventDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        } else {
            return eventService.findByIdAndUpdate(id, body);
        }
    }

    @GetMapping("/myevents")
    public List<Event> getMyEvents(@AuthenticationPrincipal List<Event> myEvents) {
        return myEvents;
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('ORGANIZER')")
    @ResponseStatus(HttpStatus.CREATED)
    public Event createEvent(@RequestBody @Validated NewEventDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        } else {
            return eventService.createEvent(body);
        }
    }

}

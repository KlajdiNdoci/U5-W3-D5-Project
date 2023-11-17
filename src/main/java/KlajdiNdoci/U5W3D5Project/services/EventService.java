package KlajdiNdoci.U5W3D5Project.services;

import KlajdiNdoci.U5W3D5Project.entities.Event;
import KlajdiNdoci.U5W3D5Project.entities.User;
import KlajdiNdoci.U5W3D5Project.enums.EventAvailability;
import KlajdiNdoci.U5W3D5Project.exceptions.NotFoundException;
import KlajdiNdoci.U5W3D5Project.exceptions.UnauthorizedException;
import KlajdiNdoci.U5W3D5Project.payloads.NewEventDTO;
import KlajdiNdoci.U5W3D5Project.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    @Autowired
    private UserService userService;

    @Autowired
    private EventRepository eventRepository;

    public Page<Event> getEvents(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return eventRepository.findAll(pageable);
    }

    public Event createEvent(NewEventDTO body, User currentUser) {
        Event newEvent = new Event();
        newEvent.setTitle(body.title());
        newEvent.setDescription(body.description());
        newEvent.setDate(body.date());
        newEvent.setLocality(body.locality());
        newEvent.setSeats(body.seats());
        newEvent.setAvailability(EventAvailability.AVAILABLE);
        newEvent.setCreator(currentUser);

        return eventRepository.save(newEvent);
    }

    public Event findById(long id) throws NotFoundException {
        return eventRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public void findByIdAndDelete(long id) throws NotFoundException {
        Event foundEvent = findById(id);
        eventRepository.delete(foundEvent);
    }

    public void findByIdAndDeleteMyEvent(long id, User currentUser) throws NotFoundException {
        Event foundEvent = findById(id);
        if (currentUser.getId() != foundEvent.getCreator().getId()) {
            throw new UnauthorizedException("You don't have the authorization to delete this event");
        }
        eventRepository.delete(foundEvent);
    }

    public Event findByIdAndUpdate(long id, NewEventDTO body) throws NotFoundException {
        Event foundEvent = findById(id);
        foundEvent.setTitle(body.title());
        foundEvent.setDescription(body.description());
        foundEvent.setDate(body.date());
        foundEvent.setLocality(body.locality());
        foundEvent.setSeats(body.seats());
        return eventRepository.save(foundEvent);
    }

    public List<Event> findEventsByCreator(User currentUser) {
        return eventRepository.findByCreator(currentUser);
    }

}

package KlajdiNdoci.U5W3D5Project.services;

import KlajdiNdoci.U5W3D5Project.entities.Event;
import KlajdiNdoci.U5W3D5Project.exceptions.NotFoundException;
import KlajdiNdoci.U5W3D5Project.payloads.NewEventDTO;
import KlajdiNdoci.U5W3D5Project.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public Event createEvent(NewEventDTO body) {
        Event newEvent = new Event();
        newEvent.setTitle(body.title());
        newEvent.setDescription(body.description());
        newEvent.setDate(body.date());
        newEvent.setLocality(body.locality());
        newEvent.setSeats(body.seats());
        return eventRepository.save(newEvent);
    }

    public Event findById(long id) throws NotFoundException {
        return eventRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public void findByIdAndDelete(long id) throws NotFoundException {
        Event foundEvent = findById(id);
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
}

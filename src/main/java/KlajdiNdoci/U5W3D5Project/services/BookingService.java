package KlajdiNdoci.U5W3D5Project.services;

import KlajdiNdoci.U5W3D5Project.entities.Booking;
import KlajdiNdoci.U5W3D5Project.entities.Event;
import KlajdiNdoci.U5W3D5Project.entities.User;
import KlajdiNdoci.U5W3D5Project.enums.EventAvailability;
import KlajdiNdoci.U5W3D5Project.exceptions.BadRequestException;
import KlajdiNdoci.U5W3D5Project.exceptions.NotFoundException;
import KlajdiNdoci.U5W3D5Project.repositories.BookingRepository;
import KlajdiNdoci.U5W3D5Project.repositories.EventRepository;
import KlajdiNdoci.U5W3D5Project.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    public Page<Booking> getBookings(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return bookingRepository.findAll(pageable);
    }

    public Booking createBooking(User user, Event event, int numberOfSeats) {
        if (event.getAvailability() == EventAvailability.SOLD_OUT) {
            throw new BadRequestException("The event is sold out.");
        }

        int availableSeats = event.getSeats() - event.getBookings().stream().mapToInt(Booking::getNumberOfSeats).sum();

        if (numberOfSeats > availableSeats) {
            throw new BadRequestException("Not enough available seats.");
        }

        Booking booking = new Booking(user, event, numberOfSeats);
        event.getBookings().add(booking);
        event.updateAvailability();
        eventRepository.save(event);
        return bookingRepository.save(booking);
    }

    public List<Booking> getUserBookings(User user) {
        return bookingRepository.findByUser(user);
    }

    public List<Booking> getEventBookings(Event event) {
        return bookingRepository.findByEvent(event);
    }

    public void cancelBooking(User user, Event event) {
        Booking booking = bookingRepository.findByUserAndEvent(user, event).orElseThrow(() -> new NotFoundException("Booking not found for the user and event."));
        event.getBookings().remove(booking);
        event.updateAvailability();
        eventRepository.save(event);
        bookingRepository.delete(booking);
    }
}

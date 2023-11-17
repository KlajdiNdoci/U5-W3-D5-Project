package KlajdiNdoci.U5W3D5Project.services;

import KlajdiNdoci.U5W3D5Project.entities.Booking;
import KlajdiNdoci.U5W3D5Project.entities.Event;
import KlajdiNdoci.U5W3D5Project.entities.User;
import KlajdiNdoci.U5W3D5Project.enums.EventAvailability;
import KlajdiNdoci.U5W3D5Project.exceptions.BadRequestException;
import KlajdiNdoci.U5W3D5Project.exceptions.NotFoundException;
import KlajdiNdoci.U5W3D5Project.exceptions.UnauthorizedException;
import KlajdiNdoci.U5W3D5Project.payloads.NewBookingDTO;
import KlajdiNdoci.U5W3D5Project.repositories.BookingRepository;
import KlajdiNdoci.U5W3D5Project.repositories.EventRepository;
import KlajdiNdoci.U5W3D5Project.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;

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

    public Booking createBooking(NewBookingDTO body, User currentUser) throws IOException {
        Event foundEvent = eventRepository.findById(body.eventId()).orElseThrow(() -> new NotFoundException(body.eventId()));
        if (foundEvent.getAvailability() == EventAvailability.SOLD_OUT) {
            throw new BadRequestException("The event is sold out.");
        }

        int availableSeats = foundEvent.getSeats() - foundEvent.getBookings().stream().mapToInt(Booking::getNumberOfSeats).sum();

        if (body.numberOfSeats() > availableSeats) {
            throw new BadRequestException("Not enough available seats.");
        }

        Booking booking = new Booking(currentUser, foundEvent, body.numberOfSeats());
        foundEvent.getBookings().add(booking);
        foundEvent.updateAvailability();
        eventRepository.save(foundEvent);
        return bookingRepository.save(booking);
    }


    public void cancelBooking(User currentUser, Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new NotFoundException(bookingId));
        if (currentUser.getId() != booking.getUser().getId()) {
            throw new UnauthorizedException("You don't have the authorization to cancel this booking");
        }

        bookingRepository.delete(booking);
    }

    public Booking findById(long id) throws NotFoundException {
        Booking found = null;
        for (Booking booking : bookingRepository.findAll()) {
            if (booking.getId() == id) {
                found = booking;
            }
        }
        if (found == null) {
            throw new NotFoundException(id);
        } else {
            return found;
        }
    }
}

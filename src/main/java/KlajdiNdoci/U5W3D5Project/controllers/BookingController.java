package KlajdiNdoci.U5W3D5Project.controllers;

import KlajdiNdoci.U5W3D5Project.entities.Booking;
import KlajdiNdoci.U5W3D5Project.entities.Event;
import KlajdiNdoci.U5W3D5Project.entities.User;
import KlajdiNdoci.U5W3D5Project.payloads.NewBookingDTO;
import KlajdiNdoci.U5W3D5Project.services.BookingService;
import KlajdiNdoci.U5W3D5Project.services.EventService;
import KlajdiNdoci.U5W3D5Project.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private UserService userService;

    @Autowired
    private EventService eventService;

    @GetMapping("")
    public Page<Booking> getBookings(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size,
                                     @RequestParam(defaultValue = "id") String orderBy) {
        return bookingService.getBookings(page, size, orderBy);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Booking createBooking(@RequestBody @Validated NewBookingDTO body,
                                 @AuthenticationPrincipal User currentUser) {
        User user = userService.findByEmail(currentUser.getUsername());
        Event event = eventService.findById(body.eventId());
        int numberOfSeats = body.numberOfSeats();

        return bookingService.createBooking(user, event, numberOfSeats);
    }

    @GetMapping("/user")
    public List<Booking> getUserBookings(@AuthenticationPrincipal User currentUser) {
        User user = userService.findByEmail(currentUser.getUsername());
        return bookingService.getUserBookings(user);
    }

    @GetMapping("/event/{eventId}")
    public List<Booking> getEventBookings(@PathVariable long eventId) {
        Event event = eventService.findById(eventId);
        return bookingService.getEventBookings(event);
    }

    @DeleteMapping("/cancel/{eventId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelBooking(@PathVariable long eventId,
                              @AuthenticationPrincipal User currentUser) {
        User user = userService.findByEmail(currentUser.getUsername());
        Event event = eventService.findById(eventId);
        bookingService.cancelBooking(user, event);
    }
}

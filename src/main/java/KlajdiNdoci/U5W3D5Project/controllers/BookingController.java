package KlajdiNdoci.U5W3D5Project.controllers;

import KlajdiNdoci.U5W3D5Project.entities.Booking;
import KlajdiNdoci.U5W3D5Project.entities.User;
import KlajdiNdoci.U5W3D5Project.exceptions.BadRequestException;
import KlajdiNdoci.U5W3D5Project.payloads.NewBookingDTO;
import KlajdiNdoci.U5W3D5Project.repositories.EventRepository;
import KlajdiNdoci.U5W3D5Project.services.BookingService;
import KlajdiNdoci.U5W3D5Project.services.EventService;
import KlajdiNdoci.U5W3D5Project.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private UserService userService;

    @Autowired
    private EventService eventService;
    @Autowired
    private EventRepository eventRepository;

    @GetMapping("")
    public Page<Booking> getBookings(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size,
                                     @RequestParam(defaultValue = "id") String orderBy) {
        return bookingService.getBookings(page, size, orderBy);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Booking createBooking(@RequestBody @Validated NewBookingDTO body,
                                 @AuthenticationPrincipal User currentUser,
                                 BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        } else {
            try {
                return bookingService.createBooking(body, currentUser);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @DeleteMapping("/mybookings/{bookingId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelBooking(@PathVariable Long bookingId, @AuthenticationPrincipal User currentUser) {
        bookingService.cancelBooking(currentUser, bookingId);
    }

}

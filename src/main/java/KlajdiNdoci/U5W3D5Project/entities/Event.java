package KlajdiNdoci.U5W3D5Project.entities;

import KlajdiNdoci.U5W3D5Project.enums.EventAvailability;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    private String title;

    private String description;

    private LocalDate date;

    private String locality;

    private int seats;

    private String image;

    @Enumerated(EnumType.STRING)
    private EventAvailability availability;

    @OneToMany(mappedBy = "event")
    @JsonIgnore
    @ToString.Exclude
    private List<Booking> bookings;


    public Event(String title, String description, LocalDate date, String locality, int seats, List<Booking> bookings, String image) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.locality = locality;
        this.seats = seats;
        this.image = image;
        this.bookings = bookings;
        updateAvailability();
    }

    public void updateAvailability() {
        int totalSeats = getSeats();
        int bookedSeats = bookings.stream().mapToInt(Booking::getNumberOfSeats).sum();

        if (bookedSeats >= totalSeats) {
            setAvailability(EventAvailability.SOLD_OUT);
        } else {
            setAvailability(EventAvailability.AVAILABLE);
        }
    }
}

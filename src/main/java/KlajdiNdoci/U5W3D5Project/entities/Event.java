package KlajdiNdoci.U5W3D5Project.entities;

import KlajdiNdoci.U5W3D5Project.enums.EventAvailability;
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

    @Enumerated(EnumType.STRING)
    private EventAvailability availability;

    @ManyToMany(mappedBy = "events")
    @ToString.Exclude
    private List<User> users;

    public Event(String title, String description, LocalDate date, String locality, int seats, List<User> users) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.locality = locality;
        this.seats = seats;
        this.users = users;
        updateAvailability();
    }

    public void updateAvailability() {
        if (users.size() >= seats) {
            availability = EventAvailability.SOLD_OUT;
        } else {
            availability = EventAvailability.AVAILABLE;
        }
    }
}

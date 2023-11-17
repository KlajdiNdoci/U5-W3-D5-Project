package KlajdiNdoci.U5W3D5Project.repositories;

import KlajdiNdoci.U5W3D5Project.entities.Booking;
import KlajdiNdoci.U5W3D5Project.entities.Event;
import KlajdiNdoci.U5W3D5Project.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUser(User user);

    List<Booking> findByEvent(Event event);

    Optional<Booking> findByUserAndEvent(User user, Event event);

}

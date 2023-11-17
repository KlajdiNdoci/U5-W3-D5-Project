package KlajdiNdoci.U5W3D5Project.repositories;

import KlajdiNdoci.U5W3D5Project.entities.Event;
import KlajdiNdoci.U5W3D5Project.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByCreator(User user);
}

package KlajdiNdoci.U5W3D5Project.repositories;

import KlajdiNdoci.U5W3D5Project.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
}

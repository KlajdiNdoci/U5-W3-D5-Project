package KlajdiNdoci.U5W3D5Project.repositories;

import KlajdiNdoci.U5W3D5Project.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
}

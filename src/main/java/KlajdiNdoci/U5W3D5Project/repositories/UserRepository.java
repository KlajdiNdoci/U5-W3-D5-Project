package KlajdiNdoci.U5W3D5Project.repositories;

import KlajdiNdoci.U5W3D5Project.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}

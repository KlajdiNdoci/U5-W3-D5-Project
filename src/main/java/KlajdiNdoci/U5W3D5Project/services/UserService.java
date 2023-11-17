package KlajdiNdoci.U5W3D5Project.services;

import KlajdiNdoci.U5W3D5Project.entities.User;
import KlajdiNdoci.U5W3D5Project.enums.UserRole;
import KlajdiNdoci.U5W3D5Project.exceptions.NotFoundException;
import KlajdiNdoci.U5W3D5Project.payloads.NewUserDTO;
import KlajdiNdoci.U5W3D5Project.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Page<User> getUsers(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return userRepository.findAll(pageable);
    }



    public User findById(long id) throws NotFoundException {
        User found = null;
        for (User user : userRepository.findAll()) {
            if (user.getId() == id) {
                found = user;
            }
        }
        if (found == null) {
            throw new NotFoundException(id);
        } else {
            return found;
        }
    }

    public void findByIdAndDelete(long id)throws NotFoundException {
        User found = this.findById(id);
        userRepository.delete(found);
    }

    public User findByIdAndUpdate(long id, NewUserDTO body) throws NotFoundException{
        User found = this.findById(id);
        found.setUsername(body.username());
        found.setEmail(body.email());
        found.setSurname(body.surname());
        found.setName(body.name());
        found.setRole(UserRole.BASIC);
        found.setPassword(body.password());
        return userRepository.save(found);
    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(()-> new NotFoundException("User with email " + email + "not found"));
    }

}

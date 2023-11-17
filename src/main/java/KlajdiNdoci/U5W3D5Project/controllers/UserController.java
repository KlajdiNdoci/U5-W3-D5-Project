package KlajdiNdoci.U5W3D5Project.controllers;

import KlajdiNdoci.U5W3D5Project.entities.User;
import KlajdiNdoci.U5W3D5Project.exceptions.BadRequestException;
import KlajdiNdoci.U5W3D5Project.payloads.NewUserDTO;
import KlajdiNdoci.U5W3D5Project.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;


    @GetMapping("")
    public Page<User> getUser(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int size,
                              @RequestParam(defaultValue = "id") String orderBy){
        return userService.getUsers(page, size, orderBy);
    }


    @GetMapping("/{id}")
    public User findById(@PathVariable long id) {
        return userService.findById(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable long id) {
        userService.findByIdAndDelete(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public User findByIdAndUpdate(@PathVariable long id, @RequestBody @Validated NewUserDTO body, BindingResult validation) {
        if (validation.hasErrors()){
            throw new BadRequestException(validation.getAllErrors());
        }else {
            return userService.findByIdAndUpdate(id, body);
        }
    }


    @GetMapping("/me")
    public UserDetails getProfile(@AuthenticationPrincipal UserDetails currentUser){
        return currentUser;
    }

    @PutMapping("/me")
    public UserDetails updateProfile(@AuthenticationPrincipal User currentUser, @RequestBody NewUserDTO body){
        return userService.findByIdAndUpdate(currentUser.getId(), body);
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProfile(@AuthenticationPrincipal User currentUser){
        userService.findByIdAndDelete(currentUser.getId());
    }

    }

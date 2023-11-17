package KlajdiNdoci.U5W3D5Project.controllers;

import KlajdiNdoci.U5W3D5Project.entities.User;
import KlajdiNdoci.U5W3D5Project.exceptions.BadRequestException;
import KlajdiNdoci.U5W3D5Project.payloads.NewUserDTO;
import KlajdiNdoci.U5W3D5Project.payloads.UserLoginDTO;
import KlajdiNdoci.U5W3D5Project.payloads.UserLoginSuccessDTO;
import KlajdiNdoci.U5W3D5Project.services.AuthService;
import KlajdiNdoci.U5W3D5Project.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User saveUser(@RequestBody @Validated NewUserDTO body, BindingResult validation){
        if (validation.hasErrors()){
            throw new BadRequestException(validation.getAllErrors());
        }else {
            try {
                return authService.saveUser(body);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @PostMapping("/login")
    public UserLoginSuccessDTO login(@RequestBody UserLoginDTO body){
        return new UserLoginSuccessDTO(authService.authenticateUser(body));
    }
}

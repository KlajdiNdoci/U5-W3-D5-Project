package KlajdiNdoci.U5W3D5Project.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record NewUserDTO(
        @NotEmpty(message = "You have to enter a username!")
        @Size(min = 3, max= 20, message = "The username must have between 3 and 20 characters")
        String username,
        @NotEmpty(message = "You have to enter a name!")
        @Size(min = 3, max= 20, message = "The name must have between 3 and 20 characters")
        String name,
        @NotEmpty(message = "You have to enter a surname!" )
        @Size(min = 3, max= 20, message = "The surname must have between 3 and 20 characters")
        String surname,

        @NotEmpty(message = "You have to enter an email!")
        @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "The email is not valid")
        String email,

        @NotEmpty(message = "you have to enter a password")
        @Size(min = 8, max = 20, message = "The password must have between 8 and 20 characters")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "The password must contain at least one lowercase letter, one uppercase letter, and one digit")
        String password

) {

}

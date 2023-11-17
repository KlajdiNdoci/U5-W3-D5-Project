package KlajdiNdoci.U5W3D5Project.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record UserLoginDTO(
        @NotEmpty(message = "You have to enter an email!")
        @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "The email is not valid")
        String email,
        @NotEmpty
        String password
) {
}

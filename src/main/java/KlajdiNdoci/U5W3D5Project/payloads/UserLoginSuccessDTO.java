package KlajdiNdoci.U5W3D5Project.payloads;

import jakarta.validation.constraints.NotEmpty;

public record UserLoginSuccessDTO(
        @NotEmpty
        String accessToken
) {
}

package KlajdiNdoci.U5W3D5Project.payloads;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record NewBookingDTO(
        @NotNull(message = "Event ID cannot be null")
        Long eventId,
        @NotNull(message = "The number of seats cannot be null")
        @Min(value = 1, message = "Number of seats must be at least 1")
        Integer numberOfSeats
) {
}

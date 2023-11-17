package KlajdiNdoci.U5W3D5Project.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record NewEventDTO(
        @NotEmpty(message = "Title cannot be empty")
        @Size(min = 3, max = 30, message = "Title must have between 3 and 30 characters")
        String title,

        @NotEmpty(message = "Description cannot be empty")
        @Size(min = 3, max = 1000, message = "Description must have between 3 and 1000 characters")
        String description,

        @NotNull(message = "Date cannot be null")
        LocalDate date,

        @NotEmpty(message = "Locality cannot be empty")
        String locality,

        @NotNull(message = "Number of seats cannot be null")
        Integer seats
) {
}

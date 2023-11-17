package KlajdiNdoci.U5W3D5Project.payloads;

import java.util.Date;
import java.util.List;

public record ErrorsResponseWithListDTO(String message, Date timestamp, List<String> errorsList) {}


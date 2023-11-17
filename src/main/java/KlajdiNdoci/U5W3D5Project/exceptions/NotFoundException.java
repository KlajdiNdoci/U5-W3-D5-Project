package KlajdiNdoci.U5W3D5Project.exceptions;

public class NotFoundException extends RuntimeException {

    public NotFoundException(long id) {
        super("Element with id " + id + " not found!");
    }
    public NotFoundException(String message) {
        super(message);
    }
}

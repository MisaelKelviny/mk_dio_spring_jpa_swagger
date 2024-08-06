package one.dio.parking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ParkingNotFoundExceptions extends RuntimeException {
    public ParkingNotFoundExceptions(String id) {
        super("Parking not found with Id: " + id);
    }
}
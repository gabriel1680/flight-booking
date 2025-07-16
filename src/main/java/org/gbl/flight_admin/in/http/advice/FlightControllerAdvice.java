package org.gbl.flight_admin.in.http.advice;

import org.gbl.shared.application.ApplicationException;
import org.gbl.shared.application.NotFoundException;
import org.gbl.shared.domain.DomainException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class FlightControllerAdvice {

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<String> domain(DomainException e) {
        return ResponseEntity.unprocessableEntity().body(e.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> notFound(NotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<String> application(ApplicationException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}

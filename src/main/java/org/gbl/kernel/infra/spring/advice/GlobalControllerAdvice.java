package org.gbl.kernel.infra.spring.advice;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.gbl.kernel.application.ApplicationException;
import org.gbl.kernel.application.NotFoundException;
import org.gbl.kernel.domain.DomainException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerAdvice {

    private static final Logger LOG = LogManager.getLogger(GlobalControllerAdvice.class);

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

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleAnyException(Exception ex) {
        LOG.error(ex.getMessage(), ex.getCause());
        final var internalServerError = HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(internalServerError).body(internalServerError.toString());
    }
}

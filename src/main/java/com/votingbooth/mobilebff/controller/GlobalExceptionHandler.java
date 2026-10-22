package com.votingbooth.mobilebff.controller;

import com.votingbooth.mobilebff.model.exception.InvalidPageException;
import com.votingbooth.mobilebff.model.exception.RepresentativeNotFoundException;
import com.votingbooth.mobilebff.model.exception.UpstreamUnavailableException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({InvalidPageException.class})
    public ProblemDetail handleBadRequest(RuntimeException exc) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exc.getMessage());
    }

    @ExceptionHandler({RepresentativeNotFoundException.class})
    public ProblemDetail handleNotFound(RuntimeException exc) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exc.getMessage());
    }

    @ExceptionHandler({UpstreamUnavailableException.class})
    public ProblemDetail handleBadGateway(RuntimeException exc) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_GATEWAY, exc.getMessage());
    }

}

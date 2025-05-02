package com.example.wallet.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleSecurityException(Exception exception) {
        ProblemDetail errorDetail = null;
        log.error("Error", exception);

        if (exception instanceof BadRequestException || exception instanceof MethodArgumentNotValidException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), exception.getMessage());
            errorDetail.setProperty("Description", "Invalid request content");
            return errorDetail;
        }

        if (exception instanceof NoResourceFoundException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(404), exception.getLocalizedMessage());
            errorDetail.setProperty("Description", "Request to the wrong path or with an error in the URL");
        }
        
        if (exception instanceof NotFoundException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(404), exception.getMessage());
            errorDetail.setProperty("Description", "The Wallet doesn't exist");
        }

        if (exception instanceof MethodNotAllowedException || exception instanceof HttpRequestMethodNotSupportedException
                || exception instanceof MethodArgumentTypeMismatchException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(405), exception.getLocalizedMessage());
            errorDetail.setProperty("Description", "Method is not supported at this URL or the HTTP method is not correct when requested");
        }

        if (exception instanceof IncorrectDataEntryException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(409), exception.getMessage());
            errorDetail.setProperty("Description", "Incorrect data entry");
        }

        return errorDetail;
    }
}

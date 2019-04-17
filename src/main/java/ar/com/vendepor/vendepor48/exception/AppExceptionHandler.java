package ar.com.vendepor.vendepor48.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(MvcException.class)
    public String handleCommonException(HttpServletRequest request, Exception ex){

        request.setAttribute("error", ex.getMessage());

        return "error";
    }

    @ExceptionHandler(RestException.class)
    public ResponseEntity<ExceptionResponse> handleRestException(RestException ex,
                                                                                   WebRequest request) {

        ExceptionResponse exceptionResponse = new ExceptionResponse(
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity(exceptionResponse, HttpStatus.UNAUTHORIZED);
    }

}

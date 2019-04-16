package ar.com.vendepor.vendepor48.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class SignUpTokenExceptionHandler {

    @ExceptionHandler(SignUpTokenException.class)
    public String handlePaymentLinkException(HttpServletRequest request, Exception ex){

        request.setAttribute("error", ex.getMessage());

        return "error";
    }

}

package ar.com.vendepor.vendepor48.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class ExceptionResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT-03:00")
    private Date timestamp;

    private String message;
    private String details;

    public ExceptionResponse() { }

    public ExceptionResponse(String message) {
        this.message = message;
    }

    public ExceptionResponse(String message, String details) {
        this.timestamp = new Date();
        this.message = message;
        this.details = details;
    }

    public ExceptionResponse(Date timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}

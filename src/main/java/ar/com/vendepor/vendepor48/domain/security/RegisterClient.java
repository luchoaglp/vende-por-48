package ar.com.vendepor.vendepor48.domain.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class RegisterClient {

    @JsonProperty("first_name")
    @NotBlank(message = "{firstName.notblank}")
    @Size(min = 2, max = 50, message =  "{firstName.size}")
    private String firstName;

    @JsonProperty("last_name")
    @NotBlank(message = "{lastName.notblank}")
    @Size(min = 2, max = 50, message =  "{lastName.size}")
    private String lastName;

}

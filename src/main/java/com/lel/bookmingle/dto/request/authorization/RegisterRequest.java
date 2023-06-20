package com.lel.bookmingle.dto.request.authorization;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequest(

        @NotBlank(message = "Name Can Not be Empty")
        String name,
        @NotBlank(message = "Surname Can Not be Empty")
        String surname,
//        @NotBlank(message = "Birth Date Can Not be Empty")
//        @Pattern(regexp="^[0-9]{2}/[0-9]{2}/[0-9]{4}", message = "Enter dd/MM/yyyy date!")
        String birthDate,
        @NotBlank(message = "Phone number can not be blank!")
        @Size(min = 10, max = 10, message = "Please enter 10 digits!")
        @Pattern(regexp = "^[0-9]{10}", message = "Number Must Contain Only Digits!")
        String phoneNumber,
        @NotBlank(message = "Email address can not be blank!")
        @Email(message = "Please enter valid email address!")
        String email,
        @NotBlank(message = "Password Can Not Be Empty")
        String password

) {
}

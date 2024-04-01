package com.ouharri.pharma.model.dto.requests;

import com.ouharri.pharma.model.dto.basic.AddressDto;
import com.ouharri.pharma.model.entities.User;
import com.ouharri.pharma.model.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * DTO for creating or updating a {@link User} entity.
 * Represents a user request with details such as password, image, phone number,
 * email, first name, last name, gender, and address.
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest implements _Request {
    private String password;
    private String image;
    @Pattern(message = "Phone number must match the format '0XXXXXXXXX'", regexp = "0\\d{9}")
    private String phoneNumber;
    @Size(message = "Email is too long", max = 80)
    @Email(message = "Email was not provided")
    private String email;
    @NotNull(message = "FirstName must be present")
    @Size(message = "Firstname cannot be empty", min = 1)
    private String firstname;
    @Size(message = "Lastname is too long", max = 30)
    private String lastname;
    private Gender gender;
    private AddressDto address;
}
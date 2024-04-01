package com.ouharri.pharma.model.dto.requests;

import com.ouharri.pharma.model.dto.responses.UserResponsesDto;
import com.ouharri.pharma.model.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ChangeRoleRequest(
        @NotBlank(message = "User is required")
        UserResponsesDto user,

        @NotNull(message = "Role is required")
        Role role

) implements _Request {
}

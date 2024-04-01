package com.ouharri.pharma.controllers;

import com.ouharri.pharma.model.dto.requests.ChangePasswordRequest;
import com.ouharri.pharma.model.dto.requests.ChangeRoleRequest;
import com.ouharri.pharma.model.dto.requests.UserRequest;
import com.ouharri.pharma.model.dto.responses.UserResponsesDto;
import com.ouharri.pharma.services.spec.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

/**
 * Controller class for handling user-related endpoints.
 *
 * @author <a href="mailto:ouharrioutman@gmail.com">Ouharri Outman</a>
 * @version 1.0
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/users")
public class UserController extends _Controller<UUID, UserRequest, UserResponsesDto, UserService> {

    /**
     * Changes the password of the currently logged-in user.
     *
     * @param request       The change password request.
     * @param connectedUser The principal representing the currently connected user.
     * @return ResponseEntity with HTTP status OK.
     */
    @PatchMapping
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest request,
            Principal connectedUser
    ) {
        service.changePassword(request, connectedUser);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/change-role")
    public ResponseEntity<?> changeRole(
            @RequestBody ChangeRoleRequest changeRoleRequest
    ) {
        service.changeRole(changeRoleRequest);
        return ResponseEntity.ok().build();
    }

    /**
     * Retrieves the currently authenticated user.
     *
     * @return ResponseEntity containing the current user's details.
     */
        @GetMapping("/current")
        public ResponseEntity<UserResponsesDto> getCurrentUser() {
            return ResponseEntity.ok(service.getCurrentUser());
        }
}
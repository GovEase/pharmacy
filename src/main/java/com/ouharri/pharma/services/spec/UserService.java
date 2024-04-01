package com.ouharri.pharma.services.spec;

import com.ouharri.pharma.model.dto.requests.ChangePasswordRequest;
import com.ouharri.pharma.model.dto.requests.ChangeRoleRequest;
import com.ouharri.pharma.model.dto.requests.UserRequest;
import com.ouharri.pharma.model.dto.responses.UserResponsesDto;
import com.ouharri.pharma.model.entities.User;
import org.springframework.security.authentication.AnonymousAuthenticationToken;

import java.security.Principal;
import java.util.Optional;
import java.util.UUID;

/**
 * Service interface for managing User entities.
 *
 * @author <a href="mailto:ouharri.outman@gmail.com">ouharri</a>
 */
public interface UserService extends _Service<UUID, UserRequest, UserResponsesDto> {

    /**
     * Retrieves a user by their unique identifier.
     *
     * @param id The unique identifier of the user.
     * @return An Optional containing the user, or an empty Optional if not found.
     */
    Optional<User> findById(UUID id);

    UserResponsesDto changeRole(ChangeRoleRequest changeRoleRequest);

    /**
     * Retrieves the currently authenticated user.
     * <p>
     * This method fetches the current user's details from the Spring Security context.
     * It performs checks to ensure that there is an authenticated user and that the user
     * is not an instance of {@link AnonymousAuthenticationToken}.
     * </p>
     */
    UserResponsesDto getCurrentUser();

    User saveUser(User user);

    /**
     * Retrieves a user by email.
     *
     * @param email The email of the user to retrieve.
     * @return Optional containing the user if found, otherwise empty.
     */
    User findByEmail(String email);

    /**
     * Changes the password for the user identified by the connected user principal.
     *
     * @param request       The request containing the old and new passwords.
     * @param connectedUser The principal representing the connected user.
     */
    void changePassword(ChangePasswordRequest request, Principal connectedUser);

    /**
     * Revokes all valid tokens for a user by marking them as expired and revoked.
     *
     * @param user User for whom tokens are revoked
     */
    void revokeAllUserTokens(User user);

    /**
     * Saves a new user token to the database.
     *
     * @param user     User for whom the token is generated
     * @param jwtToken JWT token to be saved
     */
    void saveUserToken(User user, String jwtToken);

    /**
     * Updates the status of the specified user to offline.
     *
     * @param user The user to update.
     */
    void disconnect(User user);
}
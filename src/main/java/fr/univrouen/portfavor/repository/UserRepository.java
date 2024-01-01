package fr.univrouen.portfavor.repository;

import fr.univrouen.portfavor.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The user repository allows us to retrieve information about an API user.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * @param  email The user's email.
     * @return       The user associated to the given email.
     */
    Optional<User> findByEmail(String email);

    /**
     * @param  token The user's token.
     * @return       The user associated to the given token.
     */
    Optional<User> findByToken(String token);

}

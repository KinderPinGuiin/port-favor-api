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
     * @param  username The user's username.
     * @return          The user associated to the given username.
     */
    Optional<User> findByUsername(String username);

    /**
     * @param  token The user's token.
     * @return       The user associated to the given token.
     */
    Optional<User> findByToken(String token);

}

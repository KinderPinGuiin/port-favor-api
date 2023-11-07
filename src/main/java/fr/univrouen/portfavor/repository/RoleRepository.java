package fr.univrouen.portfavor.repository;

import fr.univrouen.portfavor.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The role repository allows us to retrieve information about an API user roles.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
}

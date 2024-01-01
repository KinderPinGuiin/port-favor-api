package fr.univrouen.portfavor.repository;

import fr.univrouen.portfavor.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * The image repository allows us to retrieve information about a stored image.
 */
public interface ImageRepository extends JpaRepository<Image, Long> {

    Optional<Image> findByPath(String path);

}

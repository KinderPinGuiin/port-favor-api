package fr.univrouen.portfavor.repository;

import fr.univrouen.portfavor.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * The image repository allows us to retrieve information about a stored image.
 */
public interface ImageRepository extends JpaRepository<Image, Long> {

    @Query("SELECT i FROM Image i WHERE i.isPublic")
    List<Image> findAllPublic();

    Optional<Image> findByPath(String path);

}

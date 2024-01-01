package fr.univrouen.portfavor.repository;

import fr.univrouen.portfavor.entity.Image;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * The image repository allows us to retrieve information about a stored image.
 */
public interface ImageRepository extends JpaRepository<Image, Long> {

    @Query("SELECT i FROM Image i WHERE i.pub")
    List<Image> findAllPublic();

    @Query("SELECT i FROM Image i WHERE i.pub")
    List<Image> findAllPublic(Pageable page);

    Optional<Image> findByPath(String path);

}

package fr.univrouen.portfavor.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.io.Serializable;

/**
 * Entity representing an image. This entity doesn't contain the image content, this is stored on the resources' folder.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Image implements Serializable {

    /**
     * The image's ID.
     */
    @Id
    @Setter(AccessLevel.NONE)
    @NonNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    /**
     * The image's name.
     */
    private @NonNull String name;

    /**
     * The image's description.
     */
    private @NonNull String description;

    /**
     * Indicates if the image is public or not.
     */
    private boolean pub;

    /**
     * The MIME type of the image.
     */
    private @NonNull String mime;

    /**
     * The path where the image is stored.
     */
    private @NonNull String path;

}

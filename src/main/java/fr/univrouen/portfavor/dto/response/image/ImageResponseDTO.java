package fr.univrouen.portfavor.dto.response.image;

import lombok.*;

/**
 * Represents an image information.
 */
@Getter
@Setter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class ImageResponseDTO {

    /**
     * The image's ID.
     */
    private @NonNull Long id;

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

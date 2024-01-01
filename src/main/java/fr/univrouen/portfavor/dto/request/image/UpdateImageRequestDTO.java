package fr.univrouen.portfavor.dto.request.image;

import lombok.*;

/**
 * Request sent to update an image.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class UpdateImageRequestDTO {

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
    private @NonNull Boolean isPublic;


}

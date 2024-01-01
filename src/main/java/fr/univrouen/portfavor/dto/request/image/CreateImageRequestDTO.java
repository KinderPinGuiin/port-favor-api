package fr.univrouen.portfavor.dto.request.image;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Request sent to create an image
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class CreateImageRequestDTO {

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

    /**
     * The image's content.
     */
    private @NonNull MultipartFile imageData;

}

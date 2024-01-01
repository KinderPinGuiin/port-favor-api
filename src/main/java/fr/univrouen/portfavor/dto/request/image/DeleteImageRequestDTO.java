package fr.univrouen.portfavor.dto.request.image;

import lombok.*;

/**
 * Request sent to delete an image.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class DeleteImageRequestDTO {

    /**
     * The image's to delete ID.
     */
    private @NonNull Long id;

}

package fr.univrouen.portfavor.dto.request.image;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class CreateImageRequestDTO {

    private @NonNull String name;

    private @NonNull String description;

    private @NonNull Boolean isPublic;

    private @NonNull MultipartFile imageData;

}

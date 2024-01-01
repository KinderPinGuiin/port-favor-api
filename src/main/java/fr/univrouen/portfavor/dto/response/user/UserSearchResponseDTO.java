package fr.univrouen.portfavor.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserSearchResponseDTO {

    /**
     * The current page of the result.
     */
    private Integer page;

    /**
     * The size of the page of the result.
     */
    private Integer pageSize;

    /**
     * The amount of users.
     */
    private Long maxElements;

    /**
     * The users of this page.
     */
    private List<UserResponseDTO> elements;

}

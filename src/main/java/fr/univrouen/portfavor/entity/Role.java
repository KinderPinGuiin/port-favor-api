package fr.univrouen.portfavor.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.io.Serializable;

/**
 * A role that indicates a user permissions on the API.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public final class Role implements Serializable {

    /**
     * The role's name (ID).
     */
    @Id
    @NonNull
    @Setter(AccessLevel.NONE)
    private String name;

}

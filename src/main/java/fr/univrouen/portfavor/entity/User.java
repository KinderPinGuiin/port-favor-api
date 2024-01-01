package fr.univrouen.portfavor.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity representing an application user.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public final class User implements Serializable, UserDetails {

    /**
     * The user's ID.
     */
    @Id
    @Setter(AccessLevel.NONE)
    @NonNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    /**
     * The user's email.
     */
    @NonNull
    private String email;

    /**
     * The user's password.
     */
    @NonNull
    private String password;

    /**
     * The user's bearer token.
     */
    private String token;

    /**
     * The user's roles.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();

    public User(@NonNull Long id, @NonNull String email, @NonNull String password, String token) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.token = token;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Get the user roles and add them to a GrantedAuthority set
        var authorities = new HashSet<GrantedAuthority>();
        for (Role role : this.roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}

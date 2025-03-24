package com.continuo.ecommerce.models;


import com.continuo.ecommerce.Enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

@Entity(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter@Setter
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String email;

    private String firstName;
    private String lastName;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    private boolean verified;
    private String profilePicture;

    @Enumerated(EnumType.STRING)
    private Role role;

    public User() {
    }

    public User(String email, String username, String password, String firstName, String lastName,String profilePicture, Role role, boolean verified) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.profilePicture = profilePicture;
        this.role = role;
        this.verified = verified;
    }

    public User(String email, String username, String password, String firstName, String lastName, Role role, boolean verified) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.verified = verified;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() ->"ROLE_"+role.name());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public String setUsername(String username) {
        return this.username = username;
    }
    public String setPassword(String password) {
        return this.password = password;
    }
    public String setFirstName(String firstName) {
        return this.firstName;
    }
    public String setLastName(String lastName) {
        return this.lastName;
    }
    public String setProfilePicture(String profilePicture) {
        return this.profilePicture = profilePicture;
    }
    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    //Manual Builder

    public static class Builder {
        private String email;
        private String username;
        private String password;
        private String firstName;
        private String lastName;

        private Role role;
        private boolean verified;

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder role(Role role) {
            this.role = role;
            return this;
        }

        public Builder verified(boolean verified) {
            this.verified = verified;
            return this;
        }

        public User build() {
            return new User(email, username, password, firstName, lastName, role, verified);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}

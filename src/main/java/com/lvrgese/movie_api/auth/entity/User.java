package com.lvrgese.movie_api.auth.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    @NotBlank(message = "Name can't be blank")
    @Column(nullable = false)
    private String name;
    @NotBlank(message = "Username can't be blank")
    @Column(nullable = false,unique = true)
    private String userName;
    @NotBlank(message = "Email can't be blank")
    @Column(nullable = false,unique = true)
    @Email(message = "Not a valid email format")
    private String email;
    @Column(nullable = false)
    @Size(min = 5,message = "Password should be at least 5 characters long")
    private String passWord;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    private Boolean isAccountNonExpired =true;
    private Boolean isAccountNonLocked=true;
    private Boolean isCredentialsNonExpired=true;
    private Boolean isEnabled=true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return passWord;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}

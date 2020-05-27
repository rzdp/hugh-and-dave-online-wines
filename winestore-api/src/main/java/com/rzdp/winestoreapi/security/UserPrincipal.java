package com.rzdp.winestoreapi.security;

import com.rzdp.winestoreapi.entity.Account;
import com.rzdp.winestoreapi.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@EqualsAndHashCode
@Data
public class UserPrincipal implements UserDetails {

    private long userId;

    private String name;

    private String email;

    private String password;

    private boolean active;

    private Collection<? extends GrantedAuthority> authorities;

    public static UserPrincipal create(User user) {
        Account account = user.getAccount();
        String roleName = account.getRole().getName();
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(roleName));
        return new UserPrincipal(
                user.getUserId(),
                user.getFullName(),
                account.getEmail(),
                account.getPassword(),
                user.isActive(),
                authorities
        );
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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
        return active;
    }
}

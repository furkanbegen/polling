package com.furkanbegen.polling.security;

import com.furkanbegen.polling.model.Role;
import com.furkanbegen.polling.model.User;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class PollingUserPrincipal implements UserDetails {

  private final User user;

  public PollingUserPrincipal(User user) {
    this.user = user;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<GrantedAuthority> authorities = new ArrayList<>();

    for (Role role : user.getRoles()) {
      authorities.add(new SimpleGrantedAuthority(role.getName().name()));
      //      authorities.addAll(
      //          role.getPrivileges().stream()
      //              .map(p -> new SimpleGrantedAuthority(p.getName()))
      //              .collect(Collectors.toList()));
    }

    return authorities;
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getEmail();
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
    return user.isEnabled();
  }

  public User getUser() {
    return user;
  }
}

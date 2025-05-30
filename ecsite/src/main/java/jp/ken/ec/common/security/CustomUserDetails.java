package jp.ken.ec.common.security;



import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jp.ken.ec.domain.entity.UserEntity;

public class CustomUserDetails implements UserDetails {

    private final UserEntity user;

    public CustomUserDetails(UserEntity user) {
        this.user = user;
    }
     
    
    // ユーザーIDを返すカスタムメソッド（必要なら）
    public Long getUserId() {
        return user.getUser_id();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 例として全ユーザーに固定で ROLE_USER を付与
        return Collections.singleton(() -> "ROLE_USER");
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
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

package jp.ken.ec.application.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jp.ken.ec.common.security.CustomUserDetails;
import jp.ken.ec.domain.entity.UserEntity;
import jp.ken.ec.domain.repository.UserRepository;


@Service
public class CustomUserDetailsService implements UserDetailsService {


    
    private UserRepository userRepository;

	public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
        
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // ユーザーをデータベースから検索（ユーザー名は一意である前提）
        UserEntity userEntity = userRepository.findByUsername(username);
        if (userEntity == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        
     // ログ出力して確認
        System.out.println("Found user: " + userEntity.getUsername());
        return new CustomUserDetails(userEntity);
    }
}


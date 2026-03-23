package jp.ken.ec.common.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import jp.ken.ec.application.service.CustomUserDetailsService;
import jp.ken.ec.domain.repository.UserRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	private final DataSource dataSource;
	private final UserRepository userRepository;
	
	public SecurityConfig(DataSource dataSource,UserRepository userRepository) {
		this.dataSource = dataSource;
		this.userRepository = userRepository;
		
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
	    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	    authProvider.setUserDetailsService(userDetailsService());
	    authProvider.setPasswordEncoder(passwordEncoder());
	    return authProvider;
	}


	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http.authenticationProvider(authenticationProvider())
			.authorizeHttpRequests(auth -> auth
				 // GETリクエストの場合、/login は未認証でもアクセスできる
	            .requestMatchers(HttpMethod.GET, "/login").permitAll()
	            // 静的リソースやその他のページは未認証でもアクセス可能
	            .requestMatchers("/css/**", "/images/**", "/", "/product/**", "/h2-console/**", "/search", "/search/**").permitAll()
	            //(後でROLE_ADMINに変更予定)
	            .requestMatchers("/admin/**").hasRole("ADMIN")
	            // その他は認証が必要
	            .anyRequest().authenticated()
	    )
		
		
			.formLogin(form -> form
					.loginPage("/login") //カスタムログインページ
					.usernameParameter("username")  // フォームのフィールド名に合わせる
				    .passwordParameter("password")
					.successHandler(customSuccessHandler())//ロールで遷移先振り分け
					.failureUrl("/login?error=true")//ログイン失敗時にエラーパラメータ付与
					.permitAll() //ログイン成功後の遷移	
		)
			
			//ログアウト設定：topページにリダイレクト
			.logout(logout -> logout
					.logoutUrl("/logout")
					.logoutSuccessUrl("/")
					.invalidateHttpSession(true) //session 破棄
					.deleteCookies("JSESSIONID") //Cookie 削除
					.permitAll()
			 )
			
			
	    .csrf(csrf -> csrf.disable())  // H2コンソール用に CSRF を無効化（重要）
	    .headers(headers -> headers.frameOptions().disable());  // H2コンソール用に Frame Options を無効化（重要）　
		
		http.sessionManagement(session -> session
				.maximumSessions(1)
				//セッション管理後で修正
				.maxSessionsPreventsLogin(false));
		
		return http.build();
		
	}
	
	
	//ロールで遷移先振り分けロジック
	@Bean
	public AuthenticationSuccessHandler customSuccessHandler() {
		return (request,response, authentication) -> {
			boolean isAdmin = authentication.getAuthorities().stream()
					.anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
			
			if(isAdmin) {
				response.sendRedirect("/admin");
			}else {
				response.sendRedirect("/");
			}
		
		
		};
	}
	

	 @Bean
	    public UserDetailsService userDetailsService() {
	        // カスタム UserDetailsService を返す
	        return new CustomUserDetailsService(userRepository);
	    }
	
	
	
	
	/*
	
	@Bean 
	protected UserDetailsManager userDetailesManager() {
		JdbcUserDetailsManager users = new JdbcUserDetailsManager(this.dataSource);
		//ユーザー情報取得クエリ
		//usersテーブルには、enableのカラムがないために常にtrueとみなす。
		users.setUsersByUsernameQuery("SELECT username, password, true AS enabled FROM users WHERE username = ?");
		
	    // 権限情報取得用クエリ：
	    // 権限テーブルがない場合は、全ユーザーに対して固定で ROLE_USER を返す
		users.setAuthoritiesByUsernameQuery("SELECT username, 'ROLE_USER' AS authority FROM users WHERE username = ?");

		return users;
	
	}
	*/
	
	@Bean 
	protected HttpSessionEventPublisher httpSessionEventPublisher() {
		 return new HttpSessionEventPublisher();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
	

}

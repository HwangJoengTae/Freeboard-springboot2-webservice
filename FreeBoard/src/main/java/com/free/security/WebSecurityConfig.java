package com.free.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.free.auth.CustomUserDetailsService;
import com.free.oauth.CustomOAuth2UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	private final CustomOAuth2UserService customOAuth2UserService;
	
	private final CustomUserDetailsService customUserDetailsService;
	
	/* 로그인 실패 핸들러 의존성 주입 */
    private final AuthenticationFailureHandler customFailureHandler;
	
	@Bean 
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
		
	}
	
	/* AuthenticationManager Bean 등록 */
   
	@Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
     return super.authenticationManagerBean();
    }
	

    // 시큐리티 암호화 확인
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(encoder());
    }
   
    // static 설정 무시 
    @Override
    public void configure(WebSecurity web) throws Exception {
        		web
                .ignoring().antMatchers( "/css/**", "/js/**", "/img/**");
    }
    
	 
	@Override
    protected void configure(HttpSecurity http) throws Exception {
		  http
		  .csrf().ignoringAntMatchers("/api/**") /* REST API 사용 예외처리 */
          .and()
	      .authorizeRequests()
          .antMatchers("/", "/auth/**", "/board/read/**", "/board/search/**","/a","/v2/api-docs")
          .permitAll()
          .anyRequest().authenticated()
          .and()
          .formLogin()
          .loginPage("/auth/login") //커스텀로그인 페이지 
          .loginProcessingUrl("/auth/loginProc") //주소 요청 오는
          .failureHandler(customFailureHandler) // 로그인 실패 핸들러
          .defaultSuccessUrl("/") //로그인 성공시 이도되는 페이지
          .and()
          .logout()
          .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
          .invalidateHttpSession(true).deleteCookies("JSESSIONID")
          .logoutSuccessUrl("/") //로그아웃 성공시 
          .and()
          .oauth2Login() //oauth 로그인 
          .userInfoEndpoint()
          .userService(customOAuth2UserService) //서비스 실행
          ;
        		
        		
    }
}

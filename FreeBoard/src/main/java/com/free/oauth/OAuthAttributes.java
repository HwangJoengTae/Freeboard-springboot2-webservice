package com.free.oauth;

import java.util.Map;

import com.free.domain.Role;
import com.free.domain.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class OAuthAttributes {
	 private Map<String, Object> attributes;
	    private String nameAttributeKey;
	    private String username;
	    private String nickname;
	    private String email;
	    private Role role;

  

    
    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String,Object> attributes) {
        switch (registrationId) {
        case "google":
            return ofGoogle(userNameAttributeName, attributes);
        case "naver":
            return ofNaver(userNameAttributeName, attributes);
        case "kakao":
            return ofKakao(userNameAttributeName, attributes);
    }
    return null;
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
        		.username((String) attributes.get("email"))
                .email((String) attributes.get("email"))
                .nickname((String) attributes.get("name"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }


    
    
    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes){
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuthAttributes.builder()
        		 .username((String) response.get("email"))
                 .email((String) response.get("email"))
                 .nickname((String) response.get("nickname"))
                 .attributes(response)
                 .nameAttributeKey(userNameAttributeName)
                 .build();
    }
    
    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes){
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
       
        Map<String, Object> kakaoProfile = (Map<String, Object>)kakaoAccount.get("profile");

        return OAuthAttributes.builder()
                .username((String) kakaoProfile.get("email"))
                .email((String) kakaoAccount.get("email"))
                .nickname((String) kakaoProfile.get("nickname"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }
    
    public User toEntity() {
        return User.builder()
        		 .username(email)
                 .email(email)
                 .nickname(nickname)
                 .role(Role.SOCIAL)
                 .build();
    }

}

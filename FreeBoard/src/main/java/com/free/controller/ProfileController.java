package com.free.controller;

import java.util.Arrays;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ProfileController {
	   private final Environment env;
	    
	    //현재 어떤 포트로 돌아가는지 확인용. set1 = 8081, set2 = 8082
	    @GetMapping("/profile")
	    public String profile() {
	        //현재 동작중인 프로파일의 이름을 반환
	        return Arrays.stream(env.getActiveProfiles()).findFirst().orElse("");
	    }
	   
    
    

}
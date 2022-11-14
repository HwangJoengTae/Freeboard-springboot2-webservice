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
	    
	   @GetMapping("/profile")
	    public String getProfile () {
	        return Arrays.stream(env.getActiveProfiles())
	                .filter(e -> "set1".equals(e) || "set2".equals(e))
	                .findFirst()
	                .orElse("");
	    }
    
    

}
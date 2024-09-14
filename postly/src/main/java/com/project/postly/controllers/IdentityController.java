package com.project.postly.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.postly.entities.RefreshToken;
import com.project.postly.entities.Profile;
import com.project.postly.requests.RefreshRequest;
import com.project.postly.requests.ProfileRequest;
import com.project.postly.responses.AuthResponse;
import com.project.postly.security.JwtTokenProvider;
import com.project.postly.services.RefreshTokenService;
import com.project.postly.services.ProfileService;

@RestController
@RequestMapping("/identity")
public class IdentityController {
	
	private AuthenticationManager authManager;
	
	private JwtTokenProvider tokenProvider;
	
	private ProfileService profileService;
	
	private PasswordEncoder passwordEncoder;

	private RefreshTokenService refreshTokenService;
	
    public IdentityController(AuthenticationManager authManager, ProfileService profileService, 
    		PasswordEncoder passwordEncoder, JwtTokenProvider tokenProvider, RefreshTokenService refreshTokenService) {
        this.authManager = authManager;
        this.profileService = profileService;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.refreshTokenService = refreshTokenService;
    }
    
	@PostMapping("/signin")
	public AuthResponse signIn(@RequestBody ProfileRequest loginRequest) {
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword());
		Authentication auth = authManager.authenticate(authToken);
		SecurityContextHolder.getContext().setAuthentication(auth);
		String jwtToken = tokenProvider.generateJwtToken(auth);
		Profile profile = profileService.getOneProfileByUserName(loginRequest.getUserName());
		AuthResponse authResponse = new AuthResponse();
		authResponse.setAccessToken("Bearer " + jwtToken);
		authResponse.setRefreshToken(refreshTokenService.createRefreshToken(profile));
		authResponse.setUserId(profile.getId());
		return authResponse;
	}
	
	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> signUp(@RequestBody ProfileRequest registerRequest) {
		AuthResponse authResponse = new AuthResponse();
		if(profileService.getOneProfileByUserName(registerRequest.getUserName()) != null) {
			authResponse.setMessage("Username already in use.");
			return new ResponseEntity<>(authResponse, HttpStatus.BAD_REQUEST);
		}
		
		Profile profile = new Profile();
		profile.setUserName(registerRequest.getUserName());
		profile.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
		profileService.saveOneUser(profile); // Doğru servis çağrısı
		
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(registerRequest.getUserName(), registerRequest.getPassword());
		Authentication auth = authManager.authenticate(authToken);
		SecurityContextHolder.getContext().setAuthentication(auth);
		String jwtToken = tokenProvider.generateJwtToken(auth);
		
		authResponse.setMessage("User successfully registered.");
		authResponse.setAccessToken("Bearer " + jwtToken);
		authResponse.setRefreshToken(refreshTokenService.createRefreshToken(profile));
		authResponse.setUserId(profile.getId());
		return new ResponseEntity<>(authResponse, HttpStatus.CREATED);		
	}
	
	@PostMapping("/token/refresh")
	public ResponseEntity<AuthResponse> refreshToken(@RequestBody RefreshRequest refreshRequest) {
		AuthResponse response = new AuthResponse();
		RefreshToken token = refreshTokenService.getByUser(refreshRequest.getUserId());
		if(token.getToken().equals(refreshRequest.getRefreshToken()) &&
				!refreshTokenService.isRefreshExpired(token)) {

			Profile profile = token.getProfile();
			String jwtToken = tokenProvider.generateJwtTokenByUserId(profile.getId());
			response.setMessage("token successfully refreshed.");
			response.setAccessToken("Bearer " + jwtToken);
			response.setUserId(profile.getId());
			return new ResponseEntity<>(response, HttpStatus.OK);		
		} else {
			response.setMessage("refresh token is not valid.");
			return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
		}
	}
}

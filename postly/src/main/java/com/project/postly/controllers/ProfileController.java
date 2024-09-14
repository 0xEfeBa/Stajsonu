package com.project.postly.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.project.postly.entities.Profile;
import com.project.postly.exceptions.UserNotFoundException;
import com.project.postly.responses.ProfileResponse;
import com.project.postly.services.ProfileService;

@RestController
@RequestMapping("/profiles")
public class ProfileController {
	
	private ProfileService profileService;
	
	public ProfileController(ProfileService profileService) {
		this.profileService = profileService;
	}
	
	@GetMapping
	public List<ProfileResponse> getAllProfiles(){
		return profileService.getAllUsers().stream().map(u -> new ProfileResponse(u)).toList();
	}
	
	@PostMapping
	public ResponseEntity<Void> createProfile(@RequestBody Profile newProfile) {
		Profile profile = profileService.saveOneUser(newProfile);
		if(profile != null) 
			return new ResponseEntity<>(HttpStatus.CREATED);
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("/{profileId}")
	public ProfileResponse getOneProfile(@PathVariable Long profileId) {
		Profile profile = profileService.getOneProfileById(profileId);
		if(profile == null) {
			throw new UserNotFoundException();
		}
		return new ProfileResponse(profile);
	}
	
	@PutMapping("/{profileId}")
	public ResponseEntity<Void> updateOneProfile(@PathVariable Long profileId, @RequestBody Profile newProfile) {
		Profile profile = profileService.updateOneUser(profileId, newProfile);
		if(profile != null) 
			return new ResponseEntity<>(HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@DeleteMapping("/{profileId}")
	public void deleteOneProfile(@PathVariable Long profileId) {
		profileService.deleteById(profileId);
	}
	
	@GetMapping("/activity/{profileId}")
	public List<Object> getProfileActivity(@PathVariable Long profileId) {
		return profileService.getUserActivity(profileId);
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	private void handleProfileNotFound() {
		
	}
}

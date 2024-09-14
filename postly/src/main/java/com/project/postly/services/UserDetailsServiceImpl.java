package com.project.postly.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.postly.entities.Profile;
import com.project.postly.repos.ProfileRepository;
import com.project.postly.security.JwtUserDetails;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private ProfileRepository profileRepository;
	
    public UserDetailsServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }
    
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Profile profile = profileRepository.findByProfileName(username);
		return JwtUserDetails.create(profile);
	}
	
	public UserDetails loadUserById(Long id) {
		Profile profile = profileRepository.findById(id).get();
		return JwtUserDetails.create(profile); 
	}

}

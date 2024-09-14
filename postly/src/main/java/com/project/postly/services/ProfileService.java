package com.project.postly.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.project.postly.entities.Profile;
import com.project.postly.repos.FeedbackRepository;
import com.project.postly.repos.LikeRepository;
import com.project.postly.repos.EntryRepository;
import com.project.postly.repos.ProfileRepository;

@Service
public class ProfileService {

	ProfileRepository profileRepository;
	LikeRepository likeRepository;
	FeedbackRepository feedbackRepository;
	EntryRepository entryRepository;

	public ProfileService(ProfileRepository profileRepository, LikeRepository likeRepository, 
			FeedbackRepository feedbackRepository, EntryRepository entryRepository) {
		this.profileRepository = profileRepository;
		this.likeRepository = likeRepository;
		this.feedbackRepository = feedbackRepository;
		this.entryRepository = entryRepository;
	}

	public List<Profile> getAllUsers() {
		return profileRepository.findAll();
	}

	public Profile saveOneUser(Profile newUser) {
		return profileRepository.save(newUser);
	}

	public Profile getOneProfileById(Long userId) {
		return profileRepository.findById(userId).orElse(null);
	}

	public Profile updateOneUser(Long userId, Profile newUser) {
		Optional<Profile> user = profileRepository.findById(userId);
		if(user.isPresent()) {
			Profile foundUser = user.get();
			foundUser.setUserName(newUser.getUserName());
			foundUser.setPassword(newUser.getPassword());
			foundUser.setAvatar(newUser.getAvatar());
			profileRepository.save(foundUser);
			return foundUser;
		}else
			return null;
	}

	public void deleteById(Long userId) {
		try {
		profileRepository.deleteById(userId);
		}catch(EmptyResultDataAccessException e) { //user zaten yok, db'den empty result gelmiş
			System.out.println("User "+userId+" doesn't exist"); //istersek loglayabiliriz
		}
	}
	
	public Profile getOneProfileByUserName(String userName) {
		return profileRepository.findByProfileName(userName);
	}

	public List<Object> getUserActivity(Long userId) {
		List<Long> postIds = entryRepository.findTopByEntryId(userId);
		if(postIds.isEmpty())
			return null;
		List<Object> comments = feedbackRepository.findUserCommentsByEntryId(postIds);
		List<Object> likes = new ArrayList<>();
		for (Long postId : postIds) {
			likes.addAll(likeRepository.findByProfileIdAndEntryId(userId, postId)); // updated method call
		}
		List<Object> result = new ArrayList<>();
		result.addAll(comments);
		result.addAll(likes); 
		return result;
	}
	
}

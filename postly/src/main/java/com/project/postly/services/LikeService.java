package com.project.postly.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.project.postly.entities.Like;
import com.project.postly.entities.Entry;
import com.project.postly.entities.Profile;
import com.project.postly.repos.LikeRepository;
import com.project.postly.requests.LikeCreateRequest;
import com.project.postly.responses.LikeResponse;

@Service
public class LikeService {

	private LikeRepository likeRepository;
	private ProfileService profileService;
	private EntryService entryService;

	public LikeService(LikeRepository likeRepository, ProfileService profileService, 
			EntryService entryService) {
		this.likeRepository = likeRepository;
		this.profileService = profileService;
		this.entryService = entryService;
	}

	public List<LikeResponse> getAllLikesWithParam(Optional<Long> userId, Optional<Long> postId) {
		List<Like> list;
		if(userId.isPresent() && postId.isPresent()) {
			list = likeRepository.findByProfileIdAndEntryId(userId.get(), postId.get());
		}else if(userId.isPresent()) {
			list = likeRepository.findByProfileId(userId.get());
		}else if(postId.isPresent()) {
			list = likeRepository.findByEntryId(postId.get());
		}else
			list = likeRepository.findAll();
		return list.stream().map(like -> new LikeResponse(like)).collect(Collectors.toList());
	}

	public Like getOneLikeById(Long LikeId) {
		return likeRepository.findById(LikeId).orElse(null);
	}

	public Like createOneLike(LikeCreateRequest request) {
		Profile profile = profileService.getOneProfileById(request.getUserId());
		Entry entry = entryService.getOneEntryById(request.getPostId());
		if(profile != null && entry != null) {
			Like likeToSave = new Like();
			likeToSave.setId(request.getId());
			likeToSave.setEntry(entry);
			likeToSave.setUser(profile);
			return likeRepository.save(likeToSave);
		}else		
			return null;
	}

	public void deleteOneLikeById(Long likeId) {
		likeRepository.deleteById(likeId);
	}
	
	
}

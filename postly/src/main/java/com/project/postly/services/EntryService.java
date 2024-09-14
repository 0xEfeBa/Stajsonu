package com.project.postly.services; // Değiştirildi

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.postly.entities.Entry; // Değiştirildi
import com.project.postly.entities.Profile; // Değiştirildi
import com.project.postly.repos.EntryRepository; // Değiştirildi
import com.project.postly.requests.EntryCreateRequest; // Değiştirildi
import com.project.postly.requests.EntryUpdateRequest; // Değiştirildi
import com.project.postly.responses.LikeResponse; // Değiştirildi
import com.project.postly.responses.EntryResponse; // Değiştirildi

@Service
public class EntryService {

	private EntryRepository entryRepository;
	private LikeService likeService;
	private ProfileService profileService;
	
	public EntryService(EntryRepository entryRepository,
			ProfileService profileService) {
		this.entryRepository = entryRepository;
		this.profileService = profileService;
	}

	@Autowired
	public void setLikeService(LikeService likeService) {
		this.likeService = likeService;
	}
	public List<EntryResponse> getAllPosts(Optional<Long> userId) {
		List<Entry> list;
		if(userId.isPresent()) {
			 list = entryRepository.findByEntryId(userId.get());
		}else
			list = entryRepository.findAll();
		return list.stream().map(p -> { 
			List<LikeResponse> likes = likeService.getAllLikesWithParam(Optional.ofNullable(null), Optional.of(p.getId()));
			return new EntryResponse(p, likes); // PostResponse constructor should accept Entry instead of Post
		}).collect(Collectors.toList());
	}

	public Entry getOneEntryById(Long postId) {
		return entryRepository.findById(postId).orElse(null);
	}

	public EntryResponse getOnePostByIdWithLikes(Long postId) {
		Entry post = entryRepository.findById(postId).orElse(null);
		List<LikeResponse> likes = likeService.getAllLikesWithParam(Optional.ofNullable(null), Optional.of(postId));
		return new EntryResponse(post, likes); 
	}
	
	public Entry createOneEntry(EntryCreateRequest newPostRequest) {
		Profile profile = profileService.getOneProfileById(newPostRequest.getUserId());
		if(profile == null)
			return null;
		Entry toSave = new Entry();
		toSave.setId(newPostRequest.getId());
		toSave.setText(newPostRequest.getText());
		toSave.setTitle(newPostRequest.getTitle());
		toSave.setProfile(profile);
		toSave.setCreateDate(new Date());
		return entryRepository.save(toSave);
	}

	public Entry updateOneEntryById(Long postId, EntryUpdateRequest updatePost) {
		Optional<Entry> entry = entryRepository.findById(postId);
		if(entry.isPresent()) {
			Entry toUpdate = entry.get();
			toUpdate.setText(updatePost.getText());
			toUpdate.setTitle(updatePost.getTitle());
			entryRepository.save(toUpdate);
			return toUpdate;
		}
		return null;
	}

	public void deleteOneEntryById(Long postId) {
		entryRepository.deleteById(postId);
	}
	
}

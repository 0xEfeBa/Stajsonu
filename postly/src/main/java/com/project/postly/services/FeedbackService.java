package com.project.postly.services; // Değiştirildi

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.project.postly.entities.Entry; // Değiştirildi
import com.project.postly.entities.Feedback; // Değiştirildi
import com.project.postly.entities.Profile; // Değiştirildi
import com.project.postly.repos.FeedbackRepository; // Değiştirildi
import com.project.postly.requests.FeedbackCreateRequest; // Değiştirildi
import com.project.postly.requests.FeedbackUpdateRequest; // Değiştirildi
import com.project.postly.responses.FeedbackResponse; // Değiştirildi

@Service
public class FeedbackService {

	private FeedbackRepository feedbackRepository;
	private ProfileService profileService;
	private EntryService entryService;

	public FeedbackService(FeedbackRepository feedbackRepository, ProfileService profileService, 
			EntryService postService) {
		this.feedbackRepository = feedbackRepository;
		this.profileService = profileService;
		this.entryService = postService;
	}

	public List<FeedbackResponse> getAllCommentsWithParam(Optional<Long> userId, Optional<Long> postId) {
		List<Feedback> comments;
		if(userId.isPresent() && postId.isPresent()) {
			comments = feedbackRepository.findByProfileIdAndEntryId(userId.get(), postId.get());
		}else if(userId.isPresent()) {
			comments = feedbackRepository.findByProfileId(userId.get());
		}else if(postId.isPresent()) {
			comments = feedbackRepository.findByEntityId(postId.get());
		}else
			comments = feedbackRepository.findAll();
		return comments.stream().map(comment -> new FeedbackResponse(comment)).collect(Collectors.toList());
	}

	public Feedback getOneCommentById(Long commentId) {
		return feedbackRepository.findById(commentId).orElse(null);
	}
	
	public Feedback createOneComment(FeedbackCreateRequest request) {
		Profile profile = profileService.getOneProfileById(request.getUserId());
		Entry entry = entryService.getOneEntryById(request.getEntryId()); // Post yerine Entry olarak güncellendi
		if(profile != null && entry != null) { // Post yerine Entry olarak güncellendi
			Feedback commentToSave = new Feedback();
			commentToSave.setId(request.getId());
			commentToSave.setEntry(entry); // Post yerine Entry olarak güncellendi
			commentToSave.setUser(profile);
			commentToSave.setText(request.getText());
			commentToSave.setCreateDate(new Date());
			return feedbackRepository.save(commentToSave);
		}else		
			return null;
	}

	public Feedback updateOneCommentById(Long commentId, FeedbackUpdateRequest request) {
		Optional<Feedback> comment = feedbackRepository.findById(commentId);
		if(comment.isPresent()) {
			Feedback commentToUpdate = comment.get();
			commentToUpdate.setText(request.getText());
			return feedbackRepository.save(commentToUpdate);
		}else
			return null;
	}

	public void deleteOneCommentById(Long commentId) {
		feedbackRepository.deleteById(commentId);
	}
	
	
}

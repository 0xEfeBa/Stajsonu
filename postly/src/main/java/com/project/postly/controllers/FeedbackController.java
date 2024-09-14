package com.project.postly.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.postly.entities.Feedback;
import com.project.postly.requests.FeedbackCreateRequest;
import com.project.postly.requests.FeedbackUpdateRequest;
import com.project.postly.responses.FeedbackResponse;
import com.project.postly.services.FeedbackService;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {

	private FeedbackService feedbackService;

	public FeedbackController(FeedbackService feedbackService) {
		this.feedbackService = feedbackService;
	}

	@GetMapping
	public List<FeedbackResponse> getAllFeedback(@RequestParam Optional<Long> userId,
												@RequestParam Optional<Long> postId) {
		return feedbackService.getAllCommentsWithParam(userId, postId);
	}

	@PostMapping
	public Feedback createFeedback(@RequestBody FeedbackCreateRequest request) {
		return feedbackService.createOneComment(request);
	}

	@GetMapping("/{feedbackId}")
	public Feedback getFeedback(@PathVariable Long feedbackId) {
		return feedbackService.getOneCommentById(feedbackId);
	}

	@PutMapping("/{feedbackId}")
	public Feedback updateFeedback(@PathVariable Long feedbackId, @RequestBody FeedbackUpdateRequest request) {
		return feedbackService.updateOneCommentById(feedbackId, request);
	}

	@DeleteMapping("/{feedbackId}")
	public void deleteFeedback(@PathVariable Long feedbackId) {
		feedbackService.deleteOneCommentById(feedbackId);
	}
}

package com.project.postly.responses;

import com.project.postly.entities.Feedback;

import lombok.Data;

@Data
public class FeedbackResponse {
	
	Long id;
	Long userId;
	String userName;
	String text;

	public FeedbackResponse(Feedback entity) {
		this.id = entity.getId();
		this.userId = entity.getUser().getId();
		this.userName = entity.getUser().getUserName();
		this.text = entity.getText();
	}
}

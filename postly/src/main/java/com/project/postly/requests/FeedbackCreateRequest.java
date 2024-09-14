package com.project.postly.requests;

import lombok.Data;

@Data
public class FeedbackCreateRequest {
	
	Long id;
	Long userId;
	Long entryId;
	String text;

}

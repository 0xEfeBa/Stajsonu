package com.project.postly.responses;

import com.project.postly.entities.Like;

import lombok.Data;

@Data
public class LikeResponse {

	Long id;
	Long userId;
	Long entryId;
	
	public LikeResponse(Like entity) {
		this.id = entity.getId();
		this.userId = entity.getUser().getId();
		this.entryId = entity.getEntry().getId();
	} 
}

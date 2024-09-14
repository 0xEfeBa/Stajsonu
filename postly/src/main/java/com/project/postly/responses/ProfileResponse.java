package com.project.postly.responses;

import com.project.postly.entities.Profile;

import lombok.Data;

@Data
public class ProfileResponse {
	
	Long id;
	int avatarId;
	String userName;

	public ProfileResponse(Profile entity) {
		this.id = entity.getId();
		this.avatarId = entity.getAvatar();
		this.userName = entity.getUserName();
	} 
}

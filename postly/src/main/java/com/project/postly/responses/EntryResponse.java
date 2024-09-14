package com.project.postly.responses;

import java.util.List;

//import com.project.questapp.entities.Like;
import com.project.postly.entities.Entry;

import lombok.Data;

@Data
public class EntryResponse {
	
	Long id;
	Long userId;
	String userName;
	String title;
	String text;
	List<LikeResponse> entryLikes;
	
	public EntryResponse(Entry entity, List<LikeResponse> likes) {
		this.id = entity.getId();
		this.userId = entity.getProfile().getId();
		this.userName = entity.getProfile().getUserName();
		this.title = entity.getTitle();
		this.text = entity.getText();
		this.entryLikes = likes;
	}
}

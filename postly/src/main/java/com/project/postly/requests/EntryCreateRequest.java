package com.project.postly.requests;

import lombok.Data;

@Data
public class EntryCreateRequest {

	Long id;
	String text;
	String title;
	Long userId;
}

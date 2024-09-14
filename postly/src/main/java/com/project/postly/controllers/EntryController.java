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

import com.project.postly.entities.Entry;
import com.project.postly.requests.EntryCreateRequest;
import com.project.postly.requests.EntryUpdateRequest;
import com.project.postly.responses.EntryResponse;
import com.project.postly.services.EntryService;

@RestController
@RequestMapping("/entries")
public class EntryController {

	private EntryService entryService;

	public EntryController(EntryService entryService) {
		this.entryService = entryService;
	}
	
	@GetMapping
	public List<EntryResponse> getAllEntries(@RequestParam Optional<Long> userId) {
		return entryService.getAllPosts(userId);
	}

	@PostMapping
	public Entry createEntry(@RequestBody EntryCreateRequest newEntryRequest) {
		return entryService.createOneEntry(newEntryRequest);
	}
	
	@GetMapping("/{entryId}")
	public EntryResponse getOneEntry(@PathVariable Long entryId) {
		return entryService.getOnePostByIdWithLikes(entryId);
	}
	
	@PutMapping("/{entryId}")
	public Entry updateOneEntry(@PathVariable Long entryId, @RequestBody EntryUpdateRequest updateEntry) {
		return entryService.updateOneEntryById(entryId, updateEntry);
	}
	
	@DeleteMapping("/{entryId}")
	public void deleteOneEntry(@PathVariable Long entryId) {
		entryService.deleteOneEntryById(entryId);
	}
}

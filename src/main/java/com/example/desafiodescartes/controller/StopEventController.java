package com.example.desafiodescartes.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.desafiodescartes.domain.stopevent.dto.NewStopEventDTO;
import com.example.desafiodescartes.domain.stopevent.service.StopEventService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/routes/events")
public class StopEventController {
	
	public final StopEventService stopEventService;
	
	@PostMapping
	public ResponseEntity<?> createNewStopEvent(@Valid @RequestBody NewStopEventDTO dto) {
		stopEventService.addNewEvent(dto);
		return ResponseEntity.ok().build();
	}

}

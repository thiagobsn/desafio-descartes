package com.example.desafiodescartes.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.desafiodescartes.domain.stopevent.dto.NewStopEventDTO;
import com.example.desafiodescartes.domain.stopevent.service.StopEventService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "StopEvent")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/routes/events")
public class StopEventController {
	
	public final StopEventService stopEventService;
	
	@PostMapping
	@Operation(summary = "Criar um novo evento para uma rota/parada" ,description = "Criar um novo evento para uma rota/parada")
	public ResponseEntity<?> createNewStopEvent(@Valid @RequestBody NewStopEventDTO dto) {
		stopEventService.addNewEvent(dto);
		return ResponseEntity.ok().build();
	}

}

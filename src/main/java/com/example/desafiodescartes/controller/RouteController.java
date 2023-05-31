package com.example.desafiodescartes.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.desafiodescartes.domain.route.dto.NewRouteDTO;
import com.example.desafiodescartes.domain.route.dto.RouteDTO;
import com.example.desafiodescartes.domain.route.dto.UpdateRouteDTO;
import com.example.desafiodescartes.domain.route.service.RouteService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/routes")
public class RouteController {

	public final RouteService routeService;

	@GetMapping("/{id}")
	public ResponseEntity<RouteDTO> findBy(@PathVariable("id") Long id) {
		return ResponseEntity.ok(routeService.findBy(id));
	}

	@GetMapping
	public ResponseEntity<List<RouteDTO>> findAll() {
		return ResponseEntity.ok(routeService.findAll());
	}

	@PostMapping
	public ResponseEntity<RouteDTO> createNewRoute(@Valid @RequestBody NewRouteDTO dto) {
		return ResponseEntity.ok(routeService.createNewRoute(dto));
	}

	@PutMapping
	public ResponseEntity<RouteDTO> createNewRoute(@Valid @RequestBody UpdateRouteDTO dto) {
		return ResponseEntity.ok(routeService.updateRoute(dto));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteBy(@PathVariable("id") Long id) {
		routeService.deleteBy(id);
		return ResponseEntity.ok().build();
	}

}

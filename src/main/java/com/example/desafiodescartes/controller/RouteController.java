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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Route")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/routes")
public class RouteController {

	public final RouteService routeService;

	
	@GetMapping("/{id}")
	@Operation(summary = "Buscar uma rota pelo id" ,description = "Buscar uma rota pelo id")
	public ResponseEntity<RouteDTO> findBy(@PathVariable("id") Long id) {
		return ResponseEntity.ok(routeService.findBy(id));
	}

	@GetMapping
	@Operation(summary = "Buscar todas as rota" ,description = "Buscar todas as rota")
	public ResponseEntity<List<RouteDTO>> findAll() {
		return ResponseEntity.ok(routeService.findAll());
	}

	@PostMapping
	@Operation(summary = "Criar uma nova rota" ,description = "Criar uma nova rota")
	public ResponseEntity<RouteDTO> createNewRoute(@Valid @RequestBody NewRouteDTO dto) {
		return ResponseEntity.ok(routeService.createNewRoute(dto));
	}

	@PutMapping
	@Operation(summary = "Alterar uma rota" ,description = "Alterar uma rota")
	public ResponseEntity<RouteDTO> updateRoute(@Valid @RequestBody UpdateRouteDTO dto) {
		return ResponseEntity.ok(routeService.updateRoute(dto));
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Excluir uma rota" ,description = "Excluir uma rota")
	public ResponseEntity<?> deleteBy(@PathVariable("id") Long id) {
		routeService.deleteBy(id);
		return ResponseEntity.ok().build();
	}

}

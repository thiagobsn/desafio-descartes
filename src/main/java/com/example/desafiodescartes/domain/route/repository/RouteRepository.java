package com.example.desafiodescartes.domain.route.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.desafiodescartes.domain.route.dto.RouteStatusDTO;
import com.example.desafiodescartes.domain.route.entity.Route;

public interface RouteRepository extends JpaRepository<Route, Long>, RouteRepositoryCustom {

	@Query(value = " SELECT new com.example.desafiodescartes.domain.route.dto.RouteStatusDTO(route.id, route.status) FROM Route route")
	public List<RouteStatusDTO> findAllWithStatus();

}

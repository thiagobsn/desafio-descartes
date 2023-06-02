package com.example.desafiodescartes.domain.route.repository;

import java.util.List;

import com.example.desafiodescartes.domain.route.dto.RouteStopLongerDTO;

public interface RouteRepositoryCustom {
	
	public List<RouteStopLongerDTO> listLongerStopsRoutes();

}

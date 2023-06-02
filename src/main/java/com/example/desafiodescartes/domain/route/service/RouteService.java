package com.example.desafiodescartes.domain.route.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.desafiodescartes.domain.route.dto.NewRouteDTO;
import com.example.desafiodescartes.domain.route.dto.RouteDTO;
import com.example.desafiodescartes.domain.route.dto.RouteStatusDTO;
import com.example.desafiodescartes.domain.route.dto.RouteStopLongerDTO;
import com.example.desafiodescartes.domain.route.dto.UpdateRouteDTO;
import com.example.desafiodescartes.domain.route.entity.Route;
import com.example.desafiodescartes.domain.route.entity.Stop;
import com.example.desafiodescartes.domain.route.enums.StatusRouteEnum;
import com.example.desafiodescartes.domain.route.enums.StatusStopEnum;
import com.example.desafiodescartes.domain.route.mapper.RouteMapper;
import com.example.desafiodescartes.domain.route.repository.RouteRepository;
import com.example.desafiodescartes.exception.InvalidLatitudeLongitudeException;
import com.example.desafiodescartes.exception.RouteStartedException;
import com.example.desafiodescartes.util.ManualValidator;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RouteService {

	public final RouteRepository routeRepository;
	
	public final RouteMapper routeMapper;
	
	public final ManualValidator manualValidator;
	
	public RouteDTO findBy(Long id) {
		Route route = routeRepository.findById(id).orElseThrow();
		return routeMapper.toRouteDTO(route);
	}
	
	public Route findById(Long id) {
		return routeRepository.findById(id).orElseThrow();
	}
	
	public List<RouteDTO> findAll() {
		return routeMapper.toRouteDTO(routeRepository.findAll());
	}

	public RouteDTO createNewRoute(NewRouteDTO dto) {
		manualValidator.validate(dto);
		Route route = routeMapper.toRoute(dto);
		route.getStops().forEach(this::validateStopLatitudeLongitude);
		route.getStops().forEach(stop -> stop.setStatus(StatusStopEnum.NOT_ANSWER));
		route.setStatus(StatusRouteEnum.NOT_STARTED);		
		route.addRouteToStops();
		route = routeRepository.save(route);
		return routeMapper.toRouteDTO(route);
	}
	
	public RouteDTO updateRoute(UpdateRouteDTO dto) {
		manualValidator.validate(dto);
		Route route = routeRepository.findById(dto.getId()).orElseThrow();
		
		if(!StatusRouteEnum.NOT_STARTED.equals(route.getStatus()))
			throw new RouteStartedException();
		
		Route routeUpdate = routeMapper.toRoute(dto);
		
		routeUpdate.getStops().forEach(this::validateStopLatitudeLongitude);
		routeUpdate.addRouteToStops();
		
		route = routeRepository.save(routeUpdate);
		return routeMapper.toRouteDTO(routeUpdate);
	}
	
	public void deleteBy(Long id) {
		Route route = routeRepository.findById(id).orElseThrow();

		Boolean isRouteNotStarted = StatusRouteEnum.NOT_STARTED.equals(route.getStatus());
		Boolean isAllStopNotAnswer = route.getStops().stream()
				.allMatch(stop -> StatusStopEnum.NOT_ANSWER.equals(stop.getStatus()));

		if (!isRouteNotStarted || !isAllStopNotAnswer)
			throw new RouteStartedException();

		routeRepository.deleteById(route.getId());
	}
	
	public RouteDTO updateStatusToStarted(Long id) {
		return updateStatus(id, StatusRouteEnum.STARTED);
	}
	
	public RouteDTO updateStatusToDone(Long id) {
		return updateStatus(id, StatusRouteEnum.DONE);
	}
	
	public List<RouteStatusDTO> findAllWithStatus() {
		return routeRepository.findAllWithStatus();
	}
	
	public List<RouteStopLongerDTO> listLongerStopsRoutes() {
		return routeRepository.listLongerStopsRoutes();
	}
	
	private RouteDTO updateStatus(Long id, StatusRouteEnum newStatus) {
		Route route = routeRepository.findById(id).orElseThrow();
		route.setStatus(newStatus);
		route = routeRepository.save(route);
		return routeMapper.toRouteDTO(route);
	}
	
	private void validateStopLatitudeLongitude(Stop stop) {
		if(stop.getLatitude().equals(0D) || stop.getLongitude().equals(0D))
			throw new InvalidLatitudeLongitudeException();
	}
	
}

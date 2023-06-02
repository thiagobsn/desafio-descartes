package com.example.desafiodescartes.domain.stopevent.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.desafiodescartes.domain.route.dto.RouteDTO;
import com.example.desafiodescartes.domain.route.dto.StopDTO;
import com.example.desafiodescartes.domain.route.enums.StatusRouteEnum;
import com.example.desafiodescartes.domain.route.enums.StatusStopEnum;
import com.example.desafiodescartes.domain.route.service.RouteService;
import com.example.desafiodescartes.domain.route.service.StopService;
import com.example.desafiodescartes.domain.stopevent.dto.NewStopEventDTO;
import com.example.desafiodescartes.domain.stopevent.entity.StopEvent;
import com.example.desafiodescartes.domain.stopevent.mapper.StopEventMapper;
import com.example.desafiodescartes.domain.stopevent.repository.StopEventRepository;
import com.example.desafiodescartes.util.HaversineUtil;
import com.example.desafiodescartes.util.ManualValidator;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class StopEventService {

	public final StopEventRepository stopEventRepository;

	public final RouteService routeService;

	public final StopService stopService;

	public final StopEventMapper stopEventMapper;

	public final HaversineUtil haversineUtil;
	
	public final ManualValidator manualValidator;

	public static Integer RANGE_TIME = 5;

	public void addNewEvent(NewStopEventDTO dto) {
		
		manualValidator.validate(dto);

		StopEvent newStopEvent = stopEventMapper.toRouteDTO(dto);
		RouteDTO route = routeService.findBy(newStopEvent.getIdRoute());
		StopEvent lastEvent = stopEventRepository.findTop1ByIdRouteOrderByInstantDesc(newStopEvent.getIdRoute());

		StopEvent savedStopEvent = saveStopEvent(newStopEvent);
		
		if(StatusRouteEnum.DONE.equals(route.getStatus()))
			return;

		if (lastEvent == null) {
			routeService.updateStatusToStarted(savedStopEvent.getIdRoute());
			return;
		}

		Optional<StopDTO> stopOptional = route.getStops().stream()
				.filter(stop -> StatusStopEnum.NOT_ANSWER.equals(stop.getStatus()))
				.filter(stop -> haversineUtil.distanceMeters(savedStopEvent, stop) <= stop.getDeliveryRadius())
				.findFirst();

		if (stopOptional.isEmpty()) {
			updateStopAndRouteIfLastEventStatusEqualsOnAnswer(route, lastEvent, newStopEvent);
			return;
		}

		newStopEvent.setIdStop(stopOptional.get().getStopCode());
		newStopEvent.setStatusStop(stopOptional.get().getStatus());

		if (isSameStop(lastEvent, newStopEvent) && isValidTimeRange(lastEvent, newStopEvent)) {
			newStopEvent.setStatusStop(StatusStopEnum.ON_ANSWER);
			stopService.updateStatusToOnAnswer(newStopEvent.getIdStop());
		}

		saveStopEvent(newStopEvent);
	}

	private StopEvent saveStopEvent(StopEvent stopEvent) {
		return stopEventRepository.save(stopEvent);
	}

	public boolean isValidTimeRange(StopEvent lastEvent, StopEvent newStopEvent) {
		return (getMinutesDifference(lastEvent.getInstant(), newStopEvent.getInstant()) <= RANGE_TIME);
	}

	public static long getMinutesDifference(LocalDateTime date1, LocalDateTime date2) {
		Duration duration = Duration.between(date1, date2);
		long minutes = duration.toMinutes();
		return minutes;
	}

	private Boolean isSameStop(StopEvent lastEvent, StopEvent newStopEvent) {
		return lastEvent.getIdStop() != null && lastEvent.getIdStop().equals(newStopEvent.getIdStop());
	}

	private void updateStopAndRouteIfLastEventStatusEqualsOnAnswer(RouteDTO route, StopEvent lastEvent, StopEvent newStopEvent) {
		if (!StatusStopEnum.ON_ANSWER.equals(lastEvent.getStatusStop()))
			return;

		stopService.updateStatusToAnswer(lastEvent.getIdStop());
		
		newStopEvent.setStatusStop(StatusStopEnum.ANSWER);
		newStopEvent.setIdStop(lastEvent.getIdStop());
		saveStopEvent(newStopEvent);

		Boolean allStopsAnswer = route.getStops().stream()
				.filter(stop -> !stop.getStopCode().equals(lastEvent.getIdStop()))
				.allMatch(stop -> StatusStopEnum.ANSWER.equals(stop.getStatus()));

		if (allStopsAnswer)
			routeService.updateStatusToDone(route.getRouteCode());

	}

}

package com.example.desafiodescartes.domain.stopevent.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.desafiodescartes.domain.route.dto.RouteDTO;
import com.example.desafiodescartes.domain.route.enums.StatusRouteEnum;
import com.example.desafiodescartes.domain.route.enums.StatusStopEnum;
import com.example.desafiodescartes.domain.route.service.RouteService;
import com.example.desafiodescartes.domain.route.service.RouteServiceTest;
import com.example.desafiodescartes.domain.stopevent.dto.NewStopEventDTO;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;

@SpringBootTest
public class StopEventServiceTest {
	
	@Autowired
	public StopEventService stopEventService;
	
	@Autowired
	public RouteService routeService;
	
	@Test
	@DisplayName("Create events to set Route status to done")
	@Transactional
	public void addNewEventScenario1() throws Exception {
		
		RouteDTO routeCreated = createNewRoute();
		
		NewStopEventDTO newStop1Event1 = NewStopEventDTO.builder().routeId(routeCreated.getRouteCode())
				.instant(LocalDateTime.now()).latitude(-3.7645170).longitude(-38.482535).build();
			
		NewStopEventDTO newStop1Event2 = NewStopEventDTO.builder().routeId(routeCreated.getRouteCode())
				.instant(LocalDateTime.now().plusMinutes(2)).latitude(-3.7645170).longitude(-38.482535).build();
		
		NewStopEventDTO newStop1Event3 = NewStopEventDTO.builder().routeId(routeCreated.getRouteCode())
				.instant(LocalDateTime.now().plusMinutes(5)).latitude(-3.762392).longitude(-38.483516).build();

		NewStopEventDTO newStop1Event4 = NewStopEventDTO.builder().routeId(routeCreated.getRouteCode())
				.instant(LocalDateTime.now().plusMinutes(15)).latitude(-3.762392).longitude(-38.483516).build();

		NewStopEventDTO newStop1Event5 = NewStopEventDTO.builder().routeId(routeCreated.getRouteCode())
				.instant(LocalDateTime.now().plusMinutes(17)).latitude(-3.762392).longitude(-38.483516).build();

		NewStopEventDTO newStop1Event6 = NewStopEventDTO.builder().routeId(routeCreated.getRouteCode())
				.instant(LocalDateTime.now().plusMinutes(20)).latitude(-3.7645170).longitude(-38.482535).build();
		
		NewStopEventDTO newStop2Event1 = NewStopEventDTO.builder().routeId(routeCreated.getRouteCode())
				.instant(LocalDateTime.now().plusMinutes(25)).latitude(-3.757701).longitude(-38.483382).build();
			
		NewStopEventDTO newStop2Event2 = NewStopEventDTO.builder().routeId(routeCreated.getRouteCode())
				.instant(LocalDateTime.now().plusMinutes(28)).latitude(-3.757701).longitude(-38.483382).build();
		
		NewStopEventDTO newStop2Event3 = NewStopEventDTO.builder().routeId(routeCreated.getRouteCode())
				.instant(LocalDateTime.now().plusMinutes(30)).latitude(-3.762392).longitude(-38.483516).build();
		
		NewStopEventDTO newStop3Event = NewStopEventDTO.builder().routeId(routeCreated.getRouteCode())
				.instant(LocalDateTime.now().plusMinutes(30)).latitude(-3.767128).longitude(-38.486177).build();
		
		assertEquals(StatusRouteEnum.NOT_STARTED, getStatusRoute(routeCreated.getRouteCode()));
		
		stopEventService.addNewEvent(newStop1Event1);
		assertEquals(StatusRouteEnum.STARTED, getStatusRoute(routeCreated.getRouteCode()));
		
		stopEventService.addNewEvent(newStop1Event2);
		assertEquals(Boolean.FALSE, anyStopWithStatusOnAnswer(routeCreated.getRouteCode(), StatusStopEnum.ON_ANSWER));
		
		stopEventService.addNewEvent(newStop1Event3);
		assertEquals(Boolean.FALSE, anyStopWithStatusOnAnswer(routeCreated.getRouteCode(), StatusStopEnum.ON_ANSWER));
		
		stopEventService.addNewEvent(newStop1Event4);
		assertEquals(Boolean.FALSE, anyStopWithStatusOnAnswer(routeCreated.getRouteCode(), StatusStopEnum.ON_ANSWER));
		
		stopEventService.addNewEvent(newStop1Event5);
		assertEquals(Boolean.TRUE, anyStopWithStatusOnAnswer(routeCreated.getRouteCode(), StatusStopEnum.ON_ANSWER));
		
		stopEventService.addNewEvent(newStop1Event6);
		assertEquals(Boolean.TRUE, anyStopWithStatusOnAnswer(routeCreated.getRouteCode(), StatusStopEnum.ANSWER));
		assertEquals(StatusRouteEnum.STARTED, getStatusRoute(routeCreated.getRouteCode()));
		
		stopEventService.addNewEvent(newStop2Event1);
		assertEquals(Boolean.FALSE, anyStopWithStatusOnAnswer(routeCreated.getRouteCode(), StatusStopEnum.ON_ANSWER));
		
		stopEventService.addNewEvent(newStop2Event2);
		assertEquals(Boolean.TRUE, anyStopWithStatusOnAnswer(routeCreated.getRouteCode(), StatusStopEnum.ON_ANSWER));
		
		stopEventService.addNewEvent(newStop2Event3);
		assertEquals(Boolean.TRUE, anyStopWithStatusOnAnswer(routeCreated.getRouteCode(), StatusStopEnum.ANSWER));
		assertEquals(StatusRouteEnum.DONE, getStatusRoute(routeCreated.getRouteCode()));
		
		stopEventService.addNewEvent(newStop3Event);
		assertEquals(StatusRouteEnum.DONE, getStatusRoute(routeCreated.getRouteCode()));
		
	}
	
	@Test
	@DisplayName("Create events to set Route status to done")
	@Transactional
	public void addNewEventScenario2() throws Exception {
		
		RouteDTO routeCreated = createNewRoute();
		
		NewStopEventDTO newStopEvent = new NewStopEventDTO();
			
		assertEquals(StatusRouteEnum.NOT_STARTED, getStatusRoute(routeCreated.getRouteCode()));
		
		assertThrows(ConstraintViolationException.class, () -> {
			stopEventService.addNewEvent(newStopEvent);
		});
		
		assertEquals(StatusRouteEnum.NOT_STARTED, getStatusRoute(routeCreated.getRouteCode()));
		
		newStopEvent.setRouteId(routeCreated.getRouteCode());
		assertThrows(ConstraintViolationException.class, () -> {
			stopEventService.addNewEvent(newStopEvent);
		});
		
		assertEquals(StatusRouteEnum.NOT_STARTED, getStatusRoute(routeCreated.getRouteCode()));
		
		newStopEvent.setRouteId(routeCreated.getRouteCode());
		assertThrows(IllegalArgumentException.class, () -> {
			stopEventService.addNewEvent(null);
		});
		
		assertEquals(StatusRouteEnum.NOT_STARTED, getStatusRoute(routeCreated.getRouteCode()));

	}
	
	private RouteDTO createNewRoute() {
		return routeService.createNewRoute(RouteServiceTest.buildRoutValid());
	}
	
	private Boolean anyStopWithStatusOnAnswer(Long idRoute, StatusStopEnum status) {
		RouteDTO route = getRoute(idRoute);
		return !route.getStops().stream().filter(stop -> status.equals(stop.getStatus())).toList()
				.isEmpty();
	}
	
	private StatusRouteEnum getStatusRoute(Long idRoute) {
		return getRoute(idRoute).getStatus();
	}
	
	private RouteDTO getRoute(Long idRoute) {
		return routeService.findBy(idRoute);
	}

}

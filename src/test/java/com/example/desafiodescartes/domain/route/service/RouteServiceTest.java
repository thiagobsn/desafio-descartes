package com.example.desafiodescartes.domain.route.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.desafiodescartes.domain.route.dto.NewRouteDTO;
import com.example.desafiodescartes.domain.route.dto.NewStopDTO;
import com.example.desafiodescartes.domain.route.dto.RouteDTO;
import com.example.desafiodescartes.domain.route.dto.UpdateRouteDTO;
import com.example.desafiodescartes.domain.route.dto.UpdateStopDTO;
import com.example.desafiodescartes.domain.route.enums.StatusStopEnum;
import com.example.desafiodescartes.exception.InvalidLatitudeLongitudeException;
import com.example.desafiodescartes.exception.RouteStartedException;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;

@SpringBootTest
public class RouteServiceTest {

	@Autowired
	public RouteService routeService;
	
	@Autowired
	public StopService stopService;

	@Test
	@DisplayName("Create new Route")
	public void createNewRouteScenario1() throws Exception {

		NewRouteDTO newRoute = buildRoutValid();

		RouteDTO routeDTO = routeService.createNewRoute(newRoute);
		assertNotNull(routeDTO);
		assertNotNull(routeDTO.getRouteCode());
	}

	@Test
	@DisplayName("Create new Route with invalid latitude/longitude")
	public void createNewRouteScenario2() throws Exception {

		NewStopDTO stop1 = NewStopDTO.builder().description("Stop 1 test").latitude(0.0).longitude(-38.483516)
				.deliveryRadius(30).build();

		NewRouteDTO newRoute = NewRouteDTO.builder().date(LocalDate.now()).stops(List.of(stop1)).build();

		assertThrows(InvalidLatitudeLongitudeException.class, () -> {
			routeService.createNewRoute(newRoute);
		});
		
		stop1.setLatitude(-3.762392);
		stop1.setLongitude(0.0);
		
		newRoute.setStops(List.of(stop1));
		
		assertThrows(InvalidLatitudeLongitudeException.class, () -> {
			routeService.createNewRoute(newRoute);
		});

	}

	@Test
	@DisplayName("Create new Route with invalid NewStopDTO")
	public void createNewRouteScenario3() throws Exception {

		NewRouteDTO newRoute = new NewRouteDTO();

		assertThrows(ConstraintViolationException.class, () -> {
			routeService.createNewRoute(newRoute);
		});

		newRoute.setDate(LocalDate.now());
		assertThrows(ConstraintViolationException.class, () -> {
			routeService.createNewRoute(newRoute);
		});

		newRoute.setStops(new ArrayList<>());
		assertThrows(ConstraintViolationException.class, () -> {
			routeService.createNewRoute(newRoute);
		});

		NewStopDTO stop1 = new NewStopDTO();

		newRoute.setStops(List.of(stop1));
		assertThrows(ConstraintViolationException.class, () -> {
			routeService.createNewRoute(newRoute);
		});

	}

	@Test
	@DisplayName("Update route not started")
	public void updateRouteRouteScenario1() throws Exception {

		RouteDTO routeDTO = routeService.createNewRoute(buildRoutValid());

		UpdateStopDTO stop1 = UpdateStopDTO.builder().description("Stop 3 test").latitude(-3.762392)
				.longitude(-38.483516).status(StatusStopEnum.NOT_ANSWER).deliveryRadius(30).build();

		UpdateRouteDTO newRoute = UpdateRouteDTO.builder().id(routeDTO.getRouteCode()).date(LocalDate.now().plusDays(1))
				.stops(List.of(stop1)).status(routeDTO.getStatus()).build();

		routeService.updateRoute(newRoute);
	}

	@Test
	@DisplayName("Update route started/done")
	public void updateRouteRouteScenario2() throws Exception {

		RouteDTO routeDTO = routeService.createNewRoute(buildRoutValid());
		routeService.updateStatusToStarted(routeDTO.getRouteCode());

		UpdateStopDTO stop1 = UpdateStopDTO.builder().description("Stop 3 test").latitude(-3.762392)
				.longitude(-38.483516).status(StatusStopEnum.NOT_ANSWER).deliveryRadius(30).build();

		UpdateRouteDTO newRoute = UpdateRouteDTO.builder().id(routeDTO.getRouteCode()).date(LocalDate.now().plusDays(1))
				.stops(List.of(stop1)).status(routeDTO.getStatus()).build();

		assertThrows(RouteStartedException.class, () -> {
			routeService.updateRoute(newRoute);
		});

		routeService.updateStatusToDone(routeDTO.getRouteCode());

		assertThrows(RouteStartedException.class, () -> {
			routeService.updateRoute(newRoute);
		});

	}

	@Test
	@DisplayName("Delete route not started")
	@Transactional
	public void deleteByScenario1() throws Exception {
		RouteDTO routeDTO = routeService.createNewRoute(buildRoutValid());

		routeService.deleteBy(routeDTO.getRouteCode());
	}

	@Test
	@DisplayName("Delete route started/dene")
	@Transactional
	public void deleteByScenario2() throws Exception {
		RouteDTO routeDTO = routeService.createNewRoute(buildRoutValid());
		
		stopService.updateStatusToOnAnswer(routeDTO.getStops().get(0).getStopCode());
		assertThrows(RouteStartedException.class, () -> {
			routeService.deleteBy(routeDTO.getRouteCode());
		});
		
		stopService.updateStatusToAnswer(routeDTO.getStops().get(0).getStopCode());
		assertThrows(RouteStartedException.class, () -> {
			routeService.deleteBy(routeDTO.getRouteCode());
		});
		
		routeService.updateStatusToStarted(routeDTO.getRouteCode());

		assertThrows(RouteStartedException.class, () -> {
			routeService.deleteBy(routeDTO.getRouteCode());
		});

		routeService.updateStatusToDone(routeDTO.getRouteCode());

		assertThrows(RouteStartedException.class, () -> {
			routeService.deleteBy(routeDTO.getRouteCode());
		});
		
	}

	public static NewRouteDTO buildRoutValid() {
		NewStopDTO stop1 = NewStopDTO.builder().description("Stop 1 test").latitude(-3.762392).longitude(-38.483516)
				.deliveryRadius(30).build();

		NewStopDTO stop2 = NewStopDTO.builder().description("Stop 2 test").latitude(-3.757701).longitude(-38.483382)
				.deliveryRadius(30).build();

		return NewRouteDTO.builder().date(LocalDate.now()).stops(List.of(stop1, stop2)).build();

	}

}

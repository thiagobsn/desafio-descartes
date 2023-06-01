package com.example.desafiodescartes.domain.stopevent.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.example.desafiodescartes.domain.route.entity.Route;
import com.example.desafiodescartes.domain.route.entity.Stop;
import com.example.desafiodescartes.domain.route.enums.StatusRouteEnum;
import com.example.desafiodescartes.domain.route.enums.StatusStopEnum;
import com.example.desafiodescartes.domain.stopevent.entity.StopEvent;

@DataJpaTest
public class StopEventRepositoryTest {

	@Autowired
	private StopEventRepository stopEventRepository;

	@Autowired
	private TestEntityManager testEntityManager;

	@Test
	@DisplayName("Find last event ordened by Instant")
	void findTop1ByIdRouteOrderByInstantDescScenario1() throws Exception {

		Stop stop1 = Stop.builder().description("Stop 1 test").latitude(-3.762392).longitude(-38.483516)
				.deliveryRadius(30).status(StatusStopEnum.NOT_ANSWER).build();

		Route newRoute = Route.builder().date(LocalDate.now()).status(StatusRouteEnum.NOT_STARTED).stops(List.of(stop1))
				.build();
		newRoute.addRouteToStops();

		Route route = testEntityManager.persist(newRoute);

		StopEvent stopEvent1 = StopEvent.builder().idRoute(route.getId()).instant(LocalDateTime.now())
				.latitude(-3.762392).longitude(-38.483516).build();

		StopEvent stopEvent2 = StopEvent.builder().idRoute(route.getId()).instant(LocalDateTime.now().plusMinutes(5))
				.latitude(-3.762392).longitude(-38.483516).build();

		StopEvent stopEvent3 = StopEvent.builder().idRoute(route.getId()).instant(LocalDateTime.now().plusMinutes(2))
				.latitude(-3.762392).longitude(-38.483516).build();

		testEntityManager.persist(stopEvent1);
		testEntityManager.persist(stopEvent2);
		testEntityManager.persist(stopEvent3);

		StopEvent stopEvent = stopEventRepository.findTop1ByIdRouteOrderByInstantDesc(route.getId());

		assertEquals(stopEvent.getId(), stopEvent2.getId());
		assertEquals(stopEvent.getInstant(), stopEvent2.getInstant());

	}

}

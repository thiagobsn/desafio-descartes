package com.example.desafiodescartes.domain.route.service;

import org.springframework.stereotype.Service;

import com.example.desafiodescartes.domain.route.dto.StopDTO;
import com.example.desafiodescartes.domain.route.entity.Stop;
import com.example.desafiodescartes.domain.route.enums.StatusStopEnum;
import com.example.desafiodescartes.domain.route.mapper.RouteMapper;
import com.example.desafiodescartes.domain.route.repository.StopRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class StopService {

	public final StopRepository stopRepository;

	public final RouteMapper routeMapper;

	public StopDTO updateStatusToOnAnswer(Long id) {
		return updateStatus(id, StatusStopEnum.ON_ANSWER);
	}

	public StopDTO updateStatusToAnswer(Long id) {
		return updateStatus(id, StatusStopEnum.ANSWER);
	}

	private StopDTO updateStatus(Long id, StatusStopEnum newStatus) {
		Stop stop = stopRepository.findById(id).orElseThrow();
		stop.setStatus(newStatus);
		stop = stopRepository.save(stop);
		return routeMapper.toStopDTO(stop);
	}

}

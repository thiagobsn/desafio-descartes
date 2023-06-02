package com.example.desafiodescartes.domain.route.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.desafiodescartes.domain.route.entity.Stop;
import com.example.desafiodescartes.domain.route.enums.StatusStopEnum;

public interface StopRepository extends JpaRepository<Stop, Long> {
	
	public List<Stop> findAllByStatus(StatusStopEnum statusStop);

}

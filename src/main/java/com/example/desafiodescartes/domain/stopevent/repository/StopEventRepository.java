package com.example.desafiodescartes.domain.stopevent.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.desafiodescartes.domain.stopevent.entity.StopEvent;

public interface StopEventRepository extends JpaRepository<StopEvent, Long> {

	public StopEvent findTop1ByIdRouteOrderByInstantDesc(Long idRoute);
	
}

package com.example.desafiodescartes.domain.stopevent.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.desafiodescartes.domain.stopevent.entity.StopEvent;

public interface StopEventRepository extends JpaRepository<StopEvent, Long> {

	public List<StopEvent> findAllByIdRouteOrderByIdDesc(Long idRoute);
	
	public StopEvent findTop1ByIdRouteOrderByInstantDesc(Long idRoute);

}

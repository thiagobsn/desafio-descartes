package com.example.desafiodescartes.domain.route.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.desafiodescartes.domain.route.entity.Route;

public interface RouteRepository extends JpaRepository<Route, Long>{

}

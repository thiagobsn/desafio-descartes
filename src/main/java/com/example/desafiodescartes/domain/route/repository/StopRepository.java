package com.example.desafiodescartes.domain.route.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.desafiodescartes.domain.route.entity.Stop;

public interface StopRepository extends JpaRepository<Stop, Long>{

}

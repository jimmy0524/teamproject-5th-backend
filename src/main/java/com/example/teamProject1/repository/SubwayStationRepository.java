package com.example.teamProject1.repository;

import com.example.teamProject1.model.SubwayStation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubwayStationRepository extends JpaRepository<SubwayStation, Integer> {
    Optional<SubwayStation> findByName(String name);
}

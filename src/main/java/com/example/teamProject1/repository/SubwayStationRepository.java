package com.example.teamProject1.repository;

import com.example.teamProject1.model.LikeStation;
import com.example.teamProject1.model.SubwayStation;
import com.example.teamProject1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubwayStationRepository extends JpaRepository<SubwayStation, Integer> {
    Optional<SubwayStation> findByName(String name);
    Optional<SubwayStation> findByStationID(Integer id);
}

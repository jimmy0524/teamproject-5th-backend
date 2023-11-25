package com.example.teamProject1.repository;

import com.example.teamProject1.model.LikeStation;
import com.example.teamProject1.model.SubwayStation;
import com.example.teamProject1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeStationRepository extends JpaRepository<LikeStation, Integer> {
    boolean existsByUserAndSubwayStation(User user, SubwayStation subwayStation);
    void deleteByUserAndSubwayStation(User user,SubwayStation subwayStation);
    List<LikeStation> findByUser(User user);
}

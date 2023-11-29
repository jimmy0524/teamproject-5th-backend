package com.example.teamProject1.repository;


import com.example.teamProject1.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface BoardRepository extends JpaRepository<Board,Integer> {
    List<Board> findByLikeCountGreaterThanEqualAndCreateDateGreaterThan(Integer likeCount, Timestamp createdDate);
}

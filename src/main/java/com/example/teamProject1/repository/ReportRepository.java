package com.example.teamProject1.repository;

import com.example.teamProject1.model.Board;
import com.example.teamProject1.model.Report;
import com.example.teamProject1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report,Integer> {
    boolean existsByUserAndBoard(User user, Board board);
}

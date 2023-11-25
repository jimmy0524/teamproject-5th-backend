package com.example.teamProject1.repository;

import com.example.teamProject1.model.Board;
import com.example.teamProject1.model.Scrap;
import com.example.teamProject1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScrapRepository extends JpaRepository<Scrap, Integer> {
    boolean existsByUserAndBoard(User user, Board board);
    void deleteByUserAndBoard(User user,Board board);
    void deleteByBoard(Board board);
    List<Scrap> findByUser(User user);
}

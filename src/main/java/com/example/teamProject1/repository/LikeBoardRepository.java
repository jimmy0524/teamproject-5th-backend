package com.example.teamProject1.repository;

import com.example.teamProject1.model.Board;
import com.example.teamProject1.model.LikeBoard;
import com.example.teamProject1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeBoardRepository extends JpaRepository<LikeBoard, Integer> {
    boolean existsByUserAndBoard(User user, Board board);
    void deleteByUserAndBoard(User user,Board board);
    void deleteByBoard(Board board);
}

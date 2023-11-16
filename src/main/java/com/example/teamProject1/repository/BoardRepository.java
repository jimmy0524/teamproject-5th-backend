package com.example.teamProject1.repository;


import com.example.teamProject1.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board,Integer> {
}

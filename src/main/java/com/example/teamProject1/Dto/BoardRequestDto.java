package com.example.teamProject1.Dto;

import com.example.teamProject1.model.Board;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardRequestDto {
    private Board board;
    private String subwayStationName;
}

package com.example.teamProject1.Dto;

import com.example.teamProject1.model.Board;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardRequestDto {
    private Board board;
    private List<String> imageUrls;
    private String subwayStationName;
}

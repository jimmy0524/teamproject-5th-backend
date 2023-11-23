package com.example.teamProject1.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length=10)
    private int start;

    @Column(nullable = false, length=10)
    private int end;

    @Column(nullable = false)
    private int time;

    @Column(nullable = false)
    private int dist;

    @Column(nullable = false)
    private int cost;
}

package com.example.teamProject1.node;
import lombok.Data;

@Data
public class StationNode implements Comparable<StationNode> {
    private int station;
    private int distance;

    public StationNode(int station, int distance) {
        this.station = station;
        this.distance = distance;
    }

    @Override
    public int compareTo(StationNode other) {
        return Integer.compare(this.distance, other.distance);
    }
}
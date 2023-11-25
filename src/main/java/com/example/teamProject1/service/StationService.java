package com.example.teamProject1.service;

import com.example.teamProject1.Dto.SubwayLogicDto;
import com.example.teamProject1.model.Station;
import com.example.teamProject1.model.SubwayStation;
import com.example.teamProject1.node.StationNode;
import com.example.teamProject1.repository.StationRepository;
import com.example.teamProject1.repository.SubwayStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

@Service
public class StationService {

    @Autowired
    private StationRepository subwayRepository;

    @Autowired
    private SubwayStationRepository subwayStationRepository;


    public int shortestPath(SubwayLogicDto subwayLogicDto, String type) {

        SubwayStation startSubwayStation = subwayStationRepository.findByName(subwayLogicDto.getStartStationName())
                .orElseThrow(() -> new IllegalArgumentException("해당 역이 존재하지 않습니다."));

        SubwayStation endSubwayStation = subwayStationRepository.findByName(subwayLogicDto.getEndStationName())
                .orElseThrow(() -> new IllegalArgumentException("해당 역이 존재하지 않습니다."));

        List<Station> subways = subwayRepository.findAll();
        int n = 1000;
        List<ArrayList<StationNode>> graph = new ArrayList<>();
        int[] d = new int[n + 1];
        Arrays.fill(d, Integer.MAX_VALUE);

        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }

        for (Station subway : subways) {
            int value;
            switch (type) {
                case "distance":
                    value = subway.getDist();
                    break;
                case "time":
                    value = subway.getTime();
                    break;
                case "cost":
                    value = subway.getCost();
                    break;
                default:
                    throw new IllegalArgumentException("Invalid type: " + type);
            }
            graph.get(subway.getStart()).add(new StationNode(subway.getEnd(), value));
            graph.get(subway.getEnd()).add(new StationNode(subway.getStart(), value)); // 양방향 엣지 추가
        }

        PriorityQueue<StationNode> pq = new PriorityQueue<>();
        pq.offer(new StationNode(startSubwayStation.getStationID(), 0));
        d[startSubwayStation.getStationID()] = 0;

        while (!pq.isEmpty()) {
            StationNode node = pq.poll();
            int dist = node.getDistance();
            int now = node.getStation();

            if (d[now] < dist) continue;
            for (int i = 0; i < graph.get(now).size(); i++) {
                int cost = d[now] + graph.get(now).get(i).getDistance();
                if (cost < d[graph.get(now).get(i).getStation()]) {
                    d[graph.get(now).get(i).getStation()] = cost;
                    pq.offer(new StationNode(graph.get(now).get(i).getStation(), cost));
                }
            }
        }

        return d[endSubwayStation.getStationID()];
    }
}

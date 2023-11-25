package com.example.teamProject1.service;

import com.example.teamProject1.model.*;
import com.example.teamProject1.node.StationNode;
import com.example.teamProject1.repository.LikeStationRepository;
import com.example.teamProject1.repository.StationRepository;
import com.example.teamProject1.repository.SubwayStationRepository;
import com.example.teamProject1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private LikeStationRepository likeStationRepository;

    @Autowired
    private UserRepository userRepository;

    //최단거리,최소비용,최소시간
    @Transactional
    public int shortestPath(int start, int end, String type) {
        List<Station> subways = subwayRepository.findAll();
        int n = 1000;
        List<ArrayList<StationNode>> graph = new ArrayList<>();
        int[] d = new int[n+1];
        Arrays.fill(d, Integer.MAX_VALUE);

        for(int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }

        for(Station subway : subways) {
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
        pq.offer(new StationNode(start, 0));
        d[start] = 0;

        while(!pq.isEmpty()) {
            StationNode node = pq.poll();
            int dist = node.getDistance();
            int now = node.getStation();

            if(d[now] < dist) continue;
            for(int i = 0; i < graph.get(now).size(); i++) {
                int cost = d[now] + graph.get(now).get(i).getDistance();
                if(cost < d[graph.get(now).get(i).getStation()]) {
                    d[graph.get(now).get(i).getStation()] = cost;
                    pq.offer(new StationNode(graph.get(now).get(i).getStation(), cost));
                }
            }
        }

        return d[end];
    }

    //모든 역 정보 넘겨주기
    @Transactional
    public List<SubwayStation> stationAll(){
        return subwayStationRepository.findAll();
    }

    //역 즐겨찾기
    @Transactional
    public void likeStation(int id, User user) {

        SubwayStation findStation = subwayStationRepository.findByStationID(id)
                .orElseThrow(() -> {
                    return new IllegalArgumentException("아이디를 찾을 수 없습니다.");
                });

        if(!likeStationRepository.existsByUserAndSubwayStation(user,findStation)){
            LikeStation like= LikeStation.builder()
                    .user(user)
                    .subwayStation(findStation)
                    .build();
            likeStationRepository.save(like);
        }
        else{
            likeStationRepository.deleteByUserAndSubwayStation(user,findStation);
        }
    }

//    //역 즐겨찾기 테스트
//    @Transactional
//    public void likeStation(int id) {
//
//        SubwayStation findStation = subwayStationRepository.findByStationID(id)
//                .orElseThrow(() -> {
//                    return new IllegalArgumentException("아이디를 찾을 수 없습니다.");
//                });
//
//        User user = userRepository.findById(1)
//                .orElseThrow(() -> {
//                    return new IllegalArgumentException("아이디를 찾을 수 없습니다.");
//                });
//
//        if(!likeStationRepository.existsByUserAndSubwayStation(user,findStation)){
//            LikeStation like= LikeStation.builder()
//                    .user(user)
//                    .subwayStation(findStation)
//                    .build();
//            likeStationRepository.save(like);
//        }
//        else{
//            likeStationRepository.deleteByUserAndSubwayStation(user,findStation);
//        }
//    }

    //역 즐겨찾기 목록
    @Transactional
    public List<SubwayStation> likeStationList(User user) {
        List<SubwayStation> stations = new ArrayList<>();
        List<LikeStation> likes = likeStationRepository.findByUser(user);
        for(LikeStation like:likes){
            stations.add(like.getSubwayStation());
        }
        return stations;
    }
}

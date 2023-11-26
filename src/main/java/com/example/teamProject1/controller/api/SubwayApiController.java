package com.example.teamProject1.controller.api;

import com.example.teamProject1.Dto.SubwayLogicDto;
import com.example.teamProject1.Dto.ResponseDto;
import com.example.teamProject1.config.auth.PrincipalDetail;
import com.example.teamProject1.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class SubwayApiController {
    @Autowired
    StationService stationService;

    //경로 찾은 후 화면
    @GetMapping("/api/subway/search-way")
    public Map<String, Object> subway(@RequestBody SubwayLogicDto subwayLogicDto, @AuthenticationPrincipal PrincipalDetail principal) {
        int cost = stationService.shortestPath(subwayLogicDto, "cost");
        int dist = stationService.shortestPath(subwayLogicDto, "distance");
        int time = stationService.shortestPath(subwayLogicDto, "time");
        Map<String, Object> map = new HashMap<>();
        map.put("cost",cost);
        map.put("dist",dist);
        map.put("time",time);
        map.put("principal", principal.getUser());
        return map;
    }

    @PostMapping("api/subway/{id}") //역 즐겨찾기
    public ResponseDto<Integer> subwaySearch(@PathVariable int id, @AuthenticationPrincipal PrincipalDetail principal) {
        stationService.likeStation(id,principal.getUser());
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

//    @PostMapping("api/subway/{id}") //역 즐겨찾기 테스트
//    public ResponseDto<Integer> subwaySearch(@PathVariable int id) {
//        stationService.likeStation(id);
//        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
//    }

    @GetMapping("api/subway") //경로 찾기 전 화면
    public Map<String, Object> subwaySearch(@AuthenticationPrincipal PrincipalDetail principal) {
        Map<String, Object> map = new HashMap<>();
        map.put("stationAll", stationService.stationAll());
        map.put("stationLike", stationService.likeStationList(principal.getUser()));
        map.put("principal", principal.getUser());
        return map;
    }
}

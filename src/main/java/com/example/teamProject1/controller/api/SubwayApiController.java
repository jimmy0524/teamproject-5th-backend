package com.example.teamProject1.controller.api;

import com.example.teamProject1.Dto.ResponseDto;
import com.example.teamProject1.config.auth.PrincipalDetail;
import com.example.teamProject1.model.User;
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
    @GetMapping("/api/subway/search-way/{start}/{end}")
    public Map<String, Object> subway(@PathVariable String start, @PathVariable String end, @AuthenticationPrincipal PrincipalDetail principal) {
        int cost = stationService.shortestPath(start, end, "cost");
        int dist = stationService.shortestPath(start, end, "distance");
        int time = stationService.shortestPath(start, end, "time");
        Map<String, Object> map = new HashMap<>();
        map.put("cost",cost);
        map.put("dist",dist);
        map.put("time",time);
        if (principal != null) { // 로그인한 사용자만 추가 정보 제공
            User user = principal.getUser();
            user.setPassword(null);  // password 필드 제거
            map.put("principal", user);
        }
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
        if (principal != null) { // 로그인한 사용자만 추가 정보 제공
            map.put("stationLike", stationService.likeStationList(principal.getUser()));
            User user = principal.getUser();
            user.setPassword(null);  // password 필드 제거
            map.put("principal", user);
        }
        return map;
    }
}

package com.example.teamProject1.controller.api;

import com.example.teamProject1.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BoardApiController {

    @Autowired
    StationService stationService;

    @GetMapping("/subway")
    public String login() {
        int cost = stationService.shortestPath(102, 601, "cost");
        int dist = stationService.shortestPath(102, 601, "distance");
        int time = stationService.shortestPath(102, 601, "time");
        String result = "cost : " + cost +"dist : "+dist + "time : " + time;
        return result;
    }
}

package com.example.teamProject1.controller.api;

import com.example.teamProject1.Dto.SubwayLogicDto;
import com.example.teamProject1.model.SubwayStation;
import com.example.teamProject1.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SubwayApiController {
    @Autowired
    StationService stationService;

//    @GetMapping("/subway/{start}/{end}")
//    public String subway(Model model, @PathVariable int start, @PathVariable int end) {
//        int cost = stationService.shortestPath(start, end, "cost");
//        int dist = stationService.shortestPath(start, end, "distance");
//        int time = stationService.shortestPath(start, end, "time");
//        model.addAttribute("cost",cost);
//        model.addAttribute("dist",dist);
//        model.addAttribute("time",time);
//        String result = "cost : " + cost +"dist : "+dist + "time : " + time;
//        return result;
//    }

    @GetMapping("/api/subway")
    public String subway(@RequestBody SubwayLogicDto subwayLogicDto, Model model) {
        int cost = stationService.shortestPath(subwayLogicDto, "cost");
        int dist = stationService.shortestPath(subwayLogicDto, "distance");
        int time = stationService.shortestPath(subwayLogicDto, "time");
        model.addAttribute("cost",cost);
        model.addAttribute("dist",dist);
        model.addAttribute("time",time);
        String result = "cost : " + cost +"dist : "+dist + "time : " + time;
        return result;
    }

}

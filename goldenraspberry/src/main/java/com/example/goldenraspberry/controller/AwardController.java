package com.example.goldenraspberry.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.goldenraspberry.service.AwardService;

@RestController
public class AwardController {

    private final AwardService awardService;

    public AwardController(AwardService awardService) {
        this.awardService = awardService;
    }

    @GetMapping("/awards/intervals")
    public Map<String, List<Map<String, Object>>> getIntervals() {
        return awardService.getAwardIntervals();
    }
}

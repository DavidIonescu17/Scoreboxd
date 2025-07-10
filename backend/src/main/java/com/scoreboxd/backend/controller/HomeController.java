package com.scoreboxd.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.scoreboxd.backend.service.MatchSyncService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class HomeController {

    private final MatchSyncService syncService;

    @GetMapping("/")
    public String hello() {
        return "Scoreboxd backend is running! GBRRRRRRRRR!";
    }

    @PostMapping("/admin/sync-football")
    public String syncNow() {
        syncService.importToday();
        return "OK";
    }

    @GetMapping("/admin/sync-football")
    public String syncGet() {
        return syncNow();
    }
}
package com.scoreboxd.backend.controller;

import com.scoreboxd.backend.domain.Match;
import com.scoreboxd.backend.domain.Team;
import com.scoreboxd.backend.repository.MatchRepository;
import com.scoreboxd.backend.repository.TeamRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/data")
@RequiredArgsConstructor
public class DataController {

    private final MatchRepository matchRepo;
    private final TeamRepository teamRepo;
    private static final Logger log = LoggerFactory.getLogger(DataController.class);

    @GetMapping("/matches")
    public Iterable<Match> getAllMatches() {
        Iterable<Match> matches = matchRepo.findAll();
        log.info("Found {} matches", matchRepo.count());
        return matches;
    }

    @GetMapping("/teams")
    public Iterable<Team> getAllTeams() {
        Iterable<Team> teams = teamRepo.findAll();
        log.info("Found {} teams", teamRepo.count());
        return teams;
    }

    @GetMapping("/matches/{id}")
    public Match getMatch(@PathVariable Long id) {
        Match match = matchRepo.findById(id).orElse(null);
        if (match != null) {
            log.info("Found match with ID {}: {} vs {}", 
                id, match.getHomeTeam().getName(), match.getAwayTeam().getName());
        }
        return match;
    }

    @GetMapping("/teams/{id}")
    public Team getTeam(@PathVariable Integer id) {
        Team team = teamRepo.findById(id).orElse(null);
        if (team != null) {
            log.info("Found team with ID {}: {}", id, team.getName());
        }
        return team;
    }

    @GetMapping("/matches/count")
    public long getMatchCount() {
        long count = matchRepo.count();
        log.info("Total matches count: {}", count);
        return count;
    }

    @GetMapping("/teams/count")
    public long getTeamCount() {
        long count = teamRepo.count();
        log.info("Total teams count: {}", count);
        return count;
    }
}

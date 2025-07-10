// src/main/java/com/scoreboxd/backend/service/MatchSyncService.java
package com.scoreboxd.backend.service;

import com.scoreboxd.backend.domain.*;
import com.scoreboxd.backend.FootballApiClient;
import com.scoreboxd.backend.dto.FootballFixtureDto;
import com.scoreboxd.backend.repository.MatchRepository;
import com.scoreboxd.backend.repository.TeamRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List; // Added missing import
import java.time.OffsetDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MatchSyncService {

    private final FootballApiClient client;
    private final TeamRepository teamRepo;
    private final MatchRepository matchRepo;
    private static final Logger log = LoggerFactory.getLogger(MatchSyncService.class);

    /** Call this manually first; later annotate with @Scheduled */
    @Transactional
    public void importToday() {
        OffsetDateTime today = OffsetDateTime.now().withHour(0).withMinute(0).withSecond(0);
        OffsetDateTime yesterday = today.minusDays(1);
        log.info("Starting to sync matches for today: {} and yesterday: {}", today, yesterday);

        // Fetch matches for both days
        List<OffsetDateTime> datesToCheck = List.of(today, yesterday);
        for (OffsetDateTime date : datesToCheck) {
            log.info("Fetching matches for date: {}", date);
            
            // Use the FootballApiClient to fetch matches for this date
            List<FootballFixtureDto> fixtures = client.fetchFixtures(date);
            if (fixtures.isEmpty()) {
                log.warn("No fixtures found for date: {}", date);
                continue;
            }

            for (FootballFixtureDto fix : fixtures) {
                log.info("Processing match: {} vs {}", fix.teams().home().name(), fix.teams().away().name());
                Team home = upsertTeam(fix.teams().home());
                Team away = upsertTeam(fix.teams().away());
                upsertMatch(fix, home, away);
            }
        }

        log.info("Sync completed. Total matches: {}", matchRepo.count());
        log.info("Total teams: {}", teamRepo.count());

        log.info("Sync completed. Total matches: {}", matchRepo.count());
        log.info("Total teams: {}", teamRepo.count());
    }

    private Team upsertTeam(FootballFixtureDto.Teams.Team t) {
        Team existing = teamRepo.findById((int) t.id()).orElse(null);
        if (existing != null) {
            log.info("Found existing team with ID {}: {}", t.id(), t.name());
            return existing;
        }
        Team newTeam = new Team((int)t.id(), t.name(), t.logo(), Sport.FOOTBALL);
        Team saved = teamRepo.save(newTeam);
        log.info("Created new team: {}", saved.getName());
        return saved;
    }

    private void upsertMatch(FootballFixtureDto dto, Team home, Team away) {
        Match existing = matchRepo.findById(dto.fixture().id()).orElse(null);
        if (existing != null) {
            log.info("Updating existing match with ID: {}", dto.fixture().id());
        } else {
            log.info("Creating new match with ID: {}", dto.fixture().id());
        }

        Match m = existing != null ? existing : new Match();
        m.setId(dto.fixture().id());
        m.setSport(Sport.FOOTBALL);
        m.setMatchDate(dto.fixture().date());
        m.setCompetition("API-Football");          // refine later with dto.league()
        m.setScore(formatScore(dto));
        m.setHomeTeam(home);
        m.setAwayTeam(away);

        Match saved = matchRepo.save(m);
        log.info("Saved match: {} vs {}", home.getName(), away.getName());
    }

    private String formatScore(FootballFixtureDto dto) {
        Integer h = dto.score().fulltime().home();
        Integer a = dto.score().fulltime().away();
        String score = (h == null || a == null) ? null : h + "-" + a;
        log.debug("Formatted score: {}", score);
        return score;
    }
}

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

import java.time.OffsetDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MatchSyncService {

    private final FootballApiClient client;
    private final TeamRepository teamRepo;
    private final MatchRepository matchRepo;

    /** Call this manually first; later annotate with @Scheduled */
    @Transactional
    public void importYesterday() {
        OffsetDateTime target = OffsetDateTime.now().minusDays(1);

        client.fetchFixtures(target).forEach(fix -> {
            Team home = upsertTeam(fix.teams().home());
            Team away = upsertTeam(fix.teams().away());
            upsertMatch(fix, home, away);
        });
    }

    private Team upsertTeam(FootballFixtureDto.Teams.Team t) {
        return teamRepo.findById((int) t.id())
                .orElseGet(() -> teamRepo.save(
                        new Team((int)t.id(), t.name(), t.logo(), Sport.FOOTBALL)));
    }

    private void upsertMatch(FootballFixtureDto dto, Team home, Team away) {
        Match m = matchRepo.findById(dto.fixture().id())
                .orElseGet(Match::new);

        m.setId(dto.fixture().id());
        m.setSport(Sport.FOOTBALL);
        m.setMatchDate(dto.fixture().date());
        m.setCompetition("API-Football");          // refine later with dto.league()
        m.setScore(formatScore(dto));
        m.setHomeTeam(home);
        m.setAwayTeam(away);

        matchRepo.save(m);
    }

    private String formatScore(FootballFixtureDto dto) {
        Integer h = dto.score().fulltime().home();
        Integer a = dto.score().fulltime().away();
        return (h == null || a == null) ? null : h + "-" + a;
    }
}

package scoreboard;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

class SimpleScoreBoard implements ScoreBoard {

    private final AtomicInteger count = new AtomicInteger(0);
    private final ConcurrentMap<UUID, SimpleEntry<Match, Integer>> matches = new ConcurrentHashMap<>();

    @Override
    public Match startNewMatch(String homeTeam, String awayTeam) {
        var m = new Match(homeTeam, awayTeam);
        var i = count.getAndIncrement();
        matches.putIfAbsent(m.id(), new SimpleEntry<>(m, i));

        return m;
    }

    @Override
    public Match updateScore(UUID id, int homeScore, int awayScore) {
        try {
            SimpleEntry<Match, Integer> updated
                    = matches
                            .compute(
                                    id,
                                    (k, v) -> new SimpleEntry(v.getKey().withNewScores(homeScore, awayScore), v.getValue())
                            );
            return updated.getKey();
        } catch (NullPointerException e) {
            throw new IllegalArgumentException(String.format("id %s not found", id));
        }
    }

    @Override
    public void finishMatch(UUID id) {
        matches.remove(id);
    }

    @Override
    public List<Match> getSummary() {
        List<SimpleEntry<Match, Integer>> snapshot;

        // synchronized is for consistency, it might affect performance though
        synchronized (matches) {
            snapshot = new ArrayList<>(matches.values());
        }

        Collections.sort(snapshot, (e1, e2) -> {
            var m1 = e1.getKey();
            var m2 = e2.getKey();
            var scoreSum1 = m1.homeScore() + m1.awayScore();
            var scoreSum2 = m2.homeScore() + m2.awayScore();

            if (scoreSum1 > scoreSum2) {
                return -1;
            } else if (scoreSum1 < scoreSum2) {
                return 1;
            } else {
                var added1 = e1.getValue();
                var added2 = e2.getValue();

                return added1 > added2 ? -1 : 1;
            }
        });

        return snapshot.stream().map(e -> e.getKey()).collect(Collectors.toList());
    }
}

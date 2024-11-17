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
        matches.putIfAbsent(m.id(), new SimpleEntry<>(m, count.getAndIncrement()));

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

        Collections.sort(snapshot, (entry1, entry2) -> {
            var match1 = entry1.getKey();
            var match2 = entry2.getKey();
            var scoreSum1 = match1.homeScore() + match1.awayScore();
            var scoreSum2 = match2.homeScore() + match2.awayScore();

            if (scoreSum1 > scoreSum2) {
                return -1;
            } else if (scoreSum1 < scoreSum2) {
                return 1;
            } else {
                return entry1.getValue() > entry2.getValue() ? -1 : 1;
            }
        });

        return snapshot.stream().map(e -> e.getKey()).collect(Collectors.toList());
    }
}

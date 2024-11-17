package scoreboard;

import java.util.List;
import java.util.UUID;

class SimpleScoreBoard implements ScoreBoard {

    @Override
    public Match startNewMatch(String homeTeam, String awayTeam) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Match updateScore(UUID id, int homeScore, int awayScore) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void finishMatch(UUID id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Match> getSummary() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

package scoreboard;

import java.util.List;
import java.util.UUID;

/**
 * The representation of a Live Football World Cup Scoreboard.
 */
public interface ScoreBoard {

    /**
     * Start a new match, assuming initial score 0 – 0 and adding it the
     * scoreboard.
     *
     * @param homeTeam the name of the home team
     * @param awayTeam the name of the away team
     *
     * @return the new match
     * @throws IllegalArgumentException if Match validation fails, or one of the
     * teams is in an already existing match
     */
    Match startNewMatch(String homeTeam, String awayTeam);

    /**
     * Update the score for a match.
     *
     * @param id the id of the match to update
     * @param homeScore the new score of the home team
     * @param awayScore the new score of the away team
     *
     * @return the updated match
     * @throws IllegalArgumentException if Match validation fails or id is not
     * found or null
     */
    Match updateScore(UUID id, int homeScore, int awayScore);

    /**
     * Finish match currently in progress. This removes a match from the
     * scoreboard.
     *
     * @param id the id of the match to finish
     * @throws IllegalArgumentException if id is null
     */
    void finishMatch(UUID id);

    /**
     * Get a summary of matches in progress ordered by their total score. The
     * matches with the same total score will be returned ordered by the most
     * recently started match in the scoreboard.
     *
     * @return a sorted list of matches in the scoreboard
     */
    List<Match> getSummary();
}

package scoreboard;

import java.util.UUID;

/**
 * Represents an ongoing match in a tournament. Includes the name and score for
 * the home and away team, respectively. Has an id.
 *
 * @param id the id of the match
 * @param homeTeam the name of the home team
 * @param homeScore the score of the home team
 * @param awayTeam the name of the away team
 * @param awayScore the score of the away team
 *
 */
public record Match(UUID id, String homeTeam, int homeScore, String awayTeam, int awayScore) {

    /**
     * Constructor for convenience. Initializes id with random UUID, scores with
     * 0.
     *
     * @param homeTeam the name of the home team
     * @param awayTeam the name of the away team
     *
     * @throws IllegalArgumentException if either of homeTeam or awayTeam is
     * null or an empty String or whitespace only
     */
    public Match(String homeTeam, String awayTeam) {
        this(null, homeTeam, 0, awayTeam, 0);
    }

    /**
     * Creates a new instance with the new scores.
     *
     * @param newHomeScore the new score of the home team
     * @param newAwayScore the nes score of the away team
     *
     * @return the new instance with id and names from the original one, but new
     * scores
     */
    public Match withNewScores(int newHomeScore, int newAwayScore) {
        return null;
    }
}

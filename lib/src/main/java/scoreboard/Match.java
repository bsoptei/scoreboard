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
        this(UUID.randomUUID(), homeTeam, 0, awayTeam, 0);
    }

    /**
     * Validation for input values.
     *
     * @throws IllegalArgumentException if any of id, homeTeam, or awayTeam is
     * null, or either of homeTeam or awayTeam is an empty String or whitespace
     * only
     */
    public Match     {
        String errorMessage = null;
        if (id == null) {
            errorMessage = "id should not be null";
        }
        if (homeTeam == null) {
            errorMessage = "homeTeam should not be null";
        }
        if (awayTeam == null) {
            errorMessage = "awayTeam should not be null";
        }
        if (homeTeam != null && homeTeam.trim().isEmpty()) {
            errorMessage = "homeTeam should not be empty";
        }
        if (awayTeam != null && awayTeam.trim().isEmpty()) {
            errorMessage = "awayTeam should not be empty";
        }
        if (homeTeam != null && homeTeam.equalsIgnoreCase(awayTeam)) {
            errorMessage = "homeTeam should not be the same as awayTeam";
        }

        if (errorMessage != null) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    /**
     * Creates a new instance with the new scores.
     *
     * @param newHomeScore the new score of the home team
     * @param newAwayScore the nes score of the away team
     *
     * @return the new instance with id and names from the original one, but new
     * scores
     *
     * @throws IllegalArgumentException if either of the new scores is negative
     */
    public Match withNewScores(int newHomeScore, int newAwayScore) {
        return new Match(this.id, this.homeTeam, newHomeScore, this.awayTeam, newAwayScore);
    }
}

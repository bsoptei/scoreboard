package scoreboard;

/**
 * Factory for ScoreBoard.
 */
public class ScoreBoardFactory {

    /**
     * The factory method.
     *
     * @param boardType the type of the scoreboard. Allowed types: SIMPLE (an
     * in-memory representation of the score board); SIMPLE_WORLD_CUP (builds on
     * SIMPLE, the difference is it only accepts valid national team names,
     * throws for other team names).
     *
     * @return an instance of the ScoreBoard
     * @throws IllegalArgumentException if an unknown boardType or null is used
     */
    public static ScoreBoard getInstance(String boardType) {
        return switch (boardType != null ? boardType.toUpperCase() : "null") {
            case "SIMPLE" ->
                new SimpleScoreBoard();
            case "SIMPLE_WORLD_CUP" ->
                new SimpleWorldCupScoreBoard();
            default ->
                throw new IllegalArgumentException(String.format("board type %s not allowed", boardType));
        };
    }
}

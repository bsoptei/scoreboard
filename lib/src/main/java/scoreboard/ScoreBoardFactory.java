package scoreboard;

/**
 * Factory for ScoreBoard.
 */
public class ScoreBoardFactory {

    /**
     * The factory method.
     *
     * @param boardType the type of the scoreboard, allowed types: SIMPLE
     *
     * @return an instance of the ScoreBoard
     * @throws IllegalArgumentException if an unknown boardType or null is used
     */
    public static ScoreBoard getInstance(String boardType) {
        if (boardType != null && boardType.equalsIgnoreCase("SIMPLE")) {
            return new SimpleScoreBoard();
        }
        if (boardType != null && boardType.equalsIgnoreCase("SIMPLE_WORLD_CUP")) {
            return new SimpleWorldCupScoreBoard();
        }

        throw new IllegalArgumentException(String.format("board type %s not allowed", boardType));
    }
}

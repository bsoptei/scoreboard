package scoreboard;

import java.util.List;

/**
 * Utilities for human readable output.
 */
public class Helpers {

    /**
     * Formats a single match.
     *
     * @param match the Match to format
     *
     * @return a formatted String of the match
     */
    public static String matchPrettyString(Match match) {
        return String.format(
                "%s %s - %s %s",
                match.homeTeam(),
                match.homeScore(),
                match.awayTeam(),
                match.awayScore()
        );
    }

    /**
     * Formats a list of matches.
     *
     * @param matches a list of Matches to format
     *
     * @return a formatted String of the matches
     */
    public static String summaryPrettyString(List<Match> matches) {
        var sb = new StringBuilder();

        var it = matches.listIterator();

        while (it.hasNext()) {
            sb.append(String.format("%s. %s\n", it.nextIndex() + 1, matchPrettyString(it.next())));
        }

        return sb.toString();
    }
}

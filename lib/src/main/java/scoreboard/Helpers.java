package scoreboard;

import java.util.List;

public class Helpers {

    public static String matchPrettyString(Match match) {
        return String.format(
                "%s %s - %s %s",
                match.homeTeam(),
                match.homeScore(),
                match.awayTeam(),
                match.awayScore()
        );
    }

    public static String summaryPrettyString(List<Match> matches) {
        var sb = new StringBuilder();

        var it = matches.listIterator();

        while (it.hasNext()) {
            sb.append(String.format("%s. %s\n", it.nextIndex() + 1, matchPrettyString(it.next())));
        }

        return sb.toString();
    }
}

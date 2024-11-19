package scoreboard;

import java.util.UUID;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ScoreBoardImplementationTest {

    @DataProvider(name = "implementations")
    public ScoreBoard[] createImpl() {
        return new ScoreBoard[]{new SimpleScoreBoard(), new SimpleWorldCupScoreBoard()};
    }

    @Test(description = "adding a new match should increase the number of matches, new match score should be 0-0", dataProvider = "implementations")
    public void startNewMatchTest(ScoreBoard sb) {
        Assert.assertEquals(sb.getSummary().size(), 0);

        sb.startNewMatch("Argentina", "Uruguay");

        var matches = sb.getSummary();
        Assert.assertEquals(matches.size(), 1);

        var match = matches.get(0);
        Assert.assertEquals(match.homeScore(), 0);
        Assert.assertEquals(match.awayScore(), 0);
    }

    @Test(description = "trying to add a team that already plays should fail", expectedExceptions = IllegalArgumentException.class, dataProvider = "implementations")
    public void AddingAlreadyExistingTeamTest(ScoreBoard sb) {
        sb.startNewMatch("Argentina", "Uruguay");
        sb.startNewMatch("Vanuatu", "Argentina");
    }

    @Test(description = "updated score values should be reflected in the matches", dataProvider = "implementations")
    public void updateScoreTest(ScoreBoard sb) {
        var id = sb.startNewMatch("Argentina", "Uruguay").id();
        int newHomeScore = 1;
        int newAwayScore = 2;

        sb.updateScore(id, newHomeScore, newAwayScore);

        var match = sb.getSummary().get(0);
        Assert.assertEquals(match.homeScore(), newHomeScore);
        Assert.assertEquals(match.awayScore(), newAwayScore);
    }

    @Test(description = "negative homeScore should not be allowed", expectedExceptions = IllegalArgumentException.class, dataProvider = "implementations")
    public void updateWithNegativeHomeScoreTest(ScoreBoard sb) {
        var id = sb.startNewMatch("Argentina", "Uruguay").id();

        sb.updateScore(id, -1, 2);
    }

    @Test(description = "negative awayScore should not be allowed", expectedExceptions = IllegalArgumentException.class, dataProvider = "implementations")
    public void updateWithNegativeAwayScoreTest(ScoreBoard sb) {
        var id = sb.startNewMatch("Argentina", "Uruguay").id();

        sb.updateScore(id, 1, -2);
    }

    @Test(description = "finishing a match should remove the corresponding match from the board", dataProvider = "implementations")
    public void finishMatchTest(ScoreBoard sb) {
        var id1 = sb.startNewMatch("Argentina", "Uruguay").id();
        var id2 = sb.startNewMatch("Vanuatu", "Uganda").id();
        sb.finishMatch(id1);

        var summary = sb.getSummary();

        Assert.assertEquals(summary.size(), 1);
        Assert.assertEquals(summary.get(0).id(), id2);
    }

    @Test(dataProvider = "implementations")
    public void finishNonExistingMatchTest(ScoreBoard sb) {
        sb.startNewMatch("Argentina", "Uruguay").id();
        sb.finishMatch(UUID.randomUUID());
    }

    @Test(description = "should not start match with same team name", expectedExceptions = IllegalArgumentException.class, dataProvider = "implementations")
    public void dontStartMatchesWithSameTeams(ScoreBoard sb) {
        sb.startNewMatch("Argentina", "Argentina");
    }

    @Test(description = "summary should contain no elements before adding matches", dataProvider = "implementations")
    public void summaryIsEmptyByDefault(ScoreBoard sb) {
        Assert.assertTrue(sb.getSummary().isEmpty());
    }

    @Test(description = "can't update a match that does not exist", expectedExceptions = IllegalArgumentException.class, dataProvider = "implementations")
    public void cantUpateNonExistingIdTest(ScoreBoard sb) {
        sb.updateScore(UUID.randomUUID(), 1, -2);
    }

    @Test(description = "matches should be ordered by total score, matches with the same total score will be returned ordered by the most recently started match", dataProvider = "implementations")
    public void getSummaryTest(ScoreBoard sb) {
        var id1 = sb.startNewMatch("Mexico", "Canada").id();
        var id2 = sb.startNewMatch("Spain", "Brazil").id();
        var id3 = sb.startNewMatch("Germany", "France").id();
        var id4 = sb.startNewMatch("Uruguay", "Italy").id();
        var id5 = sb.startNewMatch("Argentina", "Australia").id();

        sb.updateScore(id1, 0, 5);
        sb.updateScore(id2, 10, 2);
        sb.updateScore(id3, 2, 2);
        sb.updateScore(id4, 6, 6);
        sb.updateScore(id5, 3, 1);

        var summary = sb.getSummary();

        Assert.assertEquals(summary.get(0).id(), id4);
        Assert.assertEquals(summary.get(1).id(), id2);
        Assert.assertEquals(summary.get(2).id(), id1);
        Assert.assertEquals(summary.get(3).id(), id5);
        Assert.assertEquals(summary.get(4).id(), id3);
    }

    @Test(description = "world cup should not have a home team that's not a country", expectedExceptions = IllegalArgumentException.class)
    public void homeTeamIsNotCountryTest() {
        var sb = new SimpleWorldCupScoreBoard();
        sb.startNewMatch("foo", "Uruguay");
    }

    @Test(description = "world cup should not have an away team that's not a country", expectedExceptions = IllegalArgumentException.class)
    public void awayTeamIsNotCountryTest() {
        var sb = new SimpleWorldCupScoreBoard();
        sb.startNewMatch("Argentina", "bar");
    }
}

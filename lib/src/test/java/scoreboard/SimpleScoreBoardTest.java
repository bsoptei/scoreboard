package scoreboard;

import java.util.UUID;

import org.testng.Assert;
import org.testng.annotations.Test;

public class SimpleScoreBoardTest {

    @Test(description = "adding a new match should increase the number of matches, new match score should be 0-0")
    public void startNewMatchTest() {
        var ssb = new SimpleScoreBoard();

        Assert.assertEquals(ssb.getSummary().size(), 0);

        ssb.startNewMatch("foo", "bar");

        var matches = ssb.getSummary();
        Assert.assertEquals(matches.size(), 1);

        var match = matches.get(0);
        Assert.assertEquals(match.homeScore(), 0);
        Assert.assertEquals(match.awayScore(), 0);
    }

    @Test(description = "trying to add a team that already plays should fail", expectedExceptions = IllegalArgumentException.class)
    public void AddingAlreadyExistingTeamTest() {
        var ssb = new SimpleScoreBoard();
        ssb.startNewMatch("foo", "bar");
        ssb.startNewMatch("baz", "foo");
    }

    @Test(description = "updated score values should be reflected in the matches")
    public void updateScoreTest() {
        var ssb = new SimpleScoreBoard();
        var id = ssb.startNewMatch("foo", "bar").id();
        int newHomeScore = 1;
        int newAwayScore = 2;

        ssb.updateScore(id, newHomeScore, newAwayScore);

        var match = ssb.getSummary().get(0);
        Assert.assertEquals(match.homeScore(), newHomeScore);
        Assert.assertEquals(match.awayScore(), newAwayScore);
    }

    @Test(description = "negative homeScore should not be allowed", expectedExceptions = IllegalArgumentException.class)
    public void updateWithNegativeHomeScoreTest() {
        var ssb = new SimpleScoreBoard();
        var id = ssb.startNewMatch("foo", "bar").id();

        ssb.updateScore(id, -1, 2);
    }

    @Test(description = "negative awayScore should not be allowed", expectedExceptions = IllegalArgumentException.class)
    public void updateWithNegativeAwayScoreTest() {
        var ssb = new SimpleScoreBoard();
        var id = ssb.startNewMatch("foo", "bar").id();

        ssb.updateScore(id, 1, -2);
    }

    @Test(description = "finishing a match should remove the corresponding match from the board")
    public void finishMatchTest() {
        var ssb = new SimpleScoreBoard();
        var id1 = ssb.startNewMatch("foo", "bar").id();
        var id2 = ssb.startNewMatch("baz", "garply").id();
        ssb.finishMatch(id1);

        var summary = ssb.getSummary();

        Assert.assertEquals(summary.size(), 1);
        Assert.assertEquals(summary.get(0).id(), id2);
    }

    @Test
    public void finishNonExistingMatchTest() {
        var ssb = new SimpleScoreBoard();
        ssb.startNewMatch("foo", "bar").id();
        ssb.finishMatch(UUID.randomUUID());
    }

    @Test(description = "should not start match with same team name", expectedExceptions = IllegalArgumentException.class)
    public void dontStartMatchesWithSameTeams() {
        var ssb = new SimpleScoreBoard();
        ssb.startNewMatch("foo", "foo");
    }

    @Test(description = "summary should contain no elements before adding matches")
    public void summaryIsEmptyByDefault() {
        var ssb = new SimpleScoreBoard();
        Assert.assertTrue(ssb.getSummary().isEmpty());
    }

    @Test(description = "can't update a match that does not exist", expectedExceptions = IllegalArgumentException.class)
    public void cantUpateNonExistingIdTest() {
        var ssb = new SimpleScoreBoard();
        ssb.updateScore(UUID.randomUUID(), 1, -2);
    }

    @Test(description = "matches should be ordered by total score, matches with the same total score will be returned ordered by the most recently started match")
    public void getSummaryTest() {
        var ssb = new SimpleScoreBoard();

        var id1 = ssb.startNewMatch("Mexico", "Canada").id();
        var id2 = ssb.startNewMatch("Spain", "Brazil").id();
        var id3 = ssb.startNewMatch("Germany", "France").id();
        var id4 = ssb.startNewMatch("Uruguay", "Italy").id();
        var id5 = ssb.startNewMatch("Argentina", "Australia").id();

        ssb.updateScore(id1, 0, 5);
        ssb.updateScore(id2, 10, 2);
        ssb.updateScore(id3, 2, 2);
        ssb.updateScore(id4, 6, 6);
        ssb.updateScore(id5, 3, 1);

        var summary = ssb.getSummary();

        Assert.assertEquals(summary.get(0).id(), id4);
        Assert.assertEquals(summary.get(1).id(), id2);
        Assert.assertEquals(summary.get(2).id(), id1);
        Assert.assertEquals(summary.get(3).id(), id5);
        Assert.assertEquals(summary.get(4).id(), id3);
    }
}

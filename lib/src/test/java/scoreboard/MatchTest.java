package scoreboard;

import org.testng.Assert;
import org.testng.annotations.Test;

public class MatchTest {

    @Test(description = "Should not accept homeTeam as empty string", expectedExceptions = IllegalArgumentException.class)
    public void homeTeamNotEmptyTest() {
        var m = new Match("", "Argentina");
    }

    @Test(description = "Should not accept homeTeam as whitespace", expectedExceptions = IllegalArgumentException.class)
    public void homeTeamNotWhitespaceTest() {
        var m = new Match("    ", "Argentina");
    }

    @Test(description = "Should not accept awayTeam as empty string", expectedExceptions = IllegalArgumentException.class)
    public void awayTeamNotEmptyTest() {
        var m = new Match("Uruguay", "");
    }

    @Test(description = "Should not accept equal homeTeam and awayTeam", expectedExceptions = IllegalArgumentException.class)
    public void homeTeamShouldNotEqualAwayTeamTest() {
        var m = new Match("Uruguay", "Uruguay");
    }

    @Test(description = "Should not accept awayTeam as whitespace", expectedExceptions = IllegalArgumentException.class)
    public void awayTeamNotWhitespaceTest() {
        var m = new Match("Uruguay", "    ");
    }

    @Test(description = "Should not accept homeTeam as null", expectedExceptions = IllegalArgumentException.class)
    public void homeTeamNotNullTest() {
        var m = new Match(null, "Argentina");
    }

    @Test(description = "Should not accept awayTeam as null", expectedExceptions = IllegalArgumentException.class)
    public void awayTeamNotNullTest() {
        var m = new Match("Uruguay", null);
    }

    @Test(description = "Should not accept id as null", expectedExceptions = IllegalArgumentException.class)
    public void idNotNullTest() {
        var m = new Match(null, "Uruguay", 0, "Argentina", 0);
    }

    @Test(description = "2-parameter constructor should initialize instance with random uuid, and 0 scores")
    public void simpleConstructorTest() {
        var m = new Match("Uruguay", "Argentina");
        Assert.assertTrue(m.id() != null);
        Assert.assertEquals(m.homeScore(), 0);
        Assert.assertEquals(m.awayScore(), 0);
    }

    @Test(description = "Should return a new instance with updated scores")
    public void withNewScoresTest() {
        var m = new Match("Uruguay", "Argentina");
        int newHomeScore = 1;
        int newAwayScore = 2;

        var mWithNewScores = m.withNewScores(newHomeScore, newAwayScore);

        Assert.assertEquals(mWithNewScores.homeScore(), newHomeScore);
        Assert.assertEquals(mWithNewScores.awayScore(), newAwayScore);

        Assert.assertEquals(mWithNewScores.id(), m.id());
        Assert.assertEquals(mWithNewScores.homeTeam(), m.homeTeam());
        Assert.assertEquals(mWithNewScores.awayTeam(), m.awayTeam());
    }
}

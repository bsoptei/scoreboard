package scoreboard;

import java.util.List;
import java.util.UUID;

import org.testng.Assert;
import org.testng.annotations.Test;

public class HelpersTest {

    @Test
    public void matchPrettyStringTest() {
        var m = new Match(UUID.randomUUID(), "Spain", 10, "Brazil", 2);

        Assert.assertEquals(Helpers.matchPrettyString(m), "Spain 10 - Brazil 2");
    }

    @Test
    public void summaryPrettyStringTest() {
        var matches = List.of(
                new Match(UUID.randomUUID(), "Uruguay", 6, "Italy", 6),
                new Match(UUID.randomUUID(), "Spain", 10, "Brazil", 2),
                new Match(UUID.randomUUID(), "Mexico", 0, "Canada", 5),
                new Match(UUID.randomUUID(), "Argentina", 3, "Australia", 1),
                new Match(UUID.randomUUID(), "Germany", 2, "France", 2)
        );

        Assert.assertEquals(Helpers.summaryPrettyString(matches), "1. Uruguay 6 - Italy 6\n2. Spain 10 - Brazil 2\n3. Mexico 0 - Canada 5\n4. Argentina 3 - Australia 1\n5. Germany 2 - France 2\n");
    }
}

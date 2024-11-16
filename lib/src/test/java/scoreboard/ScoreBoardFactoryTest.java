package scoreboard;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ScoreBoardFactoryTest {

    @Test(description = "factory should create simple scoreboard when boardType SIMPLE is used")
    public void createSimpleInstanceTest() {
        Assert.assertTrue(ScoreBoardFactory.getInstance("SIMPLE") != null);
    }

    @Test(
            description = "factory should throw exception for gibberish boardType",
            expectedExceptions = IllegalArgumentException.class
    )
    public void failForArbitraryTypeTest() {
        var m = ScoreBoardFactory.getInstance("FoObAr");
    }

    @Test(
            description = "factory should throw exception for null boardType",
            expectedExceptions = IllegalArgumentException.class
    )
    public void failForNullTypeTest() {
        var m = ScoreBoardFactory.getInstance(null);
    }
}

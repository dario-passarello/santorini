package model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GameTest {

    private Game testGame;
    Player alice;
    Player alice2;
    Player bob;
    Player charlie;
    Player dana;

    @Before
    public void prepareTest() {
        testGame = new Game();
        testGame.setMaxPlayers(3);
    }

    @Test
    public void playersShouldBeAddedAndRemovedNoDuplicates() {
        //START
        Assert.assertEquals(testGame.playerCount(),0);
        Assert.assertTrue(testGame.createPlayer("Alice"));
        Assert.assertEquals(testGame.playerCount(),1);
        Assert.assertFalse(testGame.createPlayer("Alice"));
        Assert.assertEquals(testGame.playerCount(),1);
        Assert.assertTrue(testGame.createPlayer("Bob"));
        Assert.assertEquals(testGame.playerCount(),2);
        Assert.assertFalse(testGame.createPlayer("Bob"));
        Assert.assertEquals(testGame.playerCount(),2);
        Assert.assertFalse(testGame.removePlayer("Maxwell"));
        Assert.assertEquals(testGame.playerCount(),2);
        Assert.assertTrue(testGame.createPlayer("Dana"));
        Assert.assertEquals(testGame.playerCount(),3);
        Assert.assertFalse(testGame.createPlayer("Charlie"));
        Assert.assertEquals(testGame.playerCount(),3);
        Assert.assertTrue(testGame.removePlayer("Alice"));
        Assert.assertEquals(testGame.playerCount(),2);
        Assert.assertFalse(testGame.removePlayer("Alice"));
        Assert.assertEquals(testGame.playerCount(),2);
        Assert.assertFalse(testGame.removePlayer("Bob"));
        Assert.assertEquals(testGame.playerCount(),1);
        Assert.assertTrue(testGame.removePlayer("Dana"));
        Assert.assertEquals(testGame.playerCount(),0);
        Assert.assertFalse(testGame.removePlayer("Maxwell"));
    }

    @Test
    public void gameShouldStartOnlyWhenReady() {
        Assert.assertFalse(testGame.readyToStart());
        testGame.createPlayer("Alice");
        Assert.assertFalse(testGame.readyToStart());
        testGame.createPlayer("Dana");
        Assert.assertFalse(testGame.readyToStart());
        testGame.createPlayer("Charlie");
        Assert.assertTrue(testGame.readyToStart());
    }
}

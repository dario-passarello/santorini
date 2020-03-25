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
        testGame = new Game(3);
        alice = new Player("Alice");
        alice2 = new Player("Alice");
        bob = new Player("Bob");
        charlie = new Player("Charlie");
        dana = new Player("Dana");
    }
    @Test
    public void playersShouldBeAddedAndRemovedNoDuplicates() {
        //START
        Assert.assertEquals(testGame.playerCount(),0);
        Assert.assertTrue(testGame.addPlayer(alice));
        Assert.assertEquals(testGame.playerCount(),1);
        Assert.assertFalse(testGame.addPlayer(alice));
        Assert.assertEquals(testGame.playerCount(),1);
        Assert.assertFalse(testGame.addPlayer(alice2));
        Assert.assertEquals(testGame.playerCount(),1);
        Assert.assertTrue(testGame.addPlayer(bob));
        Assert.assertEquals(testGame.playerCount(),2);
        Assert.assertFalse(testGame.addPlayer(bob));
        Assert.assertEquals(testGame.playerCount(),2);
        Assert.assertFalse(testGame.removePlayer("Maxwell"));
        Assert.assertEquals(testGame.playerCount(),2);
        Assert.assertTrue(testGame.addPlayer(dana));
        Assert.assertEquals(testGame.playerCount(),3);
        Assert.assertFalse(testGame.addPlayer(charlie));
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
        testGame.addPlayer(alice);
        Assert.assertFalse(testGame.readyToStart());
        alice.toggleReady();
        Assert.assertFalse(testGame.readyToStart());
        testGame.addPlayer(dana);
        dana.toggleReady();
        Assert.assertFalse(testGame.readyToStart());
        testGame.addPlayer(charlie);
        Assert.assertFalse(testGame.readyToStart());
        charlie.toggleReady();
        Assert.assertTrue(testGame.readyToStart()); //all players ready
        alice.toggleReady();
        Assert.assertFalse(testGame.readyToStart());
        alice.toggleReady();
        testGame.removePlayer("Alice");
        Assert.assertFalse(testGame.readyToStart());
    }
}

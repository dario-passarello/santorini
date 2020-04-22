package model.buildbehaviors;

import model.*;
import model.buildbehaviours.BeneathBuild;
import model.buildbehaviours.StandardBuild;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class BeneathBuildTest {

    private static Game game;

    static {
        try {
            game = new Game(Arrays.asList("Tester1, Tester2"), 2);
        } catch (DuplicateNameException e) {
            e.printStackTrace();
        }
    }
    private static Board board = new Board();
    private static Builder test1;
    private static Builder test2;
    private static Builder test3;
    private static Square build1;
    private static Square build2;
    private static Square build3;
    private static Player player = new Player(game, "Tester");
    private static BeneathBuild behavior = new BeneathBuild(new StandardBuild());


    @BeforeAll
    public static void init(){
        setting();
    }

    @Test
    public void NeighborhoodTest(){


        Set<Square> Expected1 = new HashSet<>(Arrays.asList(
                board.squareAt(3, 0),
                board.squareAt(4, 0),        // Expected Neighborhood corner
                board.squareAt(4, 1)));

        Set<Square> Expected2 = new HashSet<>(Arrays.asList(
                board.squareAt(4, 1),       // Expected Neighborhood edge
                board.squareAt(3, 2),
                board.squareAt(3, 3),
                board.squareAt(4, 3)));

        Set<Square> Expected3 = new HashSet<>(Arrays.asList(
                board.squareAt(3, 0),       // Expected Neighborhood regular
                board.squareAt(2, 0),
                board.squareAt(1, 0),
                board.squareAt(1, 1),
                board.squareAt(1, 2),
                board.squareAt(2, 2),
                board.squareAt(2, 1),
                board.squareAt(3, 2)));

        Set<Square> Actual1 = behavior.neighborhood(test1.getPosition());
        Set<Square> Actual2 = behavior.neighborhood(test2.getPosition());
        Set<Square> Actual3 = behavior.neighborhood(test3.getPosition());

        Assert.assertEquals("1", Expected1, Actual1);
        Assert.assertEquals("2", Expected2, Actual2);
        Assert.assertEquals("3", Expected3, Actual3);


    }

    @Test
    public void BuildTest(){


        int Expected1 = build1.getBuildLevel();
        int Expected2 = build2.getBuildLevel() + 1;
        int Expected3 = build3.getBuildLevel() + 1;

        behavior.build(build1);                                  // Call Build Method
        behavior.build(build2);
        behavior.build(build3);

        int Actual1 = build1.getBuildLevel();
        int Actual2 = build2.getBuildLevel();
        int Actual3 = build3.getBuildLevel();

        Assert.assertEquals(Expected1, Actual1);
        Assert.assertEquals(Expected2, Actual2);
        Assert.assertEquals(Expected3, Actual3);

        Assert.assertTrue(build1.isDomed());
        Assert.assertFalse(build2.isDomed());
        Assert.assertFalse(build3.isDomed());




    }


    private static void setting(){

        test1 = new Builder(board.squareAt(4, 0), player,0);      // Corner
        test2 = new Builder(board.squareAt(4, 2), player,0);      // Edge
        test3 = new Builder(board.squareAt(2, 1), player,0);      // Regular

        board.squareAt(3, 1).addDome();                             // Adding a dome to check it will not be present

        build1 = board.squareAt(4, 1);                   // Level 3 Square:
        build2 = board.squareAt(3, 0);                   // Level 2 Square:
        build3 = board.squareAt(2, 2);                   // Level 1 Square:

        for(int i = 0; i < 3; i++) build1.build();                       // Create Buildings in the board
        for(int i = 0; i < 2; i++) build2.build();
        for(int i = 0; i < 1; i++) build3.build();
        for(int i = 0; i < 3; i++) test2.getPosition().build();

    }
}

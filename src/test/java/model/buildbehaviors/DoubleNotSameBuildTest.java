package model.buildbehaviors;

import model.*;
import model.buildbehaviours.DoubleNotSameBuild;
import model.buildbehaviours.StandardBuild;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class DoubleNotSameBuildTest {

    private static Game game;

    static {
        try {
            game = new Game(Arrays.asList("Tester1", "Tester2"), 2);
        } catch (DuplicateNameException e) {
            e.printStackTrace();
        }
    }
    private static Board board = new Board();
    private static Builder test1;
    private static Builder test2;
    private static Builder test3;
    private static Builder test4;
    private static Square build1;
    private static Square build2;
    private static Square build3;
    private static Player player = new Player(game, "Tester");
    private static DoubleNotSameBuild behavior1 = new DoubleNotSameBuild(new StandardBuild());
    private static DoubleNotSameBuild behavior2 = new DoubleNotSameBuild(new StandardBuild());


    @BeforeAll
    public static void init(){
        setting();
    }







    @Order(1)
    @Test
    public void FirstNeighborhoodTest(){
        Set<Square> Expected1 = new HashSet<>(Arrays.asList(
                board.squareAt(3, 0),       // Expected Neighborhood corner
                board.squareAt(4, 1)));

        Set<Square> Expected2 = new HashSet<>(Arrays.asList(
                board.squareAt(4, 1),       // Expected Neighborhood edge
                board.squareAt(3, 3),
                board.squareAt(4, 3)));

        Set<Square> Expected3 = new HashSet<>(Arrays.asList(
                board.squareAt(3, 0),       // Expected Neighborhood regular
                board.squareAt(2, 0),
                board.squareAt(1, 0),
                board.squareAt(1, 1),
                board.squareAt(1, 2),
                board.squareAt(2, 2)));

        Set<Square> Actual1 = behavior1.neighborhood(test1.getPosition());
        Set<Square> Actual2 = behavior1.neighborhood(test2.getPosition());
        Set<Square> Actual3 = behavior1.neighborhood(test3.getPosition());

        Assertions.assertAll("The First neighborhood should return the correct neighbors",
                () -> Assert.assertEquals(Expected1, Actual1),
                () -> Assert.assertEquals(Expected2, Actual2),
                () -> Assert.assertEquals(Expected3, Actual3)
        );
    }


    @Order(2)
    @Test
    public void FirstBuildTest(){

        // 4 Cases
        // Double Build in a Lv 1 Square and a Lv 2 Square
        // Second Double Build in the previous Lv 1 Square and a Lv 3 Square

        int Expected1 = build1.getBuildLevel();                     // Lv 3 Square, Expect a Dome
        int Expected2 = build3.getBuildLevel() + 1;                 // Lv 1 Square, Expect a lv 2


        behavior1.build(build1);                                  // Call Build on a Lv3 square
        behavior2.build(build3);                                  // Call Build on a Lv2 square

        int Actual1 = build1.getBuildLevel();
        int Actual2 = build3.getBuildLevel();

        Assert.assertEquals(Expected1, Actual1);
        Assert.assertEquals(Expected2, Actual2);

        Assert.assertTrue(build1.isDomed());
        Assert.assertFalse(build3.isDomed());
    }

    @Order(3)
    @Test
    public void SecondNeighborhoodTest(){

        Set<Square> Expected1 = new HashSet<>(Arrays.asList(
                board.squareAt(2, 2),
                board.squareAt(2, 3),
                board.squareAt(3, 3),
                board.squareAt(4, 3)));

        Set<Square> Expected2 = new HashSet<>(Arrays.asList(
                board.squareAt(3, 0),
                board.squareAt(2, 0),
                board.squareAt(1, 0),
                board.squareAt(1, 1),
                board.squareAt(1, 2)));

        Set<Square> Actual1 = behavior1.neighborhood(test4.getPosition());
        Set<Square> Actual2 = behavior2.neighborhood(test3.getPosition());

        Assert.assertEquals("1", Expected1, Actual1);
        Assert.assertEquals("2", Expected2, Actual2);

        Assert.assertFalse(Actual1.contains(build1));               // They should not contain the previous Squares
        Assert.assertFalse(Actual2.contains(build3));
    }

    @Order(4)
    @Test
    public void SecondBuildTest(){

        int Expected1 = build2.getBuildLevel() + 1;
        int Expected2 = build3.getBuildLevel() + 1;


        behavior1.build(build3);
        behavior2.build(build2);

        int Actual1 = build2.getBuildLevel();
        int Actual2 = build3.getBuildLevel();

        Assert.assertEquals(Expected1, Actual1);
        Assert.assertEquals(Expected2, Actual2);

        Assert.assertFalse(build2.isDomed());
        Assert.assertFalse(build3.isDomed());

    }

    private static void setting(){

        test1 = new Builder(board.squareAt(4, 0), player,0);
        test2 = new Builder(board.squareAt(4, 2), player,0);
        test3 = new Builder(board.squareAt(2, 1), player,0);
        test4 = new Builder(board.squareAt(3, 2), player,0);

        board.squareAt(3, 1).addDome();

        build1 = board.squareAt(4, 1);                   // Level 3 Square:
        build2 = board.squareAt(3, 0);                   // Level 2 Square:
        build3 = board.squareAt(2, 2);                   // Level 1 Square:

        for(int i = 0; i < 3; i++) build1.build();                 // Create Buildings in the board
        for(int i = 0; i < 2; i++) build2.build();
        for(int i = 0; i < 1; i++) build3.build();

    }
}

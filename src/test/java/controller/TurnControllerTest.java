package controller;

import model.Game;
import model.Turn;
import model.gods.GodFactory;
import model.turnstates.TurnState;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import utils.Coordinate;
import view.RemoteView;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class TurnControllerTest {


    private Game game = new Game(Arrays.asList("Tester1", "Tester2"), 2);
    private Controller controller = new Controller(game);
    private RemoteView client1 = new RemoteView(null, controller,"Tester1");
    private RemoteView client2 = new RemoteView(null, controller,"Tester2");
    private TurnController turncontroller = controller.turn();


    public TurnControllerTest() {
    }

    @BeforeEach
    public void init(){
        //Associate a builder per Player
        game.setGameState(game.turnState, game.getFirstPlayer());
        game.getFirstPlayer().addBuilder(game.getBoard().squareAt(4,0));
        game.getLastPlayer().addBuilder(game.getBoard().squareAt(0,4));

        game.getFirstPlayer().setGod(new GodFactory().getGod("Mortal"));
        game.getFirstPlayer().getGod().setPlayer(game.getFirstPlayer());

        game.getLastPlayer().setGod(new GodFactory().getGod("Mortal"));
        game.getLastPlayer().getGod().setPlayer(game.getFirstPlayer());
        game.getLastPlayer().getGod().captureResetBehaviors();
    }



    @Nested
    class MoveState {

        @Test
        public void firstSelectionTest() {

            TurnState start = game.getCurrentTurn().moveState;

            List<Turn.State> end = nextStatesmove(
                    Turn.State.SPECIAL_MOVE,
                    Turn.State.BUILD,
                    Turn.State.MOVE,
                    Turn.State.MOVE,
                    Turn.State.MOVE,
                    Turn.State.MOVE);

            TurnControllerTest.this.firstSelectionTest(start, end);


        }

        @Test
        public void SelectCoordinateTest() {

            TurnState start = game.getCurrentTurn().moveState;

            List<Turn.State> end = nextStatesbuild(
                    Turn.State.MOVE,
                    Turn.State.MOVE,
                    Turn.State.MOVE,
                    Turn.State.MOVE,
                    Turn.State.MOVE,
                    Turn.State.MOVE);

            TurnControllerTest.this.selectCoordinateTest(start, end);
        }

        @Test
        public void EndPhaseTest(){
          TurnState start = game.getCurrentTurn().moveState;
          TurnState end = game.getCurrentTurn().moveState;

          TurnControllerTest.this.endPhaseTest(start, end);
        }

    }


    @Nested
    class BuildState{

        @Test
        public void firstSelectionTest(){

            TurnState start = game.getCurrentTurn().buildState;

            List<Turn.State> end = nextStatesmove(
                    Turn.State.BUILD,
                    Turn.State.BUILD,
                    Turn.State.BUILD,
                    Turn.State.BUILD,
                    Turn.State.BUILD,
                    Turn.State.BUILD);

            TurnControllerTest.this.firstSelectionTest(start, end);
        }

        @Test
        public void selectCoordinateTest(){

            TurnState start = game.getCurrentTurn().buildState;

            List<Turn.State> end = nextStatesmove(
                    Turn.State.MOVE,
                    Turn.State.ADDITIONAL_BUILD,
                    Turn.State.MOVE,
                    Turn.State.BUILD,
                    Turn.State.BUILD,
                    Turn.State.BUILD);

            TurnControllerTest.this.selectCoordinateTest(start, end);

        }

        /*@Test
        public void endPhaseTest(){

            TurnState start1 = game.getCurrentTurn().additionalBuildState;
            TurnState start2 = game.getCurrentTurn().buildState;

            TurnState end1 = game.getCurrentTurn().endTurnState;
            TurnState end2 = game.getCurrentTurn().buildState;

            TurnControllerTest.this.endPhaseTest(start1, end1);
            TurnControllerTest.this.endPhaseTest(start2, end2);


        }*/
    }

    @Test
    public void checkTurnCorrectnessTest(){

        //Check when the method is called outside a turn
        game.setGameState(game.godSelectionState, game.getFirstPlayer());
        game.getCurrentTurn().setTurnState(game.getCurrentTurn().moveState);
        turncontroller.firstMove(client1, 0, null, false);
        Assert.assertEquals("The game state is not set to turn. The method should launch an exception\n",
                Turn.State.MOVE, game.getCurrentTurn().getStateID());

        //Check when the method is called by a player who is not in his turn
        game.setGameState(game.turnState, game.getFirstPlayer());
        turncontroller.firstMove(client2, 0, null, false);
        Assert.assertEquals("The player is calling the method not in his turn. The method should launch an exception\n",
                Turn.State.MOVE, game.getCurrentTurn().getStateID());


    }


    public void firstSelectionTest(TurnState start, List<Turn.State> ending ){



        //Create variables
        Coordinate correctCoordinate = new Coordinate(4, 1);
        Coordinate wrongCoordinate = new Coordinate(2, 2);
        Coordinate notEvenInTheBoard = new Coordinate(66, 67);



        /* Setting */ game.getCurrentTurn().setTurnState(start);

            //With Prometheus
            game.getFirstPlayer().setGod(new GodFactory().getGod("Prometheus"));
            game.getFirstPlayer().getGod().setPlayer(game.getFirstPlayer());
            turncontroller.firstMove(client1, 0, correctCoordinate, true);
            Assert.assertEquals(start.getStateID()+": Wrong action when StartGodPower is up\n"  ,
                    ending.get(0), game.getCurrentTurn().getStateID());



        /* Resetting */ game.getCurrentTurn().setTurnState(start);

            //Without Prometheus
            game.getFirstPlayer().setGod(new GodFactory().getGod("Mortal"));
            game.getFirstPlayer().getGod().setPlayer(game.getFirstPlayer());
            turncontroller.firstMove(client1, 0, correctCoordinate, false);
            Assert.assertEquals(start.getStateID()+": Wrong action when StartGodPower is not up\n",
                    ending.get(1), game.getCurrentTurn().getStateID());

        /* Resetting */ game.getCurrentTurn().setTurnState(start);

            //Trying Special Power Without Prometheus
            game.getFirstPlayer().setGod(new GodFactory().getGod("Mortal"));
            game.getFirstPlayer().getGod().setPlayer(game.getFirstPlayer());
            turncontroller.firstMove(client1, 0, correctCoordinate, true);
            Assert.assertEquals(start.getStateID()+": Wrong handle: SpecialPower method call without having it\n",
                    ending.get(2), game.getCurrentTurn().getStateID());

            //Calling method with a builder not owned by the current player
            turncontroller.firstMove(client1,1, correctCoordinate, false);
                    Assert.assertEquals(start.getStateID()+": Wrong handle: Builder not owned by the player\n",
                            ending.get(3), game.getCurrentTurn().getStateID());

            //Calling method with a coordinate not in the board
            turncontroller.firstMove(client1,0, notEvenInTheBoard, false);
            Assert.assertEquals(start.getStateID()+": Wrong handle: Coordinate not in the board\n",
                    ending.get(4), game.getCurrentTurn().getStateID());

             //Calling method with a coordinate that is not a neighborhood of the selected builder
            turncontroller.firstMove(client1,0, wrongCoordinate, false);
                Assert.assertEquals(start.getStateID() + ": Wrong handle: Builder can't move to the that Coordinate\n",
                        ending.get(5), game.getCurrentTurn().getStateID());
    }

    public void selectCoordinateTest(TurnState start, List<Turn.State> ending){

        //Create Variables
        Coordinate correctCoordinate = new Coordinate(4, 1);
        Coordinate correctCoordinate2 = new Coordinate(3,0);
        Coordinate wrongCoordinate = new Coordinate(2, 2);
        Coordinate notEvenInTheBoard = new Coordinate (66, 67);
        game.getCurrentTurn().setActiveBuilder(game.getFirstPlayer().getBuilders().get(0));

        /* Setting */ game.getCurrentTurn().setTurnState(start);

            //With Atlas
            game.getFirstPlayer().setGod(new GodFactory().getGod("Atlas"));
            game.getFirstPlayer().getGod().captureResetBehaviors();

            game.getFirstPlayer().getGod().setPlayer(game.getFirstPlayer());
            turncontroller.selectCoordinate(client1, correctCoordinate, true);
            Assert.assertEquals(start.getStateID()+": Wrong handle: AnyDome Build is not handled correctly\n",
                    ending.get(0), game.getCurrentTurn().getStateID());

        game.getCurrentTurn().endTurn();
        /* Resetting */ game.getCurrentTurn().setTurnState(start);


            //With DoubleBuild God
            game.getFirstPlayer().setGod(new GodFactory().getGod("Demeter"));
            game.getFirstPlayer().getGod().captureResetBehaviors();
            game.getFirstPlayer().getGod().setPlayer(game.getFirstPlayer());
            turncontroller.selectCoordinate(client1, correctCoordinate2, false);
            Assert.assertEquals(start.getStateID()+": Wrong handle: DoubleBuildGod is not handled correctly\n",
                    ending.get(1), game.getCurrentTurn().getStateID());

        game.getCurrentTurn().endTurn();
        game.getCurrentTurn().endTurn();
        /* Resetting */ game.getCurrentTurn().setTurnState(start);

            //With StandardBuild God
            game.getFirstPlayer().setGod(new GodFactory().getGod("Mortal"));
            game.getFirstPlayer().getGod().captureResetBehaviors();
            game.getFirstPlayer().getGod().setPlayer(game.getFirstPlayer());
            turncontroller.selectCoordinate(client1, correctCoordinate2, false);
            Assert.assertEquals(start.getStateID()+": Wrong handle: StandardBuild is not handled correctly\n",
                    ending.get(2), game.getCurrentTurn().getStateID());
        game.getCurrentTurn().endTurn();
        /* Resetting */ game.getCurrentTurn().setTurnState(start);

            //Calling the method with a Coordinate not in the Board
            turncontroller.selectCoordinate(client1, notEvenInTheBoard, false);
            Assert.assertEquals(start.getStateID()+": Wrong handle: Coordinate not in the board\n",
                    ending.get(3), game.getCurrentTurn().getStateID());

            //Calling the SpecialPower method without having Atlas
            turncontroller.selectCoordinate(client1, correctCoordinate, true);
            Assert.assertEquals(start.getStateID()+": Wrong handle: SpecialPower method call without having it\n",
                    ending.get(4), game.getCurrentTurn().getStateID());

            //Calling the method with a Coordinate that is not a neighborhood of the selected Builder
            try{turncontroller.selectCoordinate(client1, wrongCoordinate, false);}
            catch(IllegalArgumentException e){ }
            finally{
                Assert.assertEquals(start.getStateID()+": Wrong handle: Builder can't build on that coordinate\n",
                        ending.get(5), game.getCurrentTurn().getStateID());
            }



    }

    public void endPhaseTest(TurnState start, TurnState ending){

            /* Setting */ game.getCurrentTurn().setTurnState(start);
            turncontroller.endPhase(client1);
            Assert.assertEquals(ending.getStateID(), game.getCurrentTurn().getStateID());

            // Call when State is not optional
            /* Setting */ game.getCurrentTurn().setTurnState(game.getCurrentTurn().specialMoveState);
            turncontroller.endPhase(client1);
            Assert.assertEquals(game.getCurrentTurn().specialMoveState.getStateID(), game.getCurrentTurn().getStateID());

    }

    private List<Turn.State> nextStatesmove(Turn.State withPrometheus, Turn.State withoutPrometheus, Turn.State illegalSpecialPower,
                                            Turn.State illegalBuilderOwner, Turn.State illegalCoordinate, Turn.State illegalBuilderMove){

        return Arrays.asList(withPrometheus, withoutPrometheus, illegalSpecialPower, illegalBuilderOwner, illegalCoordinate, illegalBuilderMove);


    }

    private List<Turn.State> nextStatesbuild(Turn.State withAtlas, Turn.State withDoubleBuild, Turn.State normalBuild,
                                             Turn.State illegalCoordinate, Turn.State illegalSpecialPower, Turn.State illegalBuilderBuild){
        return Arrays.asList(withAtlas, withDoubleBuild, normalBuild, illegalCoordinate, illegalSpecialPower, illegalBuilderBuild);
    }

}




package controller;

import model.Builder;
import model.Game;
import model.Turn;
import model.gods.GodFactory;
import model.turnstates.TurnState;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import utils.Coordinate;
import utils.CoordinateMessage;
import java.util.Arrays;
import java.util.List;

public class TurnControllerTest {

//    private Game game = new Game(Arrays.asList("Tester1", "Tester2"), 2);
//    private RemoteView client1 = new RemoteView("Tester1");
//    private RemoteView client2 = new RemoteView("Tester2");
//    private Controller controller = new Controller(game, Arrays.asList(client1, client2));
//    private TurnController turncontroller = controller.turn();
//    private Builder builder1;
//    private Builder builder2;
//
//    @BeforeEach
//    public void init(){
//        //Associate a builder per Player
//        game.getFirstPlayer().addBuilder(game.getBoard().squareAt(4,0));
//        game.getLastPlayer().addBuilder(game.getBoard().squareAt(0,4));
//
//        builder1 = game.getFirstPlayer().getBuilders().get(0);
//        builder2 = game.getLastPlayer().getBuilders().get(0);
//    }
//
//
//
//    @Nested
//    class MoveState {
//
//        @Test
//        public void firstSelectionTest() {
//
//            TurnState start = game.getCurrentTurn().moveState;
//
//            List<Turn.State> end = nextStatesmove(
//                    Turn.State.ADDITIONAL_MOVE,
//                    Turn.State.BUILD,
//                    Turn.State.MOVE,
//                    Turn.State.MOVE,
//                    Turn.State.MOVE,
//                    Turn.State.MOVE);
//
//            TurnControllerTest.this.firstSelectionTest(start, end);
//
//
//        }
//
//        @Test
//        public void SelectCoordinateTest() {
//
//            TurnState start = game.getCurrentTurn().moveState;
//
//            List<Turn.State> end = nextStatesbuild(
//                    Turn.State.MOVE,
//                    Turn.State.MOVE,
//                    Turn.State.MOVE,
//                    Turn.State.MOVE,
//                    Turn.State.MOVE,
//                    Turn.State.MOVE);
//
//            TurnControllerTest.this.selectCoordinateTest(start, end);
//        }
//
//        @Test
//        public void EndPhaseTest(){
//            /* Setting */ game.getCurrentTurn().setTurnState(game.getCurrentTurn().moveState);
//
//
//            //Calling a method in a wrong State
//            turncontroller.endPhase(client1);
//            Assert.assertEquals("The method should not have been called",
//                    game.getCurrentTurn().getStateID(), Turn.State.MOVE);
//
//        }
//
//    }
//
//    @Nested
//    class BuildState{
//
//        @Test
//        public void firstSelectionTest(){
//
//            TurnState start = game.getCurrentTurn().buildState;
//
//            List<Turn.State> end = nextStatesmove(
//                    Turn.State.BUILD,
//                    Turn.State.BUILD,
//                    Turn.State.BUILD,
//                    Turn.State.BUILD,
//                    Turn.State.BUILD,
//                    Turn.State.BUILD);
//
//            TurnControllerTest.this.firstSelectionTest(start, end);
//        }
//
//        @Test
//        public void selectCoordinateTest(){
//
//            TurnState start = game.getCurrentTurn().buildState;
//
//            List<Turn.State> end = nextStatesmove(
//                    Turn.State.END_TURN,
//                    Turn.State.BUILD,
//                    Turn.State.END_TURN,
//                    Turn.State.BUILD,
//                    Turn.State.BUILD,
//                    Turn.State.BUILD);
//
//            TurnControllerTest.this.selectCoordinateTest(start, end);
//
//        }
//
//        @Test
//        public void endPhaseTest(){
//
//        }
//    }
//
//
//    public void firstSelectionTest(TurnState start, List<Turn.State> ending ){
//
//
//
//        //Create variables
//        Coordinate correctCoordinate = new Coordinate(4, 1);
//        Coordinate wrongCoordinate = new Coordinate(2, 2);
//        Coordinate notEvenInTheBoard = new Coordinate(66, 67);
//
//
//
//        /* Setting */ game.getCurrentTurn().setTurnState(start);
//
//            //With Prometheus
//            game.getFirstPlayer().setGod(new GodFactory().getGod("Prometheus"));
//            game.getFirstPlayer().getGod().setPlayer(game.getFirstPlayer());
//            turncontroller.firstMove(client1, 0, correctCoordinate, true));
//            Assert.assertEquals(start.getStateID()+": Wrong action when StartGodPower is up"  ,
//                game.getCurrentTurn().getStateID(), ending.get(0));
//
//        /* Resetting */ game.getCurrentTurn().setTurnState(start);
//
//            //Without Prometheus
//            game.getFirstPlayer().setGod(new GodFactory().getGod("Mortal"));
//            game.getFirstPlayer().getGod().setPlayer(game.getFirstPlayer());
//            turncontroller.firstMove(client1, 0, correctCoordinate, false));
//            Assert.assertEquals(start.getStateID()+": Wrong action when StartGodPower is not up",
//                game.getCurrentTurn().getStateID(), ending.get(1));
//
//        /* Resetting */ game.getCurrentTurn().setTurnState(start);
//
//            //Trying Special Power Without Prometheus
//            game.getFirstPlayer().setGod(new GodFactory().getGod("Mortal"));
//            game.getFirstPlayer().getGod().setPlayer(game.getFirstPlayer());
//            turncontroller.firstMove(client1, new CoordinateMessage(builder1, correctCoordinate, true));
//            Assert.assertEquals(start.getStateID()+": Wrong handle: SpecialPower method call without having it",
//                game.getCurrentTurn().getStateID(), ending.get(2));
//
//            //Calling method with a builder not owned by the current player
//            turncontroller.firstMove(client1, new CoordinateMessage(builder2, correctCoordinate, false));
//            Assert.assertEquals(start.getStateID()+": Wrong handle: Builder not owned by the Player",
//                game.getCurrentTurn().getStateID(), ending.get(3));
//
//            //Calling method with a coordinate not in the board
//            turncontroller.firstMove(client1, new CoordinateMessage(builder1, notEvenInTheBoard, false));
//            Assert.assertEquals(start.getStateID()+": Wrong handle: Coordinate not in the board",
//                game.getCurrentTurn().getStateID(), ending.get(4));
//
//             //Calling method with a coordinate that is not a neighborhood of the selected builder
//            turncontroller.firstMove(client1, new CoordinateMessage(builder1, wrongCoordinate, false));
//            Assert.assertEquals(start.getStateID()+": Wrong handle: Builder can't move to the that Coordinate",
//                game.getCurrentTurn().getStateID(), ending.get(5));
//    }
//
//    public void selectCoordinateTest(TurnState start, List<Turn.State> ending){
//
//        //Create Variables
//        Coordinate correctCoordinate = new Coordinate(4, 1);
//        Coordinate correctCoordinate2 = new Coordinate(3,0);
//        Coordinate wrongCoordinate = new Coordinate(2, 2);
//        Coordinate notEvenInTheBoard = new Coordinate (66, 67);
//        game.getCurrentTurn().setActiveBuilder(builder1);
//
//        /* Setting */ game.getCurrentTurn().setTurnState(start);
//
//            //With Atlas
//            game.getFirstPlayer().setGod(new GodFactory().getGod("Atlas"));
//            game.getFirstPlayer().getGod().setPlayer(game.getFirstPlayer());
//            turncontroller.selectCoordinate(client1, new CoordinateMessage(correctCoordinate, true));
//            Assert.assertEquals(game.getCurrentTurn().getStateID(), ending.get(0));
//
//        /* Resetting */ game.getCurrentTurn().setTurnState(start);
//
//            //With DoubleBuild God
//            game.getFirstPlayer().setGod(new GodFactory().getGod("Demeter"));
//            game.getFirstPlayer().getGod().setPlayer(game.getFirstPlayer());
//            turncontroller.selectCoordinate(client1, new CoordinateMessage(correctCoordinate, false));
//            Assert.assertEquals(start.getStateID()+": Wrong handle: DoubleBuildGod is not handled correctly",
//                game.getCurrentTurn().getStateID(), ending.get(1));
//
//        /* Resetting */ game.getCurrentTurn().setTurnState(start);
//
//            //With StandardBuild God
//            game.getFirstPlayer().setGod(new GodFactory().getGod("Mortal"));
//            game.getFirstPlayer().getGod().setPlayer(game.getFirstPlayer());
//            turncontroller.selectCoordinate(client1, new CoordinateMessage(correctCoordinate2, false));
//            Assert.assertEquals(start.getStateID()+": Wrong handle: StandardBuild is not handled correctly",
//                game.getCurrentTurn().getStateID(), ending.get(2));
//
//        /* Resetting */ game.getCurrentTurn().setTurnState(start);
//
//            //Calling the method with a Coordinate not in the Board
//            turncontroller.selectCoordinate(client1, new CoordinateMessage(notEvenInTheBoard, false));
//            Assert.assertEquals(start.getStateID()+": Wrong handle: Coordinate not in the board",
//                game.getCurrentTurn().getStateID(), ending.get(3));
//
//            //Calling the SpecialPower method without having Atlas
//            turncontroller.selectCoordinate(client1, new CoordinateMessage(correctCoordinate, true));
//            Assert.assertEquals(start.getStateID()+": Wrong handle: SpecialPower method call without having it",
//                game.getCurrentTurn().getStateID(), ending.get(4));
//
//            //Calling the method with a Coordinate that is not a neighborhood of the selected Builder
//            turncontroller.selectCoordinate(client1, new CoordinateMessage(wrongCoordinate, false));
//            Assert.assertEquals(start.getStateID()+": Wrong handle: Builder can't build on that coordinate",
//                    game.getCurrentTurn().getStateID(), ending.get(5));
//
//
//
//
//    }
//
//    private List<Turn.State> nextStatesmove(Turn.State withPrometheus, Turn.State withoutPrometheus, Turn.State illegalSpecialPower,
//                                            Turn.State illegalBuilderOwner, Turn.State illegalCoordinate, Turn.State illegalBuilderMove){
//
//        return Arrays.asList(withPrometheus, withoutPrometheus, illegalSpecialPower, illegalBuilderOwner, illegalCoordinate, illegalBuilderMove);
//
//
//    }
//
//    private List<Turn.State> nextStatesbuild(Turn.State withAtlas, Turn.State withDoubleBuild, Turn.State normalBuild,
//                                             Turn.State illegalCoordinate, Turn.State illegalSpecialPower, Turn.State illegalBuilderBuild){
//        return Arrays.asList(withAtlas, withDoubleBuild, normalBuild, illegalCoordinate, illegalSpecialPower, illegalBuilderBuild);
//    }
//
//

}




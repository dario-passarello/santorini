package controller;

import model.Game;
import model.Player;
import model.gamestates.GameState;
import model.gods.God;
import model.gods.GodFactory;
import org.junit.Assert;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import utils.Coordinate;
import utils.CoordinateMessage;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class GameControllerTest {

//    private Game game = new Game(Arrays.asList("Tester1", "Tester2"), 2);
//    private RemoteView client1 = new RemoteView("Tester1");
//    private RemoteView client2 = new RemoteView("Tester2");
//    private Controller controller = new Controller(game, Arrays.asList(client1, client2));
//    private GameController gamecontroller = controller.game();
//
//
//    @Nested
//    class GodSelectionState{
//
//        @Test
//        public void submitGodListTest(){
//            GameState start = game.godSelectionState;
//
//            List<Game.State> end = Arrays.asList(
//                    Game.State.PLACE_BUILDER,
//                    Game.State.GOD_PICK,
//                    Game.State.GOD_SELECTION
//            );
//
//            GameControllerTest.this.submitGodListTest(start, end);
//        }
//
//        @Test
//        public void pickGodTest(){
//            GameState start = game.godSelectionState;
//
//            List<Game.State> end = Arrays.asList(
//                    Game.State.GOD_SELECTION,
//                    Game.State.GOD_SELECTION,
//                    Game.State.GOD_SELECTION
//            );
//
//            GameControllerTest.this.pickGodTest(start, end);
//        }
//
//        @Test
//        public void placeBuilderTest(){
//            GameState start = game.godSelectionState;
//
//            List<Game.State> end = Arrays.asList(
//                    Game.State.GOD_SELECTION,
//                    Game.State.GOD_SELECTION,
//                    Game.State.GOD_SELECTION,
//                    Game.State.GOD_SELECTION
//            );
//
//            GameControllerTest.this.placeBuilderTest(start, end);
//        }
//    }
//
//    @Nested
//    class GodPickState{
//
//        @Test
//        public void submitGodListTest(){
//            GameState start = game.godPickState;
//
//            List<Game.State> end = Arrays.asList(
//                    Game.State.GOD_PICK,
//                    Game.State.GOD_PICK,
//                    Game.State.GOD_PICK
//            );
//
//            GameControllerTest.this.submitGodListTest(start, end);
//        }
//
//        @Test
//        public void pickGodTest(){
//
//            GameState start = game.godPickState;
//
//            List<Game.State> end = Arrays.asList(
//                    Game.State.PLACE_BUILDER,
//                    Game.State.GOD_PICK,
//                    Game.State.GOD_PICK
//            );
//
//            GameControllerTest.this.pickGodTest(start, end);
//        }
//
//        @Test
//        public void placeBuilderTest(){
//
//            GameState start = game.godPickState;
//
//            List<Game.State> end = Arrays.asList(
//                    Game.State.GOD_PICK,
//                    Game.State.GOD_PICK,
//                    Game.State.GOD_PICK,
//                    Game.State.GOD_PICK
//            );
//
//            GameControllerTest.this.placeBuilderTest(start, end);
//        }
//
//    }
//
//    @Nested
//    class PlaceBuilderState{
//
//        @Test
//        public void submitGodListTest(){
//            GameState start = game.placeBuilderState;
//
//            List<Game.State> end = Arrays.asList(
//                    Game.State.PLACE_BUILDER,
//                    Game.State.PLACE_BUILDER,
//                    Game.State.PLACE_BUILDER
//            );
//
//            GameControllerTest.this.submitGodListTest(start, end);
//        }
//
//        @Test
//        public void pickGodTest(){
//            GameState start = game.placeBuilderState;
//
//            List<Game.State> end = Arrays.asList(
//                    Game.State.PLACE_BUILDER,
//                    Game.State.PLACE_BUILDER,
//                    Game.State.PLACE_BUILDER
//            );
//
//            GameControllerTest.this.pickGodTest(start, end);
//        }
//
//        @Test
//        public void placeBuilderTest(){
//            GameState start = game.placeBuilderState;
//
//            List<Game.State> end = Arrays.asList(
//                    Game.State.PLACE_BUILDER,
//                    Game.State.PLACE_BUILDER,
//                    Game.State.PLACE_BUILDER,
//                    Game.State.TURN
//            );
//
//            GameControllerTest.this.placeBuilderTest(start, end);
//        }
//
//    }
//
//    @Nested
//    class TurnState{
//
//        @Test
//        public void submitGodListTest(){
//            GameState start = game.turnState;
//
//            List<Game.State> end = Arrays.asList(
//                    Game.State.TURN,
//                    Game.State.TURN,
//                    Game.State.TURN
//            );
//
//            GameControllerTest.this.submitGodListTest(start, end);
//        }
//
//        @Test
//        public void pickGodTest(){
//            GameState start = game.turnState;
//
//            List<Game.State> end = Arrays.asList(
//                    Game.State.TURN,
//                    Game.State.TURN,
//                    Game.State.TURN
//            );
//
//            GameControllerTest.this.pickGodTest(start, end);
//        }
//
//        @Test
//        public void placeBuilderTest(){
//            GameState start = game.turnState;
//
//            List<Game.State> end = Arrays.asList(
//                    Game.State.TURN,
//                    Game.State.TURN,
//                    Game.State.TURN,
//                    Game.State.TURN
//            );
//
//            GameControllerTest.this.placeBuilderTest(start, end);
//        }
//
//    }
//
//    @Nested
//    class EndGameState{
//
//        @Test
//        public void submitGodListTest(){
//            GameState start = game.endGameState;
//
//            List<Game.State> end = Arrays.asList(
//                    Game.State.END_GAME,
//                    Game.State.END_GAME,
//                    Game.State.END_GAME
//            );
//
//            GameControllerTest.this.submitGodListTest(start, end);
//        }
//
//        @Test
//        public void pickGodTest(){
//            GameState start = game.endGameState;
//
//            List<Game.State> end = Arrays.asList(
//                    Game.State.END_GAME,
//                    Game.State.END_GAME,
//                    Game.State.END_GAME
//            );
//
//            GameControllerTest.this.pickGodTest(start, end);
//        }
//
//        @Test
//        public void placeBuilderTest(){
//            GameState start = game.endGameState;
//
//            List<Game.State> end = Arrays.asList(
//                    Game.State.END_GAME,
//                    Game.State.END_GAME,
//                    Game.State.END_GAME,
//                    Game.State.END_GAME
//            );
//
//            GameControllerTest.this.placeBuilderTest(start, end);
//        }
//
//    }
//
//    public void submitGodListTest(GameState start, List<Game.State> ending){
//
//        //Create variables
//        Set<String> empty = new HashSet<String>();
//        Set<String> correct = new HashSet<String>();    correct.add("Atlas");   correct.add("Demeter");
//        Set<String> wrong = new HashSet<String>();      wrong.add("Atlas");     wrong.add("Demeter");   wrong.add("Artemis");
//
//        /* Setting */ game.setGameState(start, game.getFirstPlayer());
//
//            //Check Mortal Creation
//            gamecontroller.submitGodList(client1, empty);
//            Assert.assertEquals(game.getStateIdentifier()+": Problem in Creating a Mortal game",
//                    game.getGameState().getStateIdentifier(), ending.get(0));
//
//        /* Resetting */ game.setGameState(start, game.getFirstPlayer());
//
//            //Check CorrectGodList Creation
//            gamecontroller.submitGodList(client1, correct);
//            Assert.assertEquals(game.getStateIdentifier()+": Problem in using the List of God to create a game",
//                    game.getGameState().getStateIdentifier(), ending.get(1));
//
//        /* Resetting */ game.setGameState(start, game.getFirstPlayer());
//
//            //Check Malformed GodList
//            gamecontroller.submitGodList(client1, wrong);
//            Assert.assertEquals(game.getStateIdentifier()+": Wrong Handle: The malformed GodList is not handled corectly",
//                    game.getGameState().getStateIdentifier(), ending.get(2));
//
//
//    }
//
//    public void pickGodTest(GameState start, List<Game.State> ending){
//
//        //Create Variables
//        Set<String> chosenlist = new HashSet<String>();    chosenlist.add("Atlas");   chosenlist.add("Demeter");
//        GodFactory factory = new GodFactory();
//        List<God> godlist = chosenlist.stream().map(factory::getGod).collect(Collectors.toList());
//        game.setGodList(godlist);
//
//        /* Setting */   game.setGameState(start, game.getFirstPlayer());
//                        for(Player player : game.getPlayers()) player.setGod(null);
//
//            //Check Regular Call
//            gamecontroller.pickGod(client2, "Atlas");
//            Assert.assertEquals(game.getStateIdentifier()+": Problem with the call of the method with correct inputs\n",
//                    game.getGameState().getStateIdentifier(), ending.get(0));
//
//        /* Resetting */ game.setGameState(start, game.getFirstPlayer());
//                        for(Player player : game.getPlayers()) player.setGod(null);
//
//            //Check IllegalName
//            gamecontroller.pickGod(client1, "Demeter");
//            Assert.assertEquals(game.getStateIdentifier()+": Wrong Handle: The illegal name is not handled correctly\n",
//                    game.getGameState().getStateIdentifier(), ending.get(1));
//
//        /* Resetting */ game.setGameState(start, game.getFirstPlayer());
//                        for(Player player : game.getPlayers()) player.setGod(null);
//
//            //Check IllegalGod
//            gamecontroller.pickGod(client2, "Athena");
//            Assert.assertEquals(game.getStateIdentifier()+": Wrong Handle: The illegal god is not handled correctly",
//                    game.getGameState().getStateIdentifier(), ending.get(2));
//    }
//
//    public void placeBuilderTest(GameState start, List<Game.State> ending){
//
//        //Create Variables
//        game.getFirstPlayer().addBuilder(game.getBoard().squareAt(3,0));
//        game.getLastPlayer().addBuilder(game.getBoard().squareAt(0,4));
//
//        Coordinate correctCoordinate = new Coordinate(4,0);
//        Coordinate correctCoordinate2 = new Coordinate(0,3);
//        Coordinate occupiedCoordinate = new Coordinate(3,0);
//        Coordinate notEvenInTheBoard = new Coordinate(66, 67);
//
//        game.getFirstPlayer().setGod(new GodFactory().getGod("Mortal"));
//        game.getFirstPlayer().getGod().setPlayer(game.getFirstPlayer());
//
//        game.getLastPlayer().setGod(new GodFactory().getGod("Mortal"));
//        game.getLastPlayer().getGod().setPlayer(game.getLastPlayer());
//
//        /* Setting */   game.setGameState(start, game.getFirstPlayer());
//
//
//            //Check calling the method with an illegal username
//            gamecontroller.placeBuilder(client1, new CoordinateMessage(correctCoordinate));
//            Assert.assertEquals(game.getStateIdentifier()+": Wrong Handle: The illegal usename is not handled correctly\n",
//                    game.getGameState().getStateIdentifier(), ending.get(0));
//
//        /* Setting */   game.setGameState(start, game.getFirstPlayer());
//
//            //Check calling the method with a coordinate that it's not on the board
//            gamecontroller.placeBuilder(client1, new CoordinateMessage(notEvenInTheBoard));
//            Assert.assertEquals(game.getStateIdentifier()+": Wrong Handle: The illegal coordinate is not handled correctly\n",
//                    game.getGameState().getStateIdentifier(), ending.get(1));
//
//        /* Setting */   game.setGameState(start, game.getFirstPlayer());
//
//            //Check calling the method on an occupied square
//            gamecontroller.placeBuilder(client1, new CoordinateMessage(occupiedCoordinate));
//            Assert.assertEquals(game.getStateIdentifier()+": Wrong Handle: Problem when calling the method on an occupied square\n",
//                    game.getGameState().getStateIdentifier(), ending.get(2));
//
//        /* Setting */   game.setGameState(start, game.getFirstPlayer());
//
//            //Check Regular Call
//            gamecontroller.placeBuilder(client1, new CoordinateMessage(correctCoordinate));
//            gamecontroller.placeBuilder(client2, new CoordinateMessage(correctCoordinate2));
//            Assert.assertEquals(game.getStateIdentifier()+": Calling a method with the correct parameter caused an error\n",
//                    game.getGameState().getStateIdentifier(), ending.get(3));
//    }

}

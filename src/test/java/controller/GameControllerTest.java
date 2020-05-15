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
import view.RemoteView;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class GameControllerTest {


    private Game game = new Game(Arrays.asList("Tester1", "Tester2"), 2);
    // private Socket socket1 = new Socket(InetAddress.getLoopbackAddress(), 12345);
    // private Socket socket2 = new Socket(InetAddress.getLoopbackAddress(), 12345);
    private Controller controller = new Controller(game);
    private RemoteView client1 = new RemoteView(null, controller,"Tester1");
    private RemoteView client2 = new RemoteView(null, controller,"Tester2");
    private GameController gamecontroller = controller.game();

    public GameControllerTest() throws IOException {
    }



    @Nested
    class GodSelectionState{

        @Test
        public void submitGodListTest(){
            GameState start = game.godSelectionState;

            List<Game.State> end = Arrays.asList(
                    Game.State.PLACE_BUILDER,
                    Game.State.GOD_PICK,
                    Game.State.GOD_SELECTION,
                    Game.State.GOD_SELECTION
            );

            GameControllerTest.this.submitGodListTest(start, end);
        }

        @Test
        public void pickGodTest(){
            GameState start = game.godSelectionState;

            List<Game.State> end = Arrays.asList(
                    Game.State.GOD_SELECTION,
                    Game.State.GOD_SELECTION,
                    Game.State.GOD_SELECTION
            );

            GameControllerTest.this.pickGodTest(start, end);
        }

        @Test
        public void placeBuilderTest(){
            GameState start = game.godSelectionState;

            List<Game.State> end = Arrays.asList(
                    Game.State.GOD_SELECTION,
                    Game.State.GOD_SELECTION,
                    Game.State.GOD_SELECTION,
                    Game.State.GOD_SELECTION
            );

            GameControllerTest.this.placeBuilderTest(start, end);
        }

        @Test
        public void quitGameTest(){
            GameState start = game.godSelectionState;

            List<Game.State> end = Arrays.asList(
                    Game.State.END_GAME,
                    Game.State.END_GAME
            );

            GameControllerTest.this.quitGameTest(start, end);
        }
    }

    @Nested
    class GodPickState{

        @Test
        public void submitGodListTest(){
            GameState start = game.godPickState;

            List<Game.State> end = Arrays.asList(
                    Game.State.GOD_PICK,
                    Game.State.GOD_PICK,
                    Game.State.GOD_PICK,
                    Game.State.GOD_PICK
            );

            GameControllerTest.this.submitGodListTest(start, end);
        }

        @Test
        public void pickGodTest(){

            GameState start = game.godPickState;

            List<Game.State> end = Arrays.asList(
                    Game.State.PLACE_BUILDER,
                    Game.State.GOD_PICK,
                    Game.State.GOD_PICK
            );

            GameControllerTest.this.pickGodTest(start, end);
        }

        @Test
        public void placeBuilderTest(){

            GameState start = game.godPickState;

            List<Game.State> end = Arrays.asList(
                    Game.State.GOD_PICK,
                    Game.State.GOD_PICK,
                    Game.State.GOD_PICK,
                    Game.State.GOD_PICK
            );

            GameControllerTest.this.placeBuilderTest(start, end);
        }

        @Test
        public void quitGameTest(){
            GameState start = game.godPickState;

            List<Game.State> end = Arrays.asList(
                    Game.State.END_GAME,
                    Game.State.END_GAME
            );

            GameControllerTest.this.quitGameTest(start, end);
        }

    }

    @Nested
    class PlaceBuilderState{

        @Test
        public void submitGodListTest(){
            GameState start = game.placeBuilderState;

            List<Game.State> end = Arrays.asList(
                    Game.State.PLACE_BUILDER,
                    Game.State.PLACE_BUILDER,
                    Game.State.PLACE_BUILDER,
                    Game.State.PLACE_BUILDER
            );

            GameControllerTest.this.submitGodListTest(start, end);
        }

        @Test
        public void pickGodTest(){
            GameState start = game.placeBuilderState;

            List<Game.State> end = Arrays.asList(
                    Game.State.PLACE_BUILDER,
                    Game.State.PLACE_BUILDER,
                    Game.State.PLACE_BUILDER
            );

            GameControllerTest.this.pickGodTest(start, end);
        }

        @Test
        public void placeBuilderTest(){
            GameState start = game.placeBuilderState;

            List<Game.State> end = Arrays.asList(
                    Game.State.PLACE_BUILDER,
                    Game.State.PLACE_BUILDER,
                    Game.State.PLACE_BUILDER,
                    Game.State.TURN
            );

            GameControllerTest.this.placeBuilderTest(start, end);
        }

        @Test
        public void quitGameTest(){
            GameState start = game.placeBuilderState;

            List<Game.State> end = Arrays.asList(
                    Game.State.END_GAME,
                    Game.State.END_GAME
            );

            GameControllerTest.this.quitGameTest(start, end);
        }

    }

    @Nested
    class TurnState{

        @Test
        public void submitGodListTest(){
            GameState start = game.turnState;

            List<Game.State> end = Arrays.asList(
                    Game.State.TURN,
                    Game.State.TURN,
                    Game.State.TURN,
                    Game.State.TURN
            );

            GameControllerTest.this.submitGodListTest(start, end);
        }

        @Test
        public void pickGodTest(){
            GameState start = game.turnState;

            List<Game.State> end = Arrays.asList(
                    Game.State.TURN,
                    Game.State.TURN,
                    Game.State.TURN
            );

            GameControllerTest.this.pickGodTest(start, end);
        }

        @Test
        public void placeBuilderTest(){
            GameState start = game.turnState;

            List<Game.State> end = Arrays.asList(
                    Game.State.TURN,
                    Game.State.TURN,
                    Game.State.TURN,
                    Game.State.TURN
            );

            GameControllerTest.this.placeBuilderTest(start, end);
        }

        @Test
        public void quitGameTest(){
            GameState start = game.turnState;

            List<Game.State> end = Arrays.asList(
                    Game.State.END_GAME,
                    Game.State.TURN
            );

            GameControllerTest.this.quitGameTest(start, end);
        }

    }

    @Nested
    class EndGameState{

        @Test
        public void submitGodListTest(){
            GameState start = game.endGameState;

            List<Game.State> end = Arrays.asList(
                    Game.State.END_GAME,
                    Game.State.END_GAME,
                    Game.State.END_GAME,
                    Game.State.END_GAME
            );

            GameControllerTest.this.submitGodListTest(start, end);
        }

        @Test
        public void pickGodTest(){
            GameState start = game.endGameState;

            List<Game.State> end = Arrays.asList(
                    Game.State.END_GAME,
                    Game.State.END_GAME,
                    Game.State.END_GAME
            );

            GameControllerTest.this.pickGodTest(start, end);
        }

        @Test
        public void placeBuilderTest(){
            GameState start = game.endGameState;

            List<Game.State> end = Arrays.asList(
                    Game.State.END_GAME,
                    Game.State.END_GAME,
                    Game.State.END_GAME,
                    Game.State.END_GAME
            );

            GameControllerTest.this.placeBuilderTest(start, end);
        }

        @Test
        public void quitGameTest(){
            GameState start = game.endGameState;

            List<Game.State> end = Arrays.asList(
                    Game.State.END_GAME,
                    Game.State.END_GAME
            );

            GameControllerTest.this.quitGameTest(start, end);
        }

    }

    public void submitGodListTest(GameState start, List<Game.State> ending){

        //Create variables
        Set<String> empty = new HashSet<String>();
        Set<String> correct = new HashSet<String>();    correct.add("Atlas");   correct.add("Demeter");
        Set<String> wrong = new HashSet<String>();      wrong.add("Atlas");     wrong.add("Demeter");   wrong.add("Artemis");

        /* Setting */ game.setGameState(start, game.getFirstPlayer());


            //Check Mortal Creation
            gamecontroller.submitGodList(client1, empty);
            Assert.assertEquals(game.getStateIdentifier()+": Problem in Creating a Mortal game",
                    game.getGameState().getStateIdentifier(), ending.get(0));

        /* Resetting */ game.setGameState(start, game.getFirstPlayer());

            //Check CorrectGodList Creation
            gamecontroller.submitGodList(client1, correct);
            Assert.assertEquals(game.getStateIdentifier()+": Problem in using the List of God to create a game",
                    game.getGameState().getStateIdentifier(), ending.get(1));

        /* Resetting */ game.setGameState(start, game.getFirstPlayer());

            //Check Malformed GodList
            gamecontroller.submitGodList(client1, wrong);
            Assert.assertEquals(game.getStateIdentifier()+": Wrong Handle: The malformed GodList is not handled corectly",
                    game.getGameState().getStateIdentifier(), ending.get(2));

        /* Resetting */ game.setGameState(start, game.getFirstPlayer());

            //Check Wrong username caller
            gamecontroller.submitGodList(client2, correct);
            Assert.assertEquals(game.getGameState().getStateIdentifier(), ending.get(3));



    }

    public void pickGodTest(GameState start, List<Game.State> ending){

        //Create Variables
        Set<String> chosenlist = new HashSet<String>();    chosenlist.add("Atlas");   chosenlist.add("Demeter");
        GodFactory factory = new GodFactory();
        List<God> godlist = chosenlist.stream().map(factory::getGod).collect(Collectors.toList());
        game.setGodList(godlist);

        /* Setting */   game.setGameState(start, game.getFirstPlayer());
                        for(Player player : game.getPlayers()) player.setGod(null);

            //Check Regular Call
            gamecontroller.pickGod(client2, "Atlas");
            Assert.assertEquals(game.getStateIdentifier()+": Problem with the call of the method with correct inputs\n",
                    game.getGameState().getStateIdentifier(), ending.get(0));

        /* Resetting */ game.setGameState(start, game.getFirstPlayer());
                        for(Player player : game.getPlayers()) player.setGod(null);

            //Check IllegalName
            gamecontroller.pickGod(client1, "Demeter");
            Assert.assertEquals(game.getStateIdentifier()+": Wrong Handle: The illegal name is not handled correctly\n",
                    game.getGameState().getStateIdentifier(), ending.get(1));

        /* Resetting */ game.setGameState(start, game.getFirstPlayer());
                        for(Player player : game.getPlayers()) player.setGod(null);

            //Check IllegalGod
            gamecontroller.pickGod(client2, "Athena");
            Assert.assertEquals(game.getStateIdentifier()+": Wrong Handle: The illegal god is not handled correctly",
                    game.getGameState().getStateIdentifier(), ending.get(2));
    }

    public void placeBuilderTest(GameState start, List<Game.State> ending){

        //Create Variables
        game.getFirstPlayer().addBuilder(game.getBoard().squareAt(3,0));
        game.getLastPlayer().addBuilder(game.getBoard().squareAt(0,4));

        Coordinate correctCoordinate = new Coordinate(4,0);
        Coordinate correctCoordinate2 = new Coordinate(0,3);
        Coordinate occupiedCoordinate = new Coordinate(3,0);
        Coordinate notEvenInTheBoard = new Coordinate(66, 67);

        game.getFirstPlayer().setGod(new GodFactory().getGod("Mortal"));
        game.getFirstPlayer().getGod().setPlayer(game.getFirstPlayer());

        game.getLastPlayer().setGod(new GodFactory().getGod("Mortal"));
        game.getLastPlayer().getGod().setPlayer(game.getLastPlayer());

        /* Setting */   game.setGameState(start, game.getFirstPlayer());


            //Check calling the method with an illegal username
            gamecontroller.placeBuilder(client1, correctCoordinate);
            Assert.assertEquals(game.getStateIdentifier()+": Wrong Handle: The illegal usename is not handled correctly\n",
                    game.getGameState().getStateIdentifier(), ending.get(0));

        /* Resetting */   game.setGameState(start, game.getFirstPlayer());

            //Check calling the method with a coordinate that it's not on the board
            gamecontroller.placeBuilder(client1, notEvenInTheBoard);
            Assert.assertEquals(game.getStateIdentifier()+": Wrong Handle: The illegal coordinate is not handled correctly\n",
                    game.getGameState().getStateIdentifier(), ending.get(1));

        /* Resetting */   game.setGameState(start, game.getFirstPlayer());

            //Check calling the method on an occupied square
            gamecontroller.placeBuilder(client1, occupiedCoordinate);
            Assert.assertEquals(game.getStateIdentifier()+": Wrong Handle: Problem when calling the method on an occupied square\n",
                    game.getGameState().getStateIdentifier(), ending.get(2));

        /* Resetting */   game.setGameState(start, game.getFirstPlayer());

            //Check Regular Call
            gamecontroller.placeBuilder(client1, correctCoordinate);
            gamecontroller.placeBuilder(client2, correctCoordinate2);
            Assert.assertEquals(game.getStateIdentifier()+": Calling a method with the correct parameter caused an error\n",
                    game.getGameState().getStateIdentifier(), ending.get(3));
    }

    public void quitGameTest(GameState start, List<Game.State> ending){
        String falseplayer = "Fake_Tester";

        /* Setting */ game.setGameState(start, game.getFirstPlayer());

        //Check when the method is called with a name name that is not in the game
        gamecontroller.quitGame(new RemoteView(null, controller, falseplayer));
        Assert.assertEquals(game.getStateIdentifier()+": Wrong handle: the illegal username is not handled correctly\n",
                game.getGameState().getStateIdentifier(), ending.get(1));

        /* Resetting */ game.setGameState(start, game.getFirstPlayer());

            //Check when the method is called with a client that is in the game
            gamecontroller.quitGame(client1);
            Assert.assertEquals(game.getStateIdentifier()+": Calling the method with a client in game caused an error\n",
                    game.getGameState().getStateIdentifier(), ending.get(0));
            if(start == game.turnState) { // Resetting the game after checking the method was called correctly
                game = new Game(Arrays.asList("Tester1", "Tester2"), 2);
                game.setGameState(game.turnState, game.getFirstPlayer());
            }



    }


}

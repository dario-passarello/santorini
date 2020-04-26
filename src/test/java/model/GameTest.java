package model;

import model.gods.God;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import utils.Coordinate;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class GameTest {

    @ParameterizedTest
    @ValueSource(ints = {2,3})
    public void gameStateMachineShouldProceedCorrectly(int num){
        List<String> players = IntStream.range(1,num + 1).mapToObj(n -> "Player_" + n).collect(Collectors.toList());
        List<String> gods = List.of("Zeus","Apollo","Chronus").subList(0,num);
        Game mortalGame = new Game(players,num);
        Game godGame = new Game(players,num);
        assertEquals("Wrong number of players returned", num, mortalGame.getNumberOfPlayers());
        assertEquals("Player name lists don't match",players,mortalGame.getPlayers().stream().map(Player::getName).collect(Collectors.toList()));
        assertEquals("First player is not the first playing",players.get(0),mortalGame.getCurrentTurn().getCurrentPlayer().getName());

        //GodSelectionState checks
        assertSame("First state should be GodSelectionState",mortalGame.godSelectionState,mortalGame.getGameState());
        assertEquals(mortalGame.getStateIdentifier(), Game.State.GOD_SELECTION);
        assertFalse("Action pick god should not be allowed", mortalGame.pickGod("Player_1","Zeus"));
        assertFalse("Action select coordinate should not be allowed",mortalGame.selectCoordinate("Player_2", new Coordinate(2,3)));

        assertThrows(IllegalArgumentException.class, () -> godGame.submitGodList(Set.of("Zeus","Pulcinella"))); //Thr
        assertThrows(IllegalArgumentException.class, () -> mortalGame.submitGodList(Set.of("Zeus")));
        assertTrue("Action submit god list should be allowed",mortalGame.submitGodList(Set.of()));
        assertTrue("Action submit god list should be allowed",godGame.submitGodList(new HashSet<String>(gods)));
        assertSame("The state should be PlaceBuilders in mortal only games",mortalGame.placeBuilderState,mortalGame.getGameState());
        assertTrue("Action quit game should be allowed",mortalGame.quitGame());
        assertSame("State should be End Game", mortalGame.endGameState, mortalGame.getGameState());

        //GodPickState checks
        assertSame("The state should be GodPick in games with gods",godGame.godPickState,godGame.getGameState());
        assertEquals(godGame.getStateIdentifier(), Game.State.GOD_PICK);
        assertThrows(IllegalArgumentException.class, () -> godGame.pickGod("Player_0","Zeus"));
        assertFalse("Action submit gods should be not allowed", godGame.submitGodList(Set.of()));
        assertFalse("Action select coordinate should be not allowed", godGame.selectCoordinate("Player_0",new Coordinate(2,3)));
        for(int i = num - 1; i > 0; i--) {
            assertTrue(godGame.pickGod(players.get(i),gods.get(i)));
            if(i > 1) {
                final int ii = i; //The player should not pick twice
                assertThrows(IllegalArgumentException.class,() -> godGame.pickGod(players.get(ii), gods.get(ii)));
            }
        }

        //PlaceBuilderState checks
        assertSame("The state should be Place Builders",godGame.placeBuilderState, godGame.getGameState());
        assertEquals(godGame.getStateIdentifier(), Game.State.PLACE_BUILDER);
        assertFalse("Action submit gods should be not allowed", godGame.submitGodList(Set.of()));
        assertFalse("Action pick god should not be allowed",godGame.pickGod("Player_0","Zeus"));
        assertThrows(IllegalArgumentException.class, () -> godGame.selectCoordinate("Player_0",new Coordinate(6,7)));
        assertThrows(IllegalArgumentException.class, () -> godGame.selectCoordinate("Player_2",new Coordinate(0,0)));
        assertThrows(IllegalArgumentException.class, () -> godGame.selectCoordinate("Missingno", new Coordinate(1,1)));
        for(int i = 0; i < num; i++){
            final int fi = i;
            assertTrue("Action should be allowed",godGame.selectCoordinate(players.get(i),new Coordinate(i + 1,0)));
            //Could not place a builder in an already occupied space
            assertThrows(IllegalArgumentException.class, () ->godGame.selectCoordinate(players.get(fi), new Coordinate(fi + 1,0)));
            assertTrue("Action should be allowed",godGame.selectCoordinate(players.get(i),new Coordinate(0,i + 1)));
            //Place phase should have ended
            if(i < num-1) {
                assertThrows(IllegalArgumentException.class, () -> godGame.selectCoordinate(players.get(fi), new Coordinate(4, 4)));
            }
        }

        //TurnState checks
        assertSame("The state should be Turn",godGame.turnState, godGame.getGameState());
        assertEquals(godGame.getStateIdentifier(), Game.State.TURN);
        assertFalse("Action submit god should not be allowed",godGame.submitGodList(Set.of()));
        assertFalse("Action pick god should not be allowed", godGame.pickGod("Player_0","Zeus"));
        assertFalse("Action select coordinate should not be allowed", godGame.selectCoordinate("Player_0",new Coordinate(0,0)));
        assertTrue("Action quit game should be allowed",godGame.quitGame());
        assertSame("State should be End Game", godGame.endGameState, godGame.getGameState());

    }

    @ParameterizedTest
    @ValueSource(ints = {2,3})
    public void turnStateMachineShouldProceedCorrectly(int num){
        List<String> players = IntStream.range(1,num + 1).mapToObj(n -> "Player_" + n).collect(Collectors.toList());
        List<String> gods = List.of("Zeus","Apollo","Chronus").subList(0,num);
        List<Coordinate> builderPosition = List.of(new Coordinate(0,1), new Coordinate(1,4), new Coordinate(4,3), new Coordinate(0,3), new Coordinate(1,2), new Coordinate(3,2)).subList(0,num*2);
        Game game = setupGameAtTurnPhase(players, gods, builderPosition);
        Board board = game.getBoard();
        final Turn turn = game.getCurrentTurn();

        //MoveState checks
        assertSame("The turn state should be move",turn.moveState,turn.getTurnState());
        assertFalse("Action select coordinate should not be allowed",turn.selectCoordinate(new Coordinate(1,0)));
        assertFalse("Action end phase should not be allowed",turn.getTurnState().endPhase());
        assertThrows(IllegalArgumentException.class, () -> turn.firstSelection(board.squareAt(0,3).getOccupant().get(), new Coordinate(2,1), false));
        assertThrows(IllegalArgumentException.class, () -> turn.firstSelection(board.squareAt(0,1).getOccupant().get(), new Coordinate(5,2), false));
        assertThrows(IllegalArgumentException.class, () -> turn.firstSelection(board.squareAt(0,1).getOccupant().get(), new Coordinate(0,2), true));
        assertThrows(IllegalArgumentException.class, () -> turn.firstSelection(board.squareAt(0,1).getOccupant().get(), new Coordinate(4,4), false));
        assertTrue("Action first selection should be allowed",turn.firstSelection(board.squareAt(0,1).getOccupant().get(), new Coordinate(0,2), false));

        //BuildState checks
        assertSame("The turn state should be build",turn.buildState,turn.getTurnState());
        assertFalse("Action first selection should not be allowed",turn.firstSelection(board.squareAt(0,2).getOccupant().get(), new Coordinate(0,2), false));
        assertThrows(IllegalArgumentException.class, () -> turn.selectCoordinate(new Coordinate(0,5), false));
        assertThrows(IllegalArgumentException.class, () -> turn.selectCoordinate(new Coordinate(0,1), true));
        assertThrows(IllegalArgumentException.class, () -> turn.selectCoordinate(new Coordinate(4,4), false));
        assertTrue("Action select coordinate should be allowed",turn.selectCoordinate(new Coordinate(0,1), false));

        //EndTurnState checks
        assertSame("The turn state should be end turn", turn.endTurnState,turn.getTurnState());
        assertFalse("Action first selection should not be allowed",turn.firstSelection(board.squareAt(0,2).getOccupant().get(), new Coordinate(0,2), false));
        assertFalse("Action select coordinate should not be allowed",turn.selectCoordinate(new Coordinate(1,2)));
        assertTrue("Action end phase should be allowed",turn.endPhase());

        //turn rotation checks
        assertEquals("Player_2",game.getCurrentTurn().getCurrentPlayer().getName());
    }

    @ParameterizedTest
    @ValueSource(ints = {2,3})
    public void basicStuffShouldWork(int num){
        List<String> players = IntStream.range(1,num + 1).mapToObj(n -> "Player_" + n).collect(Collectors.toList());
        assertThrows(IllegalArgumentException.class, () -> new Game(players, 4));
        assertThrows(DuplicateNameException.class, () -> new Game(List.of("Player_1","Player_1","Player_2").subList(0,num), num));

        List<String> gods = List.of("Zeus","Apollo","Chronus").subList(0,num);
        List<Coordinate> builderPosition = List.of(new Coordinate(0,1), new Coordinate(1,4), new Coordinate(4,3), new Coordinate(0,3), new Coordinate(1,2), new Coordinate(3,2)).subList(0,num*2);
        Game game = setupGameAtTurnPhase(players, gods, builderPosition);
        Board board = game.getBoard();
        final Turn turn = game.getCurrentTurn();

        assertTrue(game.getWinner().isEmpty());
        assertEquals(game.getPlayers(), game.getPlayersInGame());
        for(int i = 0; i < num*2; i++){
            assertEquals(game.getAllBuilders().get(i).getPosition().getCoordinate(), builderPosition.get(i));
        }

        //remove a player
        assertThrows(NoSuchElementException.class, () -> game.removePlayer(new Player(game, "Fake_Player")));
        Player removedPlayer = game.getPlayers().get(0);
        game.removePlayer(removedPlayer);
        if(num == 2){
            assertSame(game.endGameState,game.getGameState());
            assertTrue(removedPlayer.isSpectator());
            assertEquals(game.getWinner().orElse(null), game.getPlayers().get(1));
            //endGame checks
            assertFalse(game.submitGodList(new HashSet<>(gods)));
            assertFalse(game.pickGod("Player_1", "Zeus"));
            assertFalse(game.selectCoordinate("Player_1", new Coordinate(3,4)));
            assertFalse(game.quitGame());
            assertEquals(game.getStateIdentifier(),Game.State.END_GAME);
        } else {
            assertThrows(IllegalArgumentException.class, () -> game.removePlayer(removedPlayer));
            assertSame(game.turnState,game.getGameState());

        }


    }



    public static Game setupGameAtTurnPhase(List<String> players, List<String> gods, List<Coordinate> builderPositions) throws DuplicateNameException {
        Game game = new Game(players, players.size());
        Collections.reverse(players);
        game.submitGodList(new HashSet<>(gods));
        //if(game.getGameState() == game.godPickState) {
        try {
            for (int i = 0; i < players.size() - 1; i++) {
                game.pickGod(players.get(i), gods.get(i));
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            fail("Malformed Test Input: Not enough gods provided");
        }
        Collections.reverse(players);
        //}
        try {
            for (int i = 0; i < players.size() * 2; i++) {
                game.selectCoordinate(players.get(i / 2), builderPositions.get(i));
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            fail("Malformed Test Input: Not enough builder positions provided");
        }
        assertEquals("Unexpected turn state",game.getGameState(), game.turnState);
        return game;
    }
}

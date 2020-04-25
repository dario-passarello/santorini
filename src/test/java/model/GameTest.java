package model;

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
        assertSame("First state should be GodSelectionState",mortalGame.godSelectionState,mortalGame.getGameState());
        assertFalse("Action pick god should not be allowed", mortalGame.pickGod("Player_1","Zeus"));
        assertFalse("Action select coordinate should not be allowed",mortalGame.selectCoordinate("Player_2", new Coordinate(2,3)));
        assertThrows(IllegalArgumentException.class, () -> godGame.submitGodList(Set.of("Zeus","Pulcinella"))); //Thr
        assertThrows(IllegalArgumentException.class, () -> mortalGame.submitGodList(Set.of("Zeus")));
        assertTrue("Action submit god list should be allowed",mortalGame.submitGodList(Set.of()));
        assertTrue("Action submit god list should be allowed",godGame.submitGodList(new HashSet<String>(gods)));
        assertSame("The state should be PlaceBuilders in mortal only games",mortalGame.placeBuilderState,mortalGame.getGameState());
        assertTrue("Action quit game should be allowed",mortalGame.quitGame());
        assertSame("State should be End Game", mortalGame.endGameState, mortalGame.getGameState());
        assertSame("The state should be GodPick in games with gods",godGame.godPickState,godGame.getGameState());
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
        assertSame("The state should be Place Builders",godGame.placeBuilderState, godGame.getGameState());
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
            assertThrows(IllegalArgumentException.class, () -> godGame.selectCoordinate(players.get(fi), new Coordinate(4,4)));
        }
        assertSame(godGame.turnState, godGame.getGameState());
        assertFalse("Action submit god should not be allowed",godGame.submitGodList(Set.of()));
        assertFalse("Action pick god should not be allowed", godGame.pickGod("Player_0","Zeus"));
        assertFalse("Action select coordinate should not be allowed", godGame.selectCoordinate("Player_0",new Coordinate(0,0)));
        assertTrue("Action quit game should be allowed",godGame.quitGame());
        assertSame("State should be End Game", godGame.endGameState, godGame.getGameState());
    }



    public static Game setupGameAtTurnPhase(List<String> players, List<String> gods, List<Coordinate> builderPositions) throws DuplicateNameException {
        Game game = new Game(players, players.size());
        Collections.reverse(gods);
        game.submitGodList(new HashSet<>(gods));
        if(game.getGameState() == game.godPickState) {
            try {
                for (int i = 0; i < players.size() - 1; i++) {
                    game.pickGod(players.get(i), gods.get(i));
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                fail("Malformed Test Input: Not enough gods provided");
            }
        }
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

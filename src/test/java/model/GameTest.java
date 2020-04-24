package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import utils.Coordinate;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class GameTest {
    Game testGame2, testGame3;

    @RepeatedTest(9999)
    public void gameConstructTest(int a, int b){

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

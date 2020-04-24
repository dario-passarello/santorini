package model;

import org.junit.jupiter.api.BeforeEach;
import utils.Coordinate;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


public class GameTest {
    private Game game;


    @BeforeEach
    public void setupGame(List<String> names) {
        //TODO
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
                game.selectCoordinate(players.get(i / 2), gods.get(i));
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            fail("Malformed Test Input: Not enough builder positions provided");
        }
        assertEquals("Unexpected turn state",game.getGameState(), game.turnState);
        return game;
    }
}

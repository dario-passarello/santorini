package model.gods;

import model.Board;
import model.Builder;
import model.Player;
import model.Square;
import model.buildbehaviours.BuildBehavior;
import model.movebehaviors.MoveBehavior;
import model.wincondition.WinCondition;

import java.io.Serializable;
import java.util.*;

/**
 * A generic God class. A god could have special powers that could change the behavior of all {@link Builder} controlled
 * by the player bound to the God instance. or change his win condition.
 * A god has his {@link MoveBehavior}, his {@link BuildBehavior}, his {@link WinCondition}. A god could also have
 * a special start power and a special build phase power
 */
public class God implements Serializable {

    protected transient Player player;
    protected final String name;
    protected transient WinCondition winCondition;
    protected transient MoveBehavior moveBehavior;
    protected transient MoveBehavior resetMoveBehavior;
    protected transient BuildBehavior buildBehavior;
    protected final boolean specialStartPower;
    protected final boolean specialBuildPower;

    /**
     * The Constructor of the class. It creates a God Object with specific characteristics
     * @param name The name of the God
     * @param winCondition The effect it has on the Winning Condition of the game
     * @param moveBehavior The MoveBehavior parameter. It could either be a Standard one or a Decorator one
     * @param buildBehavior The BuildBehavior parameter. It could either be a Standard one or a Decorator one
     * @param specialStartPower The parameter that specifies if it has a Starting Special Power
     * @param specialBuildPower The parameter that specifies if it has a Special Build Power
     */
    public God(String name, WinCondition winCondition, MoveBehavior moveBehavior, BuildBehavior buildBehavior, boolean specialStartPower, boolean specialBuildPower) {
        this.name = name;
        setWinCondition(winCondition);
        setMoveBehavior(moveBehavior);
        setBuildBehavior(buildBehavior);
        this.specialBuildPower = specialBuildPower;
        this.specialStartPower = specialStartPower;
    }

    /**
     * @param god is a God we want to copy
     */
    public God(God god) {
        this.name = god.name;
        this.specialBuildPower = god.specialBuildPower;
        this.specialStartPower = god.specialStartPower;
    }

    /**
     * Sets the player to bound to this god
     * NOTE: The players should also be bound to this god
     *
     * @param p A reference to the player to bound to this God
     */
    public void setPlayer(Player p) {
        player = p;
    }

    /**
     * Sets the {@link WinCondition} of the god
     *
     * @param winCondition A reference to the top level decorator of the WinCondition
     */
    public void setWinCondition(WinCondition winCondition) {
        this.winCondition = winCondition;
    }

    /**
     * Sets the {@link MoveBehavior} of the god
     *
     * @param moveBehavior A reference to the top level decorator of the MoveBehavior
     */
    public void setMoveBehavior(MoveBehavior moveBehavior) {
        this.moveBehavior = moveBehavior;
    }

    /**
     * Sets the {@link BuildBehavior} of the god
     *
     * @param buildBehavior A reference to the top level decorator of the BuildBehavior
     */
    public void setBuildBehavior(BuildBehavior buildBehavior) {
        this.buildBehavior = buildBehavior;
    }

    /**
     * Capture the current behavior structures and sets them as default ones.
     */
    public void captureResetBehaviors() {
        resetMoveBehavior = moveBehavior.copyBehavior();
    }

    /**
     * @return player attribute
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * @return name attribute
     */
    public String getName() {
        return name;
    }

    /**
     * @return winCondition attribute
     */
    public WinCondition getWinCondition() {
        return winCondition;
    }

    /**
     * @return moveBehavior attribute
     */
    public MoveBehavior getMoveBehavior() {
        return moveBehavior;
    }

    /**
     * @return buildBehavior attribute
     */
    public BuildBehavior getBuildBehavior() {
        return buildBehavior;
    }

    /**
     * @return true if this God has a special start power
     */
    public boolean hasSpecialStartPower() {
        return specialStartPower;
    }

    /**
     * @return true if this God has a special build power
     */
    public boolean hasSpecialBuildPower() {
        return specialBuildPower;
    }


    /**
     * @param location the position of the builder that wants to move
     * @return a list of squares where the builder can move
     */
    public List<Square> getWalkableNeighborhood(Square location) {
        return new ArrayList<>(moveBehavior.neighborhood(location));
    }

    /**
     * @param builder The reference to the builder executing the action
     * @param location the initial position of the builder that is going to move
     * @return true if the player could move again, false if his move phase terminates here
     */
    public boolean move(Builder builder, Square location) {
        return moveBehavior.move(builder, location);
    }

    /**
     * @param start The square from which the builder started to move
     * @param builder A reference to the builder who has moved
     * @return Optionally a reference to the player that won the game
     */
    public Optional<Player> checkWinCondition(Square start, Builder builder) {
        return winCondition.checkWinCondition(start, builder);
    }

    /**
     * @return Optionally a reference to the player that won the game
     */
    public Optional<Player> checkSpecialWinCondition() {
        Board board = this.getPlayer().getGame().getBoard();
        return winCondition.checkSpecialWinCondition(board);
    }

    /**
     * @param location the position of the builder that wants to build
     * @return a list of squares where the builder can build
     */
    public List<Square> getBuildableNeighborhood(Square location) {
        return new ArrayList<>(buildBehavior.neighborhood(location));
    }

    /**
     * @param builder The builder that is currently selected
     * @param location the position where the builder that is going to build
     * @return True if the player can build again
     */
    public boolean build(Builder builder, Square location) {
        return buildBehavior.build(location);
    }

    /**
     * @param targets a list of gods whose move behaviors have to be changed
     */
    public void setAllMoveBehaviors(List<God> targets) {
    }

    /**
     * @param targets a list of gods whose win conditions have to be changed
     */
    public void configureAllOtherWinConditions(List<God> targets) {
        //Standard method does nothing
    }

    /**
     * Reset the behavior structures to the default ones
     * Resets also the internal states of all the behaviors
     * This function should be called at the end of a turn
     */
    public void resetBehaviors() {
        this.moveBehavior = resetMoveBehavior.copyBehavior();
        moveBehavior.reset();
        buildBehavior.reset();
    }

    /**
     * @param o is an object
     * @return true if o is equal to this instance of God
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof God)) return false;
        God god = (God) o;
        if (player == null && god.player == null) {
            return name.equals(god.name);
        } else {
            return Objects.equals(player, god.player) &&
                    name.equals(god.name);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(player, name);
    }
}

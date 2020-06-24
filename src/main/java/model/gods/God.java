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

    public God(String name, WinCondition winCondition, MoveBehavior moveBehavior, BuildBehavior buildBehavior, boolean specialStartPower, boolean specialBuildPower) {
        this.name = name;
        setWinCondition(winCondition);
        setMoveBehavior(moveBehavior);
        setBuildBehavior(buildBehavior);
        this.specialBuildPower = specialBuildPower;
        this.specialStartPower = specialStartPower;
    }

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

    public Player getPlayer() {
        return player;
    }

    public String getName() {
        return name;
    }

    public WinCondition getWinCondition() {
        return winCondition;
    }

    public MoveBehavior getMoveBehavior() {
        return moveBehavior;
    }

    public BuildBehavior getBuildBehavior() {
        return buildBehavior;
    }

    public boolean hasSpecialStartPower() {
        return specialStartPower;
    }

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
     * @param location the initial position of the builder that is going to move
     * @return true if the player could move again, false if his move phase terminates here
     */
    public boolean move(Builder builder, Square location) {
        return moveBehavior.move(builder, location);
    }

    /**
     * @return Optionally a reference to the player that won the player
     */
    public Optional<Player> checkWinCondition(Square start, Builder builder) {
        return winCondition.checkWinCondition(start, builder);
    }

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
     * @param location the position of the builder that is going to build
     */
    public boolean build(Builder builder, Square location) {
        return buildBehavior.build(location);
    }

    public void setAllMoveBehaviors(List<God> targets) {

    }

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

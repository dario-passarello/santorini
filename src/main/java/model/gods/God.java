package model.gods;

import model.Builder;
import model.Player;
import model.Square;
import model.buildbehaviours.BuildBehavior;
import model.movebehaviors.MoveBehavior;
import model.movebehaviors.MoveDecorator;
import model.startbehaviors.StartBehavior;
import model.wincondition.WinCondition;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public abstract class God {

    protected Player player;
    protected String name;
    protected WinCondition winCondition;
    protected WinCondition resetWinCondition;
    protected StartBehavior startBehavior;
    protected MoveBehavior moveBehavior;
    protected MoveBehavior resetMoveBehavior;
    protected BuildBehavior buildBehavior;

    public God(String name, WinCondition winCondition, StartBehavior startBehavior, MoveBehavior moveBehavior, BuildBehavior buildBehavior){

        this.name = name;
        setWinCondition(winCondition);
        setStartBehavior(startBehavior);
        setMoveBehavior(moveBehavior);
        setBuildBehavior(buildBehavior);
    }


    public void setPlayer(Player p) {
        player = p;
    }
    public void setWinCondition(WinCondition winCondition){
        this.winCondition = winCondition;
    }
    public void setStartBehavior(StartBehavior startBehavior){
        this.startBehavior = startBehavior;
    }
    public void setMoveBehavior(MoveBehavior moveBehavior) {
        this.moveBehavior = moveBehavior;
    }
    public void setBuildBehavior(BuildBehavior buildBehavior) {
        this.buildBehavior = buildBehavior;
    }
    public void captureResetBehaviors() {
        resetMoveBehavior = moveBehavior;
        resetWinCondition = winCondition;
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
    public StartBehavior getStartBehavior() {
        return startBehavior;
    }
    public MoveBehavior getMoveBehavior() {
        return moveBehavior;
    }
    public BuildBehavior getBuildBehavior() {
        return buildBehavior;
    }
    public abstract boolean hasSpecialStartPower();
    public abstract boolean hasSpecialBuildPower();


    public void startTurn(){
        //TODO
    }

    /**
     * @param location the position of the builder that wants to move
     * @return a list of squares where the builder can move
     */
    public List<Square> getWalkableNeighborhood(Square location){
        //TODO
        return null;
    }
    /**
     * @param location the initial position of the builder that is going to move
     * @return true if the player could move again, false if his move phase terminates here
     */
    public boolean move(Builder builder, Square location){
        return moveBehavior.move(builder, location);
    }
    /**
     * @return Optionally a reference to the player that won the player
     */

    public Optional<Player> checkWinCondition(Square start, Builder builder){
        return winCondition.checkWinCondition(start, builder);
    }
    /**
     * @param location the position of the builder that wants to build
     * @return a list of squares where the builder can build
     */
    public List<Square> getBuildableNeighborhood(Square location){
        //TODO
        return null;
    }
    /**
     * @param location the position of the builder that is going to build
     */
    public boolean build(Builder builder, Square location){
        return buildBehavior.build(location);
    }



    public void setAllMoveBehaviors(List<God> targets){

    }

    public void configureAllOtherWinConditions(List<God> targets){
        //Standard method does nothing
    }


    public void resetBehaviors(){
        this.moveBehavior = resetMoveBehavior;
        this.winCondition = resetWinCondition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof God)) return false;
        God god = (God) o;
        if(player == null && god.player == null) {
            return name.equals(god.name);
        }
        else {
            return Objects.equals(player, god.player) &&
                    name.equals(god.name);
        }

    }

    @Override
    public int hashCode() {
        return Objects.hash(player, name);
    }
}

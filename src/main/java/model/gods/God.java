package model.gods;

import model.Builder;
import model.Player;
import model.Square;
import model.buildbehaviours.BuildBehavior;
import model.movebehaviors.MoveBehavior;
import model.startbehaviors.StartBehavior;
import model.wincondition.WinCondition;

import java.util.List;

public abstract class God {

    protected final Player player;
    protected String name;
    protected WinCondition winCondition;
    protected StartBehavior startBehavior;
    protected MoveBehavior moveBehavior;
    protected BuildBehavior buildBehavior;

    public God(Player player, String name, WinCondition winCondition, StartBehavior startBehavior, MoveBehavior moveBehavior, BuildBehavior buildBehavior){

        this.player = player;
        this.name = name;
        setWinCondition(winCondition);
        setStartBehavior(startBehavior);
        setMoveBehavior(moveBehavior);
        setBuildBehavior(buildBehavior);
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
     */
    public boolean move(Builder builder, Square location){
        return moveBehavior.move(builder, location);
    }

    /**
     * @return true if the player that use this god won
     */
    public  boolean checkWinCondition(Square start, Builder builder){
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

    public boolean endBuild(){
        //TODO
        return false;
    }

    public void resetBehaviors(){
        //TODO
    }

}

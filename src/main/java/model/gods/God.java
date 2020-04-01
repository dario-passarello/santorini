package model.gods;

import model.Player;
import model.Square;
import model.buildbehaviours.BuildBehavior;
import model.movebehaviors.MoveBehavior;
import model.startbehaviors.StartBehavior;
import model.wincondition.WinCondition;

import java.util.List;

public abstract class God {

    private String name;
    private WinCondition winCondition;
    private StartBehavior startBehavior;
    private MoveBehavior moveBehavior;
    private BuildBehavior buildBehavior;

    public God(String name, WinCondition winCondition, StartBehavior startBehavior, MoveBehavior moveBehavior, BuildBehavior buildBehavior){
        setName(name);
        setWinCondition(winCondition);
        setStartBehavior(startBehavior);
        setMoveBehavior(moveBehavior);
        setBuildBehavior(buildBehavior);
    }

    public void setName(String name){
        this.name = name;
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

    public  void setBuildBehavior(BuildBehavior buildBehavior) {
        this.buildBehavior = buildBehavior;
    }

    public Player getPlayer(){      //probably useless (?)
        //TODO
        return null;
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
    public void move(Square location){
        moveBehavior.move(location);
    }

    public boolean endMove(){
        //TODO
        return false;
    }

    /**
     * @return true if the player that use this god won
     */
    public  boolean checkWinCondition(){
        return winCondition.checkWinCondition();
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
    public void build(Square location){
        buildBehavior.build(location);
    }

    public boolean endBuild(){
        //TODO
        return false;
    }

    public void resetBehaviors(){
        //TODO
    }

}

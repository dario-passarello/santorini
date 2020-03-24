package model.gods;

import model.Player;
import model.Square;
import model.buildbehaviors.BuildBehavior;
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

    public List<Square> getWalkableNeighborhood(Square location){
        //TODO
        return null;
    }

    public void move(Square location){
        moveBehavior.move(location);
    }

    public boolean endMove(){
        //TODO
        return false;
    }

    public  boolean checkWinCondition(){
        return winCondition.checkWinCondition();
    }

    public List<Square> getBuildableNeighborhood(Square location){
        //TODO
        return null;
    }

    public void build(Square location){
        buildBehavior.build();
    }

    public boolean endBuild(){
        //TODO
        return false;
    }

    public void resetBehaviors(){
        //TODO
    }

}

package view.screens;

public abstract class ScreenBuilder {



    protected ScreenFactory screenFactory;

    public ScreenBuilder(ScreenFactory factory){
        this.screenFactory = factory;
    }


    public abstract Screen buildScreen();
}

package view.screens;

import view.ViewManager;

public abstract class MenuScreen extends Screen{
    public MenuScreen(ViewManager view) {
        super(view);
    }

    public final void quitGame(){

    }

    /**
     *  Logic button for opening the connection screen
     */
    public final void goToConnectionScreen(){
        view.changeActiveScreen(view.getScreenFactory().getConnectionScreen());
    }

}

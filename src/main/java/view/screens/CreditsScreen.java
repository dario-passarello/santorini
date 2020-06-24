package view.screens;

import view.ViewManager;

public class CreditsScreen extends Screen{

    public CreditsScreen(ViewManager view) {
        super(view);
    }

    public final void goToMenuScreen(){
        view.changeActiveScreen(view.getScreenFactory().getMenuScreen());
    }


    @Override
    public void onScreenOpen() {

    }

    @Override
    public void onScreenClose() {

    }
}

package view.CLI;

import view.ViewManager;
import view.screens.GodSelectionScreen;

public class CLIGodSelectionScreen extends GodSelectionScreen {

    public CLIGodSelectionScreen(ViewManager viewManager, String activePlayer){
        super(viewManager, activePlayer);
    }


    @Override
    public void onScreenOpen() {

        //TEMPORARY VISUAL

        System.out.println("This is the God Selection Screen:  ");
    }

    @Override
    public void onScreenClose() {

    }
}

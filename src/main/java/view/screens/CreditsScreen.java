package view.screens;

import view.ViewManager;

/**
 * This screen should display the credits of the game
 * From this screen it's possible to:
 * - Go back to the {@link MenuScreen}
 */
public class CreditsScreen extends Screen{

    /**
     * CreditsScreen constructor
     * @param view the viewmanager used
     */
    public CreditsScreen(ViewManager view) {
        super(view);
    }

    /**
     * it will change the active screen in {@link MenuScreen}
     */
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

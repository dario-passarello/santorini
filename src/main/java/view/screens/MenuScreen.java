package view.screens;

import view.ViewManager;

/**
 * This screen should display the menu of the game
 * From this screen it's possible to:
 * <p>- Starting a new game, going to the {@link ConnectionScreen}</p>
 * <p>- See the {@link CreditsScreen}</p>
 * <p>- Quit</p>
 */
public abstract class MenuScreen extends Screen{
    public MenuScreen(ViewManager view) {
        super(view);
    }

    public final void quitGame(){
        System.exit(0);
    }

    /**
     *  Logic button for opening the {@link ConnectionScreen}
     */
    public final void goToConnectionScreen(){
        view.changeActiveScreen(view.getScreenFactory().getConnectionScreen());
    }

    /**
     * Logic button for opening the {@link CreditsScreen}
     */
    public final void goToCreditsScreen(){
        view.changeActiveScreen(view.getScreenFactory().getCreditsScreen());
    }

}

package view.CLI;

import java.util.Scanner;

/**
 * This class represent the thread that listens to stdin and delivers the input to the current screen
 */
public class InputListener implements Runnable {


    private InputProcessor currentScreen;

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        while(true){
            if(scanner.hasNextLine()){
                String input = scanner.nextLine();
                currentScreen.processInput(input);

            }
        }
    }

    /**
     * This method sets the current screen
     * @param screen The screen parameter
     */
    public void setScreen(InputProcessor screen){
        this.currentScreen = screen;
    }

    /**
     *
     * @return
     */
    protected InputProcessor getScreen(){
        return this.currentScreen;
    }

    }

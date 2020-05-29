package view.CLI;

import java.util.Scanner;

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

    public void setScreen(InputProcessor screen){
        this.currentScreen = screen;
    }
}

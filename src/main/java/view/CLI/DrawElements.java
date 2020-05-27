package view.CLI;

import java.io.PrintWriter;

public class DrawElements {

    private static PrintWriter out = new PrintWriter(System.out, true);
    private static final String FLUSH = "\033[H\033[2J";
    private static final String ESC = (char) 27 + "[";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String RED = "\u001B[38;5;88m";
    public static final String REDBG = "\u001B[48;5;88m";
    public static final String GREEN1 = "\u001B[38;5;83m";
    public static final String GREEN2 = "\u001B[38;5;41m";
    public static final String GREENBG1 = "\u001B[48;5;83m";
    public static final String GREENBG2 = "\u001B[48;5;41m";
    public static final String GREYBG = "\u001B[48;5;250m";
    public static final String WHITE = "\u001B[38;5;230m";
    public static final String BLACK = "\u001B[38;5;244m";
    public static final String GREENBG3 = "\u001B[48;5;83m";
    public static final String BLUE1 = "\u001B[38;5;21m";
    public static final String WHITEBG = "\u001B[48;5;230m";

    public static void main(String[] args){

        out.println(FLUSH);


        DrawBoard(GREENBG3, BLACK);



        saveCursor();
        printBackground(2, 3, WHITE);
        restoreCursor();

        saveCursor();
        cellColors(GREEN1, GREEN2);
        restoreCursor();

        saveCursor();
        printBlock(2, 3, WHITE,true);
        restoreCursor();









    }


    private static void DrawBoard(String backgroundColor, String boardColor){

        out.print(ANSI_RESET);
        out.println("        a         b         c         d         e     ");
        out.println("   " + backgroundColor + boardColor + "╔═════════╦═════════╦═════════╦═════════╦═════════╗" + ANSI_RESET);
        out.println("   " + backgroundColor + boardColor + "║         ║         ║         ║         ║         ║" + ANSI_RESET);
        out.println(" 1 " + backgroundColor + boardColor + "║         ║         ║         ║         ║         ║" + ANSI_RESET);
        out.println("   " + backgroundColor + boardColor + "║         ║         ║         ║         ║         ║" + ANSI_RESET);
        out.println("   " + backgroundColor + boardColor + "╠═════════╬═════════╬═════════╬═════════╬═════════╣" + ANSI_RESET);
        out.println("   " + backgroundColor + boardColor + "║         ║         ║         ║         ║         ║" + ANSI_RESET);
        out.println(" 2 " + backgroundColor + boardColor + "║         ║         ║         ║         ║         ║" + ANSI_RESET);
        out.println("   " + backgroundColor + boardColor + "║         ║         ║         ║         ║         ║" + ANSI_RESET);
        out.println("   " + backgroundColor + boardColor + "╠═════════╬═════════╬═════════╬═════════╬═════════╣" + ANSI_RESET);
        out.println("   " + backgroundColor + boardColor + "║         ║         ║         ║         ║         ║" + ANSI_RESET);
        out.println(" 3 " + backgroundColor + boardColor + "║         ║         ║         ║         ║         ║" + ANSI_RESET);
        out.println("   " + backgroundColor + boardColor + "║         ║         ║         ║         ║         ║" + ANSI_RESET);
        out.println("   " + backgroundColor + boardColor + "╠═════════╬═════════╬═════════╬═════════╬═════════╣" + ANSI_RESET);
        out.println("   " + backgroundColor + boardColor + "║         ║         ║         ║         ║         ║" + ANSI_RESET);
        out.println(" 4 " + backgroundColor + boardColor + "║         ║         ║         ║         ║         ║" + ANSI_RESET);
        out.println("   " + backgroundColor + boardColor + "║         ║         ║         ║         ║         ║" + ANSI_RESET);
        out.println("   " + backgroundColor + boardColor + "╠═════════╬═════════╬═════════╬═════════╬═════════╣" + ANSI_RESET);
        out.println("   " + backgroundColor + boardColor + "║         ║         ║         ║         ║         ║" + ANSI_RESET);
        out.println(" 5 " + backgroundColor + boardColor + "║         ║         ║         ║         ║         ║" + ANSI_RESET);
        out.println("   " + backgroundColor + boardColor + "║         ║         ║         ║         ║         ║" + ANSI_RESET);
        out.println("   " + backgroundColor + boardColor + "╚═════════╩═════════╩═════════╩═════════╩═════════╝" + ANSI_RESET);

        out.print(ANSI_RESET);
        out.flush();


    }

    private static void saveCursor(){
        out.println(ESC + "s");
    }

    private static void restoreCursor(){
        out.println(ESC + "u");
    }


    private static void moveUp(int lines){
        out.print(ESC + Integer.toString(lines) + "A");
    }

    private static void moveDown(int lines){
        out.print(ESC + Integer.toString(lines) + "B");
    }

    private static void moveRight(int lines){
        out.print(ESC + Integer.toString(lines) + "C");
    }

    private static void moveLeft(int lines){
        out.print(ESC + Integer.toString(lines) + "D");
    }

    private static void SelectCell(int line, int column){

        out.print(ESC + "H" + ESC + "1B");
        moveDown(2);
        moveRight(4);

        if(line > 1) moveDown(4*(line - 1));
        if(column > 1) moveRight(10* (column - 1));

        out.flush();

    }

    private static void printBackground(int line, int column, String Color){
        SelectCell(line, column);
        out.print(Color);
        out.print("█████████"); moveDown(1); moveLeft(9);
        out.print("█████████"); moveDown(1); moveLeft(9);
        out.print("█████████");
        out.print(ANSI_RESET);

        out.flush();

    }

    private static void printBlock(int line, int column, String color2, boolean neighborhood){

        SelectCell(line, column);
        String color;
        String background;
        if(neighborhood){
            color = RED;
            background = REDBG;
        }
        else {
            if ((line + column) % 2 == 0) {
                color = GREEN1;
                background = GREENBG1;
            } else {
                color = GREEN2;
                background = GREENBG2;
            }
        }
        out.print(background);
        out.print(color +" " + color2 + "▗▄▄▄▄▄▖" + color + " ");    moveDown(1); moveLeft(9);
        out.print(color +" " + color2 + "▐█████▌" + color + " ");    moveDown(1); moveLeft(9);
        out.print(color +" " + color2 + "▝▀▀▀▀▀▘" + color + " ");
        out.print(ANSI_RESET);

    }

    private static void drawLevel(int line, int column, String s){

        SelectCell(line, column);
        if(s == "Dome"){
            addDome();
            return;
        }



    }

    private static void addDome(){
        moveRight(1);
        out.print(WHITEBG);
        out.print(BLUE1);
        out.print("▗▄▄▄▄▄▖");    moveDown(1); moveLeft(7);
        out.print("▐█████▌");    moveDown(1); moveLeft(7);
        out.print("▝▀▀▀▀▀▘");
        out.print(ANSI_RESET);
    }

    private static void cellColors(String color1, String color2){
        boolean light = true;
        for(int i = 1; i <=5; i++){
            for(int j = 1; j<=5; j++) {
                if (light) {
                    printBackground(i, j, color1);
                } else {
                    printBackground(i, j, color2);
                }
                light = !light;
            }
        }
    }
}

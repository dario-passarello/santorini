package view.CLI;

import java.io.PrintWriter;

public class DrawElements {

    private static PrintWriter out = new PrintWriter(System.out, true);
    private static final String FLUSH = "\033[H\033[2J";
    private static final String ESC = (char) 27 + "[";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[38;5;250m";
    public static final String GREEN = "\u001B[38;5;157m";
    public static final String GREENBG = "\u001B[48;5;157m";
    public static final String GREYBG = "\u001B[48;5;250m";
    public static final String WHITE = "\u001B[38;5;15m";

    public static void main(String[] args){

        out.println(FLUSH);

        DrawBoard();
        saveCursor();
        restoreCursor();

        saveCursor();
        printBlock(2, 1);
        restoreCursor();


    }


    private static void DrawBoard(){

        out.print(ANSI_RED);
        out.println("        a         b         c         d         e     ");
        out.println("   ╔═════════╦═════════╦═════════╦═════════╦═════════╗");
        out.println("   ║         ║         ║         ║         ║         ║");
        out.println(" 1 ║         ║         ║         ║         ║         ║");
        out.println("   ║         ║         ║         ║         ║         ║");
        out.println("   ╠═════════╬═════════╬═════════╬═════════╬═════════╣");
        out.println("   ║         ║         ║         ║         ║         ║");
        out.println(" 2 ║         ║         ║         ║         ║         ║");
        out.println("   ║         ║         ║         ║         ║         ║");
        out.println("   ╠═════════╬═════════╬═════════╬═════════╬═════════╣");
        out.println("   ║         ║         ║         ║         ║         ║");
        out.println(" 3 ║         ║         ║         ║         ║         ║");
        out.println("   ║         ║         ║         ║         ║         ║");
        out.println("   ╠═════════╬═════════╬═════════╬═════════╬═════════╣");
        out.println("   ║         ║         ║         ║         ║         ║");
        out.println(" 4 ║         ║         ║         ║         ║         ║");
        out.println("   ║         ║         ║         ║         ║         ║");
        out.println("   ╠═════════╬═════════╬═════════╬═════════╬═════════╣");
        out.println("   ║         ║         ║         ║         ║         ║");
        out.println(" 5 ║         ║         ║         ║         ║         ║");
        out.println("   ║         ║         ║         ║         ║         ║");
        out.println("   ╚═════════╩═════════╩═════════╩═════════╩═════════╝");

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

    private static void printBackground(int line, int column){
        SelectCell(line, column);
        out.print(GREEN);
        out.print("█████████"); moveDown(1); moveLeft(9);
        out.print("█████████"); moveDown(1); moveLeft(9);
        out.print("█████████");
        out.print(ANSI_RESET);

        out.flush();

    }

    private static void printBlock(int line, int column){
        SelectCell(line, column);
        out.print(WHITE);
        out.print("  ▗▄▄▄▖  ");    moveDown(1); moveLeft(9);
        out.print("  ▐███▌  ");    moveDown(1); moveLeft(9);
        out.print("  ▝▀▀▀▘  ");
        out.print(ANSI_RESET);
    }
}

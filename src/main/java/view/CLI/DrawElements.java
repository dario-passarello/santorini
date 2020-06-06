package view.CLI;

import model.Board;
import model.Builder;
import model.Square;
import utils.Coordinate;

import java.io.PrintWriter;
import java.util.List;

public class DrawElements {

    private static PrintWriter out = new PrintWriter(System.out, true);
    public static final String FLUSH = "\033[H\033[2J";
    public static final String ESC = (char) 27 + "[";
    public static final String RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[38;5;88m";
    public static final String REDBG = "\u001B[48;5;88m";
    public static final String GREEN = "\u001B[38;5;157m";
    public static final String GREENBG = "\u001B[48;5;157m";
    public static final String GREYBG = "\u001B[48;5;250m";
    public static final String WHITE = "\u001B[38;5;231m";
    public static final String WHITEBG = "\u001B[38;5;231m";

    public static final String BLUE_1 = "\u001B[38;5;51m";
    public static final String BLUE_2 = "\u001B[38;5;45m";
    public static final String BLUE_3 = "\u001B[38;5;39m";
    public static final String BLUE_4 = "\u001B[38;5;33m";
    public static final String BLUE_5 = "\u001B[38;5;27m";
    public static final String BLUE_6 = "\u001B[38;5;27m";


    public static void main(String[] args){

        System.out.println(FLUSH);
        drawBoard(GREENBG, WHITE);
        saveCursor();
        restoreCursor();





    }



    public static void drawBoard(String background, String mainColor){


        out.println("        a         b         c         d         e     ");
        out.println("   " + background + mainColor + "╔═════════╦═════════╦═════════╦═════════╦═════════╗" + RESET);
        out.println("   " + background + mainColor + "║         ║         ║         ║         ║         ║" + RESET);
        out.println(" 1 " + background + mainColor + "║         ║         ║         ║         ║         ║" + RESET);
        out.println("   " + background + mainColor + "║         ║         ║         ║         ║         ║" + RESET);
        out.println("   " + background + mainColor + "╠═════════╬═════════╬═════════╬═════════╬═════════╣" + RESET);
        out.println("   " + background + mainColor + "║         ║         ║         ║         ║         ║" + RESET);
        out.println(" 2 " + background + mainColor + "║         ║         ║         ║         ║         ║" + RESET);
        out.println("   " + background + mainColor + "║         ║         ║         ║         ║         ║" + RESET);
        out.println("   " + background + mainColor + "╠═════════╬═════════╬═════════╬═════════╬═════════╣" + RESET);
        out.println("   " + background + mainColor + "║         ║         ║         ║         ║         ║" + RESET);
        out.println(" 3 " + background + mainColor + "║         ║         ║         ║         ║         ║" + RESET);
        out.println("   " + background + mainColor + "║         ║         ║         ║         ║         ║" + RESET);
        out.println("   " + background + mainColor + "╠═════════╬═════════╬═════════╬═════════╬═════════╣" + RESET);
        out.println("   " + background + mainColor + "║         ║         ║         ║         ║         ║" + RESET);
        out.println(" 4 " + background + mainColor + "║         ║         ║         ║         ║         ║" + RESET);
        out.println("   " + background + mainColor + "║         ║         ║         ║         ║         ║" + RESET);
        out.println("   " + background + mainColor + "╠═════════╬═════════╬═════════╬═════════╬═════════╣" + RESET);
        out.println("   " + background + mainColor + "║         ║         ║         ║         ║         ║" + RESET);
        out.println(" 5 " + background + mainColor + "║         ║         ║         ║         ║         ║" + RESET);
        out.println("   " + background + mainColor + "║         ║         ║         ║         ║         ║" + RESET);
        out.println("   " + background + mainColor + "╚═════════╩═════════╩═════════╩═════════╩═════════╝" + RESET);

        out.flush();


    }

    public static void drawBuilder(Coordinate coordinate, String backGround){
        selectCell(coordinate.getX() + 1, coordinate.getY() + 1);
        moveDown(1);
        moveRight(4);
        out.print(BLUE_6 + backGround + "X");
        out.print(RESET);
        out.flush();

    }

    public static void drawSquare(Square square, boolean neighborhood){
        int line = square.getCoordinate().getX() + 1;
        int column = square.getCoordinate().getY() + 1;
        String background;
        String color;

        // Set the color of the square background
        if(neighborhood) background = REDBG;
        else background = GREENBG;

        if(square.getBuildLevel() == 0) printBackground(line, column, background);
        else{
            if(neighborhood) printBlock(line, column, background, WHITE);
            else{
                if(square.isDomed()) printBlock(line, column, WHITEBG, BLUE_6);
                else printBackground(line, column, WHITEBG);
            }
        }



    }

    public static void refreshBoard(Board board, List<Builder> builders){
        out.println(FLUSH);
        drawBoard(GREENBG, WHITE);
        for(int i = 0; i <5; i++){
            for(int j = 0; j < 5; j++){
                drawSquare(board.squareAt(i, j), false);
            }
        }
        for(Builder builder : builders){
            String background;
            if(builder.getSquare().getBuildLevel() != 0) background = WHITEBG;
            else background = GREENBG;
            drawBuilder(builder.getSquare().getCoordinate(), background);
        }
    }

    public static void saveCursor(){
        out.println(ESC + "s");
    }

    public static void restoreCursor(){
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

    private static void selectCell(int line, int column){

        out.print(ESC + "H" + ESC + "1B");
        moveDown(2);
        moveRight(4);

        if(line > 1) moveDown(4*(line - 1));
        if(column > 1) moveRight(10* (column - 1));

        out.flush();

    }



    private static void printBackground(int line, int column, String color){
        selectCell(line, column);
        out.print(color);
        out.print("         "); moveDown(1); moveLeft(9);
        out.print("         "); moveDown(1); moveLeft(9);
        out.print("         ");
        out.print(RESET);

        out.flush();

    }

    private static void printBlock(int line, int column, String background, String color){
        selectCell(line, column);
        out.print(background);
        out.print(color);
        out.print("  ▗▄▄▄▖  ");    moveDown(1); moveLeft(9);
        out.print("  ▐███▌  ");    moveDown(1); moveLeft(9);
        out.print("  ▝▀▀▀▘  ");
        out.print(RESET);
    }


    private static void AlternativeTitle(){

        System.out.println(
                WHITE + "      ::::::::      :::     ::::    ::: ::::::::::: ::::::::  :::::::::  ::::::::::: ::::    ::: ::::::::::: \n" +
                        BLUE_1 + "    :+:    :+:   :+: :+:   :+:+:   :+:     :+:    :+:    :+: :+:    :+:     :+:     :+:+:   :+:     :+:      \n" +
                        BLUE_2 + "   +:+         +:+   +:+  :+:+:+  +:+     +:+    +:+    +:+ +:+    +:+     +:+     :+:+:+  +:+     +:+       \n" +
                        BLUE_3 + "  +#++:++#++ +#++:++#++: +#+ +:+ +#+     +#+    +#+    +:+ +#++:++#:      +#+     +#+ +:+ +#+     +#+        \n" +
                        BLUE_4 + "        +#+ +#+     +#+ +#+  +#+#+#     +#+    +#+    +#+ +#+    +#+     +#+     +#+  +#+#+#     +#+         \n" +
                        BLUE_5 + "#+#    #+# #+#     #+# #+#   #+#+#     #+#    #+#    #+# #+#    #+#     #+#     #+#   #+#+#     #+#          \n" +
                        BLUE_6 + "########  ###     ### ###    ####     ###     ########  ###    ### ########### ###    #### ########### " + RESET);
    }

    public static void DrawTitle(String mainColor, String shadeColor){
        //Not proud of this code
        System.out.println(
                WHITE + "\t███████" + BLUE_6 + "╗" + WHITE + " █████" + BLUE_6 + "╗" + WHITE + " ███" + BLUE_6 + "╗" + WHITE + "   ██" + BLUE_6 + "╗" + WHITE + "████████" + BLUE_6 + "╗" + WHITE + " ██████" + BLUE_6 + "╗" + WHITE + " ██████" + BLUE_6 + "╗" + WHITE + " ██" + BLUE_6 + "╗" + WHITE + "███" + BLUE_6 + "╗" + WHITE + "   ██" + BLUE_6 + "╗" + WHITE + "██" + BLUE_6 + "╗" + "\n\t" +
                        WHITE + "██" + BLUE_6 + "╔════╝" + WHITE + "██" + BLUE_6 + "╔══" + WHITE + "██" + BLUE_6 + "╗" + WHITE + "████" + BLUE_6 + "╗" + WHITE + "  ██" + BLUE_6 + "║╚══" + WHITE + "██" + BLUE_6 + "╔══╝" + WHITE + "██" + BLUE_6 + "╔═══" + WHITE + "██" + BLUE_6 + "╗" + WHITE + "██" + BLUE_6 + "╔══" + WHITE + "██" + BLUE_6 + "╗" + WHITE + "██" + BLUE_6 + "║" + WHITE + "████" + BLUE_6 + "╗" + WHITE + "  ██" + BLUE_6 + "║" + WHITE + "██" + BLUE_6 + "║" +  "\n\t" +
                        mainColor + "███████" + shadeColor + "╗" + mainColor + "███████" + shadeColor + "║" + mainColor + "██" + shadeColor + "╔" + mainColor + "██" + shadeColor + "╗" + mainColor + " ██" + shadeColor + "║" + mainColor + "   ██" + shadeColor + "║" + mainColor + "   ██" + shadeColor + "║" + mainColor + "   ██" + shadeColor + "║" + mainColor + "██████" + shadeColor + "╔╝" + mainColor + "██" + shadeColor + "║" + mainColor + "██" + shadeColor + "╔" + mainColor + "██" + shadeColor + "╗" + mainColor + " ██" + shadeColor + "║" + mainColor + "██" + shadeColor + "║" + "\n\t" +
                        shadeColor + "╚════" + mainColor + "██" + shadeColor + "║" + mainColor + "██" + shadeColor + "╔══" + mainColor + "██" + shadeColor + "║" + mainColor + "██" + shadeColor + "║╚" + mainColor + "██" + shadeColor + "╗" + mainColor + "██" + shadeColor + "║" + mainColor + "   ██" + shadeColor + "║" + mainColor + "   ██" + shadeColor + "║" + mainColor + "   ██" + shadeColor + "║" + mainColor + "██" + shadeColor + "╔══" + mainColor + "██" + shadeColor + "╗" + mainColor + "██" + shadeColor + "║" + mainColor + "██" + shadeColor + "║╚" + mainColor + "██" + shadeColor + "╗" + mainColor + "██" + shadeColor + "║" + mainColor + "██" + shadeColor + "║" + "\n\t" +
                        mainColor + "███████" + shadeColor + "║" + mainColor + "██" + shadeColor + "║" + mainColor + "  ██" + shadeColor + "║" + mainColor + "██" + shadeColor + "║ ╚" + mainColor + "████" + shadeColor + "║" + mainColor + "   ██" + shadeColor + "║   ╚" + mainColor + "██████" + shadeColor + "╔╝" + mainColor + "██" + shadeColor + "║" + mainColor + "  ██" + shadeColor + "║" + mainColor + "██" + shadeColor + "║" + mainColor + "██" + shadeColor + "║ ╚" + mainColor + "████" + shadeColor + "║" + mainColor + "██" + shadeColor + "║" + "\n\t" +
                        shadeColor + "╚══════╝╚═╝  ╚═╝╚═╝  ╚═══╝   ╚═╝    ╚═════╝ ╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝╚═╝" + RESET);
    }

    public static void drawStartGameBox(){
        System.out.println(
                "\t\t     ╔════════════════════════════════════════╗\n" +
                "\t\t     ║           (S) - START GAME             ║\n" +
                "\t\t     ╚════════════════════════════════════════╝");
    }

    public static void drawCreditsGameBox(){
        System.out.println(
                "\t\t     ╔════════════════════════════════════════╗\n" +
                "\t\t     ║           (C) - CREDITS                ║\n" +
                "\t\t     ╚════════════════════════════════════════╝");
    }

    public static void drawQuitGameBox(){
        System.out.println(
                "\t\t     ╔════════════════════════════════════════╗\n" +
                "\t\t     ║           (Q) - QUIT GAME              ║\n" +
                "\t\t     ╚════════════════════════════════════════╝");
    }
}



package view.CLI;

import model.Board;
import model.Builder;
import model.Player;
import model.Square;
import utils.Coordinate;

import java.io.PrintWriter;
import java.util.List;

public class DrawElements {

    public static PrintWriter out = new PrintWriter(System.out, true);
    private static final String firstBackgroundColor = Colors.GREENBG_83;
    private static final String secondBackgroundColor = Colors.GREENBG_41;
    private static final String borderColor = Colors.GREY_250;
    private static final String player1Color = Colors.BLUE_21;
    private static final String player2Color = Colors.YELLOW_226;
    private static final String player3Color = Colors.RED_196;
    private static final String levelColor = Colors.BLUE_20;
    public static final String FLUSH = "\033[H\033[2J";
    public static final String ESC = (char) 27 + "[";
    public static final String ANSI_RED = "\u001B[38;5;88m";


    public static void main(String[] args){

        System.out.println(FLUSH);
        drawBoard(Colors.GREENBG_157, Colors.WHITE_231);
        saveCursor();
        restoreCursor();





    }



    public static void drawBoard(String background, String mainColor){


        out.println("        a         b         c         d         e     ");
        out.println("   " + background + mainColor + "╔═════════╦═════════╦═════════╦═════════╦═════════╗" + Colors.RESET);
        out.println("   " + background + mainColor + "║         ║         ║         ║         ║         ║" + Colors.RESET);
        out.println(" 1 " + background + mainColor + "║         ║         ║         ║         ║         ║" + Colors.RESET);
        out.println("   " + background + mainColor + "║         ║         ║         ║         ║         ║" + Colors.RESET);
        out.println("   " + background + mainColor + "╠═════════╬═════════╬═════════╬═════════╬═════════╣" + Colors.RESET);
        out.println("   " + background + mainColor + "║         ║         ║         ║         ║         ║" + Colors.RESET);
        out.println(" 2 " + background + mainColor + "║         ║         ║         ║         ║         ║" + Colors.RESET);
        out.println("   " + background + mainColor + "║         ║         ║         ║         ║         ║" + Colors.RESET);
        out.println("   " + background + mainColor + "╠═════════╬═════════╬═════════╬═════════╬═════════╣" + Colors.RESET);
        out.println("   " + background + mainColor + "║         ║         ║         ║         ║         ║" + Colors.RESET);
        out.println(" 3 " + background + mainColor + "║         ║         ║         ║         ║         ║" + Colors.RESET);
        out.println("   " + background + mainColor + "║         ║         ║         ║         ║         ║" + Colors.RESET);
        out.println("   " + background + mainColor + "╠═════════╬═════════╬═════════╬═════════╬═════════╣" + Colors.RESET);
        out.println("   " + background + mainColor + "║         ║         ║         ║         ║         ║" + Colors.RESET);
        out.println(" 4 " + background + mainColor + "║         ║         ║         ║         ║         ║" + Colors.RESET);
        out.println("   " + background + mainColor + "║         ║         ║         ║         ║         ║" + Colors.RESET);
        out.println("   " + background + mainColor + "╠═════════╬═════════╬═════════╬═════════╬═════════╣" + Colors.RESET);
        out.println("   " + background + mainColor + "║         ║         ║         ║         ║         ║" + Colors.RESET);
        out.println(" 5 " + background + mainColor + "║         ║         ║         ║         ║         ║" + Colors.RESET);
        out.println("   " + background + mainColor + "║         ║         ║         ║         ║         ║" + Colors.RESET);
        out.println("   " + background + mainColor + "╚═════════╩═════════╩═════════╩═════════╩═════════╝" + Colors.RESET);


        for(int i = 1; i <= 5; i++) {
            for (int j = 1; j <= 5; j++) {

                printBackground(i, j, getCorrectAlternateColor(i, j), levelColor, 0);
            }
        }
        out.print(Colors.RESET);
        out.print(DrawElements.ESC + "25H");
        out.flush();


    }

    private static String getCorrectAlternateColor(int i, int j){
        String color;
        if((i+j) % 2 == 0) color = firstBackgroundColor;
        else color = secondBackgroundColor;
        return color;
    }

    public static void drawBuilder(Coordinate coordinate, String backGround, int player){
        String color;
        switch(player){
            case 1: color = player1Color; break;
            case 2: color = player2Color; break;
            case 3: color = player3Color; break;
            default: color = player1Color;
        }
        selectCell(coordinate.getX() + 1, coordinate.getY() + 1);
        moveDown(1);
        moveRight(4);
        out.print(color + backGround + "X");
        out.print(Colors.RESET);
        out.flush();

    }

    public static void drawSquare(Square square, boolean neighborhood){
        int line = square.getCoordinate().getX() + 1;
        int column = square.getCoordinate().getY() + 1;
        String background;
        String color;

        // Set the color of the square background
        if(neighborhood) background = Colors.REDBG;
        else background = getCorrectAlternateColor(line, column);

        if(square.getBuildLevel() == 0) printBackground(line, column, background, levelColor, 0 );
        else{
            if(neighborhood) printBlock(line, column, background, Colors.WHITE_231, levelColor, square.getBuildLevel());
            else{
                if(square.isDomed()) printBlock(line, column, Colors.WHITEBG_231, Colors.BLUE_21, levelColor, square.getBuildLevel());
                else printBackground(line, column, Colors.WHITEBG_231, levelColor, square.getBuildLevel());
            }
        }



    }

    public static void refreshBoard(Board board, List<Builder> builders, List<Player> players){
        out.println(FLUSH);
        drawBoard(firstBackgroundColor, borderColor);
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                drawSquare(board.squareAt(i, j), false);
            }
        }


        for(Builder builder : builders){
            // Find the correct background of the builder
            String background;
            if(builder.getSquare().getBuildLevel() != 0) background = Colors.WHITEBG_231;
            else background = getCorrectAlternateColor(
                    builder.getSquare().getCoordinate().getX(),
                    builder.getSquare().getCoordinate().getY());
            // Find who does the builder belong to
            int i = 1;
            for(Player player : players){
                if(!player.equals(builder.getOwner())) i++;
            }
            // Draw the Builder
            drawBuilder(builder.getSquare().getCoordinate(), background, i);
        }

        out.print(DrawElements.ESC + "25H");
        out.flush();
    }

    public static void saveCursor(){
        out.println(ESC + "s");
    }

    public static void restoreCursor(){
        out.println(ESC + "u");
    }


    public static void moveUp(int lines){
        out.print(ESC + Integer.toString(lines) + "A");
    }

    public static void moveDown(int lines){
        out.print(ESC + Integer.toString(lines) + "B");
    }

    public static void moveRight(int lines){
        out.print(ESC + Integer.toString(lines) + "C");
    }

    public static void moveLeft(int lines){
        out.print(ESC + Integer.toString(lines) + "D");
    }

    public static void selectCell(int line, int column){

        out.print(ESC + "H" + ESC + "1B");
        moveDown(2);
        moveRight(4);

        if(line > 1) moveDown(4*(line - 1));
        if(column > 1) moveRight(10* (column - 1));

        out.flush();

    }



    private static void printBackground(int line, int column, String color, String levelColor, int level){
        String levelToString;
        if(level == 0) levelToString = " ";
        else levelToString = Integer.toString(level);
        selectCell(line, column);
        out.print(color);
        out.print(" " +
                  levelColor + levelToString + color +
                   "       "); moveDown(1); moveLeft(9);
        out.print("         "); moveDown(1); moveLeft(9);
        out.print("         ");
        out.print(Colors.RESET);

        out.flush();

    }

    private static void printBlock(int line, int column, String background, String color, String levelColor,  int level){
        String levelToString = Integer.toString(level);
        selectCell(line, column);
        out.print(background);
        out.print(color);
        out.print(" " + levelColor + levelToString + color + "▗▄▄▄▖  ");    moveDown(1); moveLeft(9);
        out.print("  ▐███▌  ");    moveDown(1); moveLeft(9);
        out.print("  ▝▀▀▀▘  ");
        out.print(Colors.RESET);

        out.flush();
    }


    private static void AlternativeTitle(){

        System.out.println(
                Colors.WHITE_231 + "      ::::::::      :::     ::::    ::: ::::::::::: ::::::::  :::::::::  ::::::::::: ::::    ::: ::::::::::: \n" +
                        Colors.BLUE_51 + "    :+:    :+:   :+: :+:   :+:+:   :+:     :+:    :+:    :+: :+:    :+:     :+:     :+:+:   :+:     :+:      \n" +
                        Colors.BLUE_45 + "   +:+         +:+   +:+  :+:+:+  +:+     +:+    +:+    +:+ +:+    +:+     +:+     :+:+:+  +:+     +:+       \n" +
                        Colors.BLUE_39 + "  +#++:++#++ +#++:++#++: +#+ +:+ +#+     +#+    +#+    +:+ +#++:++#:      +#+     +#+ +:+ +#+     +#+        \n" +
                        Colors.BLUE_33 + "        +#+ +#+     +#+ +#+  +#+#+#     +#+    +#+    +#+ +#+    +#+     +#+     +#+  +#+#+#     +#+         \n" +
                        Colors.BLUE_27 + "#+#    #+# #+#     #+# #+#   #+#+#     #+#    #+#    #+# #+#    #+#     #+#     #+#   #+#+#     #+#          \n" +
                        Colors.BLUE_27 + "########  ###     ### ###    ####     ###     ########  ###    ### ########### ###    #### ########### " + Colors.RESET);
    }

    public static void drawTitle(String mainColor, String shadeColor){
        //Not proud of this code
        System.out.println(
                mainColor + "\t███████" + shadeColor + "╗" + mainColor + " █████" + shadeColor + "╗" + mainColor+ " ███" + shadeColor + "╗" + mainColor + "   ██" + shadeColor + "╗" + mainColor + "████████" + shadeColor + "╗" + mainColor + " ██████" + shadeColor + "╗" + mainColor + " ██████" + shadeColor + "╗" + mainColor + " ██" + shadeColor + "╗" + mainColor + "███" + shadeColor + "╗" + mainColor + "   ██" + shadeColor + "╗" + mainColor + "██" + shadeColor + "╗" + "\n\t" +
                        mainColor + "██" + shadeColor + "╔════╝" + mainColor + "██" + shadeColor + "╔══" + mainColor + "██" + shadeColor + "╗" + mainColor + "████" + shadeColor + "╗" + mainColor + "  ██" + shadeColor + "║╚══" + mainColor + "██" + shadeColor + "╔══╝" + mainColor + "██" + shadeColor + "╔═══" + mainColor + "██" + shadeColor + "╗" + mainColor + "██" + shadeColor + "╔══" + mainColor + "██" + shadeColor + "╗" + mainColor + "██" + shadeColor + "║" + mainColor + "████" + shadeColor + "╗" + mainColor + "  ██" + shadeColor + "║" + mainColor + "██" + shadeColor + "║" +  "\n\t" +
                        mainColor + "███████" + shadeColor + "╗" + mainColor + "███████" + shadeColor + "║" + mainColor + "██" + shadeColor + "╔" + mainColor + "██" + shadeColor + "╗" + mainColor + " ██" + shadeColor + "║" + mainColor + "   ██" + shadeColor + "║" + mainColor + "   ██" + shadeColor + "║" + mainColor + "   ██" + shadeColor + "║" + mainColor + "██████" + shadeColor + "╔╝" + mainColor + "██" + shadeColor + "║" + mainColor + "██" + shadeColor + "╔" + mainColor + "██" + shadeColor + "╗" + mainColor + " ██" + shadeColor + "║" + mainColor + "██" + shadeColor + "║" + "\n\t" +
                        shadeColor + "╚════" + mainColor + "██" + shadeColor + "║" + mainColor + "██" + shadeColor + "╔══" + mainColor + "██" + shadeColor + "║" + mainColor + "██" + shadeColor + "║╚" + mainColor + "██" + shadeColor + "╗" + mainColor + "██" + shadeColor + "║" + mainColor + "   ██" + shadeColor + "║" + mainColor + "   ██" + shadeColor + "║" + mainColor + "   ██" + shadeColor + "║" + mainColor + "██" + shadeColor + "╔══" + mainColor + "██" + shadeColor + "╗" + mainColor + "██" + shadeColor + "║" + mainColor + "██" + shadeColor + "║╚" + mainColor + "██" + shadeColor + "╗" + mainColor + "██" + shadeColor + "║" + mainColor + "██" + shadeColor + "║" + "\n\t" +
                        mainColor + "███████" + shadeColor + "║" + mainColor + "██" + shadeColor + "║" + mainColor + "  ██" + shadeColor + "║" + mainColor + "██" + shadeColor + "║ ╚" + mainColor + "████" + shadeColor + "║" + mainColor + "   ██" + shadeColor + "║   ╚" + mainColor + "██████" + shadeColor + "╔╝" + mainColor + "██" + shadeColor + "║" + mainColor + "  ██" + shadeColor + "║" + mainColor + "██" + shadeColor + "║" + mainColor + "██" + shadeColor + "║ ╚" + mainColor + "████" + shadeColor + "║" + mainColor + "██" + shadeColor + "║" + "\n\t" +
                        shadeColor + "╚══════╝╚═╝  ╚═╝╚═╝  ╚═══╝   ╚═╝    ╚═════╝ ╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝╚═╝" + Colors.RESET);
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



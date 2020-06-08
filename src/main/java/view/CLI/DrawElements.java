package view.CLI;

import javafx.scene.paint.Color;
import model.Board;
import model.Builder;
import model.Player;
import model.Square;
import utils.Coordinate;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class DrawElements {

    public static PrintWriter out = new PrintWriter(System.out, true);
    private static final String firstBackgroundColor = Colors.GREENBG_83;
    private static final String secondBackgroundColor = Colors.GREENBG_41;
    private static final String borderColor = Colors.GREY_250;
    public static final String player1Color = Colors.BLUE_21;
    public static final String player2Color = Colors.YELLOW_226;
    public static final String player3Color = Colors.RED_196;
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
            default: color = Colors.RESET;
        }
        selectCell(coordinate.getX() + 1, coordinate.getY() + 1);
        moveDown(1);
        moveRight(4);
        out.print(color + backGround + "X");
        out.print(Colors.RESET);
        out.flush();

    }

    public static void refreshBuilders(List<Builder> builders, List<Player> players, boolean neighborhood){
        for(Builder builder : builders){
            // Find the correct background of the builder
            String background;
            if(builder.getSquare().getBuildLevel() != 0) background = Colors.WHITEBG_231;
            else{
                if(neighborhood) background = Colors.REDBG;
                else{
                    background = getCorrectAlternateColor(
                            builder.getSquare().getCoordinate().getX(),
                            builder.getSquare().getCoordinate().getY());
                }
            }
            // Find who does the builder belong to
            int i = 1;
            for(Player player : players){
                if(!player.getName().equals(builder.getOwner().getName())){
                    i++;
                }
                else break;
            }
            // Draw the Builder
            drawBuilder(builder.getSquare().getCoordinate(), background, i);
        }
    }

    public static void drawSquare(Square square, List<Builder> builders, List<Player> players, boolean neighborhood){
        int line = square.getCoordinate().getX() + 1;
        int column = square.getCoordinate().getY() + 1;
        String background;
        String color;

        // Set the color of the square background
        if(neighborhood) background = Colors.REDBG;
        else background = getCorrectAlternateColor(line, column);

        // Check if it is required to print a block or just to paint the background
        if(square.getBuildLevel() == 0){
            if(square.isDomed())
                printBlock(line, column, getCorrectAlternateColor(line, column), Colors.BLUE_21, levelColor, 0 );
            else
                printBackground(line, column, background, levelColor, 0 );
        }
        else{
            if(neighborhood) printBlock(line, column, background, Colors.WHITE_231, levelColor, square.getBuildLevel());
            else{
                if(square.isDomed()){
                    printBlock(line, column, Colors.WHITEBG_231, Colors.BLUE_21, levelColor, square.getBuildLevel());
                }
                else printBackground(line, column, Colors.WHITEBG_231, levelColor, square.getBuildLevel());
            }
        }

        // Find if there are any builders in the square to draw. If so, print them correctly
        List<Builder> toRefresh = new ArrayList<>();
        for(Builder builder : builders){
            if(builder.getSquare().getCoordinate().equals(square.getCoordinate()))
                toRefresh.add(builder);
        }
        refreshBuilders(toRefresh, players, neighborhood);



    }

    public static void refreshBoard(Board board, List<Builder> builders, List<Player> players, String activePlayer, String client){
        out.println(FLUSH);
        drawBoard(firstBackgroundColor, borderColor);
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                drawSquare(board.squareAt(i, j), builders, players, false);
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
                if(!player.getName().equals(builder.getOwner().getName())){
                    i++;
                }
                else break;
            }
            // Draw the Builder
            drawBuilder(builder.getSquare().getCoordinate(), background, i);
        }

        drawGameInfo(players, activePlayer, client);

        out.print(DrawElements.ESC + "25H");
        out.flush();
    }

    /**
     * This Method draws the little box near the board that show the info about the players
     * @param players The list of players in the game
     * @param activeplayer The player who is holding the turn
     * @param client The player who is playing
     */
    public static void drawGameInfo(List<Player> players, String activeplayer, String client){

        selectCell(1,1);
        moveUp(1);
        moveRight(55);
        out.flush();
        out.print("╔══════════════════════════════════════╗");
        out.flush();
        moveDown(1);
        moveLeft(40);
        out.flush();
        for(Player player : players){
            // Find the number of spaces between the player name and the god name
            int numspacesPlayer = 20 - player.getName().length();
            String playerSpaces = "";
            for(int j = 0; j < numspacesPlayer; j++){
                playerSpaces = playerSpaces.concat(" ");
            }
            // Find the number of spaces between the god name and the end of the box
            int numspacesGod = 11 - player.getGod().getName().length();
            String godSpaces = "";
            for(int j = 0; j < numspacesGod; j++){
                godSpaces = godSpaces.concat(" ");
            }

            String color;
            if(player.getName().equals(client)) color = Colors.GREEN_47;
            else color = Colors.RESET;

            String towrite = "║ " + getPlayerColor(players, player) + "██ " + Colors.RESET + color + player.getName() +
                    Colors.RESET + playerSpaces + " - " + player.getGod().getName() + godSpaces + "║";
            out.print(towrite);
            moveDown(1);
            moveLeft(40);
            out.flush();
        }
        out.print("╚══════════════════════════════════════╝");
        moveDown(2);
        moveLeft(40);
        out.print("  CURRENT TURN: " + Colors.YELLOW_227 + activeplayer + Colors.RESET);

    }


    public static String getPlayerColor(List<Player> players, Player currentplayer){
        int i = 1;
        for(Player player : players){
            if(!player.getName().equals(currentplayer.getName())) i++;
            else break;
        }
        String color;
        switch(i){
            case 1: color = DrawElements.player1Color; break;
            case 2: color = DrawElements.player2Color; break;
            case 3: color = DrawElements.player3Color; break;
            default: color = Colors.RESET;
        }
        return color;

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
        String levelToString;
        if(level == 0) levelToString = " ";
        else levelToString = Integer.toString(level);
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



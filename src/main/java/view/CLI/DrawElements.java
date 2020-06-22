package view.CLI;

import javafx.scene.paint.Color;
import model.Board;
import model.Builder;
import model.Player;
import model.Square;
import utils.Coordinate;
import view.AssetLoader;
import view.GodAssetsBundle;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * This class contains all the methods responsible for the aesthetic of the game and its appearance
 */
public class DrawElements {

    public static PrintWriter out = new PrintWriter(System.out, true);
    private static final String firstBackgroundColor = Colors.GREENBG_83;
    private static final String secondBackgroundColor = Colors.GREENBG_41;
    private static final String borderColor = Colors.GREY_250;

    public static final String player1Color = Colors.BLUE_21;
    public static final String player2Color = Colors.YELLOW_226;
    public static final String player3Color = Colors.RED_196;

    public static final String player1ColorLight = Colors.BLUE_153;
    public static final String player2ColorLight = Colors.YELLOW_228;
    public static final String player3ColorLight = Colors.RED_210;
    private static final String levelColor = Colors.BLUE_20;
    public static final String FLUSH = "\033[H\033[2J";
    public static final String ESC = (char) 27 + "[";


    public static void main(String[] args){

        drawVictory();

        drawDefeat();






    }


    /**
     * This method draws the Board
     * @param background The color of the background
     * @param mainColor The color of the Square delimiters
     */
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

    /**
     * This method gets the correct color of the Square, considering the board as a chess grid
     * @param i The first coordinate of the square (line)
     * @param j The second coordinate of the square (column)
     * @return The string representing the color of the square
     */
    private static String getCorrectAlternateColor(int i, int j){
        String color;
        if((i+j) % 2 == 0) color = firstBackgroundColor;
        else color = secondBackgroundColor;
        return color;
    }

    /**
     * A basic method that draws the builder on an assigned square
     * @param coordinate The coordinate parameter
     * @param backGround The color of the background of the cell containing the symbol of the builder
     * @param player The number of the player owning the builder drawn (required to identify its color)
     */
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
        out.print(color + backGround + "♜");
        out.print(Colors.RESET);
        out.flush();

    }

    /**
     * Given the board, this method draws exclusively the builders in the square
     * @param builders The list of builders in the game
     * @param players The list of players in the game
     * @param neighborhood A boolean that specifies if the builder is on a highlighted square
     */
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


    /**
     * This method draws a square using the information from the board
     * @param square The square parameter
     * @param builders The list of builders in the game
     * @param players The list of players in the game
     * @param neighborhood A boolean that specifies if the square should be highlighted as a neighborhood or not
     */
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

    /**
     * This method updates the whole board, square by square
     * It also updates the Game Information panel
     * @param board The current board
     * @param builders The list of builders present at the moment
     * @param players The list of players currently playing
     * @param activePlayer The player who is currently holding the turn
     * @param client The client from which this method is called
     */
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
     * This Method draws the Game Information panel, which is the little box near the board that shows the info about the players
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

    /**
     * This method writes the descriptions of the gods during the game
     * @param players The list of players in the game
     */
    public static void writeGodInfo(List<Player> players){
        int maxCharacters = 40;
        int row = 11;

        out.print(ESC + Integer.toString(row) + ";60H");  // Cursor position to write to
        out.flush();
        for(Player player : players){
            // Get the god name
            String godName = player.getGod().getName();
            String description = AssetLoader.getGodAssetsBundle(godName).getDescription();

            // Divide everything into words
            String[] split;
            String[] words;
            if(godName.equals("Mortal")){
                split = new String[1];
                split[0] = "";
                words = description.split(" ");
            }
            else {
                split = description.split("\n");
                words = split[1].split(" ");
            }
            int characterWritten = 0;

            // Write [GOD] - [Condition]
            String intro = godName + " - " + split[0];
            out.print(intro);
            moveDown(1);
            moveLeft(intro.length());
            out.flush();

            // Write [Description]
            out.print(getPlayerSecondColor(players, player));
            for(String word : words){
                if((characterWritten + word.length() + 1) > (maxCharacters + 1)){
                    moveDown(1);
                    moveLeft(characterWritten);
                    out.flush();
                    characterWritten = 0;
                }
                out.print(word + " ");
                characterWritten = characterWritten + word.length() + 1;
                out.flush();
            }
            out.print(Colors.RESET);

            // Repositioning
            moveDown(3);
            moveLeft(characterWritten);
            out.flush();
        }
    }

    /**
     * This method writes the End Game Message (Victory or Defeat)
     * @param players The list of players in the game
     * @param winner The player who has won the match
     * @param client The name of the player who calls this method
     */
    public static void writeWinner(List<Player> players, Player winner, String client){

        // Identifying the position to write the winner message
        selectCell(1, 1);
        moveRight(55);
        for(Player p : players){
            moveDown(1);
        }
        moveDown(2);
        out.flush();

        // Writing the winner message
        out.print(ESC + "0K"); // Erase line to the right
        out.print("  WINNER: " + Colors.YELLOW_227 + winner.getName() + Colors.RESET);

        // Identifying the position to write the end game message
        selectCell(1,1);
        moveRight(55);
        moveDown(7);

        // Writing the end Game Message
        if(winner.getName().equals(client)) drawVictory();
        else drawDefeat();
        out.print(DrawElements.ESC + "25H");
        out.flush();
    }


    /**
     * This method extracts the correct color of the player
     * @param players The list of players in the game
     * @param currentplayer The player parameter
     * @return The String representing the color of the selected player
     */
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

    /**
     * This method extracts the correct light color of the player
     * @param players The list of players in the game
     * @param currentplayer The player parameter
     * @return The String representing the color of the selected player
     */
    public static String getPlayerSecondColor(List<Player> players, Player currentplayer){
        int i = 1;
        for(Player player : players){
            if(!player.getName().equals(currentplayer.getName())) i++;
            else break;
        }
        String color;
        switch(i){
            case 1: color = DrawElements.player1ColorLight; break;
            case 2: color = DrawElements.player2ColorLight; break;
            case 3: color = DrawElements.player3ColorLight; break;
            default: color = Colors.RESET;
        }
        return color;

    }

// THESE ARE ALL METHODS THAT HANDLE THE MOVEMENT OF THE CURSOR IN THE TERMINAL

    /**
     * This method saves the current position of the cursor
     */
    public static void saveCursor(){
        out.println(ESC + "s");
    }

    /**
     * This method èlaces the cursor at the position previously saved
     */
    public static void restoreCursor(){
        out.println(ESC + "u");
    }

    /**
     * This method moves the cursor upwards by the the number of lines selected
     * @param lines The number of lines
     */
    public static void moveUp(int lines){
        out.print(ESC + Integer.toString(lines) + "A");
    }

    /**
     * This method moves the cursor downwards by the number of line selected
     * @param lines The number of lines
     */
    public static void moveDown(int lines){
        out.print(ESC + Integer.toString(lines) + "B");
    }

    /**
     * This method moves the cursor to the right by a number of column equals to the parameter
     * @param columns The number of columns
     */
    public static void moveRight(int columns){
        out.print(ESC + Integer.toString(columns) + "C");
    }

    /**
     * This method moves the cursor to the left by a number of column equals to the parameter
     * @param columns
     */
    public static void moveLeft(int columns){
        out.print(ESC + Integer.toString(columns) + "D");
    }

    /**
     * This method places the cursor at the beginning of the selected square in the board
     * @param line The first coordinate of the square (line)
     * @param column The second coordinate of the square (column)
     */
    public static void selectCell(int line, int column){

        out.print(ESC + "H" + ESC + "1B");
        moveDown(2);
        moveRight(4);

        if(line > 1) moveDown(4*(line - 1));
        if(column > 1) moveRight(10* (column - 1));

        out.flush();

    }


    /**
     * This method draws the background of a square using the color given as a parameter
     * @param line The first coordinate of the square (line)
     * @param column The second coordinate of the square (column)
     * @param color The color of the background
     * @param levelColor The color of the number representing the level of the building
     * @param level The level of of the building
     */
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

    /**
     * This method draws a square containing a block, or a dome
     * @param line The first coordinate of the square (line)
     * @param column The second coordinate of the square (column)
     * @param background The background color of the square
     * @param color The color of the buildinng
     * @param levelColor The color of the number representing the level of the building
     * @param level The  leve lof the building
     */
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

    /**
     * It draws the TITLE of the game in the main Screen
     * @param mainColor The main Color
     * @param shadeColor The shade Color
     */
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

    /**
     * It draws the Starting Game Box in the Menu Screen
     */
    public static void drawStartGameBox(){
        System.out.println(
                "\t\t     ╔════════════════════════════════════════╗\n" +
                "\t\t     ║           (S) - START GAME             ║\n" +
                "\t\t     ╚════════════════════════════════════════╝");
    }

    /**
     * It draws the Credit Box in the Menu Screen
     */
    public static void drawCreditsGameBox(){
        System.out.println(
                "\t\t     ╔════════════════════════════════════════╗\n" +
                "\t\t     ║           (C) - CREDITS                ║\n" +
                "\t\t     ╚════════════════════════════════════════╝");
    }

    /**
     * It draws the Quit Game Box in the Menu Screen
     */
    public static void drawQuitGameBox(){
        System.out.println(
                "\t\t     ╔════════════════════════════════════════╗\n" +
                "\t\t     ║           (Q) - QUIT GAME              ║\n" +
                "\t\t     ╚════════════════════════════════════════╝");
    }

    /**
     * This method draws the victory message
     */
    public static void drawVictory(){
        out.print( Colors.RED_124 + " __      __ _        _                      ");            moveDown(1);    moveLeft(44);
        out.print( Colors.RED_124 + " \\ \\    / /(_)      | |                     ");          moveDown(1);    moveLeft(44);
        out.print( Colors.RED_196 + "  \\ \\  / /  _   ___ | |_  ___   _ __  _   _ ");          moveDown(1);    moveLeft(44);
        out.print( Colors.ORANGE_202 + "   \\ \\/ /  | | / __|| __|/ _ \\ | '__|| | | |");      moveDown(1);    moveLeft(44);
        out.print( Colors.ORANGE_208 + "    \\  /   | || (__ | |_| (_) || |   | |_| |");        moveDown(1);    moveLeft(44);
        out.print( Colors.ORANGE_214 + "     \\/    |_| \\___| \\__|\\___/ |_|    \\__, |");    moveDown(1);    moveLeft(44);
        out.print( Colors.YELLOW_220 + "                                       __/ |");         moveDown(1);    moveLeft(44);
        out.print( Colors.YELLOW_226 + "                                      |___/ ");
        out.print( Colors.RESET);
    }

    /**
     * This method draws the defeat message
     */
    public static void drawDefeat(){
        // Length 37
        out.print( Colors.BLUE_21 + "  _____          __              _   ");       moveDown(1);    moveLeft(37);
        out.print( Colors.BLUE_27 + " |  __ \\        / _|            | |  ");      moveDown(1);    moveLeft(37);
        out.print( Colors.BLUE_33 + " | |  | |  ___ | |_  ___   __ _ | |_ ");      moveDown(1);    moveLeft(37);
        out.print( Colors.BLUE_39 + " | |  | | / _ \\|  _|/ _ \\ / _` || __|");     moveDown(1);    moveLeft(37);
        out.print( Colors.BLUE_45 + " | |__| ||  __/| | |  __/| (_| || |_ ");       moveDown(1);    moveLeft(37);
        out.print( Colors.BLUE_51 + " |_____/  \\___||_|  \\___| \\__,_| \\__|");
        out.print( Colors.RESET);

        out.flush();
    }
}



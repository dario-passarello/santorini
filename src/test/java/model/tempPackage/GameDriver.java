package model.tempPackage;

import model.*;
import utils.Coordinate;

import java.util.ArrayList;
import java.util.List;

public class GameDriver {

    public static Player player1 = new Player("player1");
    public static Player player2 = new Player("player2");
    public static Game game = new Game(2);

    public static Board board = new Board();


    public static List<List<Square>> initialize(){
        ArrayList<Square> row;
        List<List<Square>> matrix = new ArrayList<>();

        Coordinate temp;

        for(int i = 1; i <=5; i++){
            row = new ArrayList<>();
            for(int j = 1; j <=5 ; j++){
                temp = new Coordinate(i, j);
                row.add(new Square(board, temp));
            }
            matrix.add(row);
        }

        return matrix;
    }

    public static void start() {
        board.setMatrix(initialize());
        game.getPlayers().add(player1);
        game.getPlayers().add(player2);
    }

    public static void setbuilder(int a, int b) {
        List<Square> column = board.getMatrix().get(a - 1);
        Square square = column.get(b - 1);

        Builder builder1 = new Builder(square, player1);
        square.setOccupant(builder1);
    }

    public static void setdome(int a, int b){
        List<Square> column = board.getMatrix().get(a - 1);
        Square square = column.get(b - 1);

        square.addDome();
    }

    public static Square getSquare (int a, int b){
        List<Square> column = board.getMatrix().get(a - 1);
        Square square = column.get(b - 1);

        return square;
    }
    }


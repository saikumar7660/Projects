package tictactoe.models;

import java.util.Scanner;

public class Player {

    private char symbol;
    private String name;
    private int id;
    private PlayerType playerType;

    private Scanner scanner;

    public Player(char symbol, String name, int id, PlayerType playerType) {
        this.symbol = symbol;
        this.name = name;
        this.id = id;
        this.playerType = playerType;
        scanner = new Scanner(System.in);
    }

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

    public void setPlayerType(PlayerType playerType) {
        this.playerType = playerType;
    }

    public Cell makeMove(Board board) {

        System.out.println(this.name + ", It's your turn to make the move, enter row and col");

        int row = scanner.nextInt();
        int col = scanner.nextInt();

        // validate if user entered row and col input is already filled or not

        while(validateMove(row,col,board) == false)
        {
            System.out.println(this.name+", Invalid Move, please try again");

             row = scanner.nextInt();
             col = scanner.nextInt();
        }

        // now we have valid move. Put that move in the board

        Cell cell = board.getBoard().get(row).get(col);
        cell.setCellState(CellState.FILLED);
        cell.setPlayer(this);
        return cell;
    }

    private boolean validateMove(int row, int col, Board board) {

        // row < 0 and col < 0 is used to check if seller entered negative value of row and col index

        if(row >= board.getDimension() || row<0)
                return false;
        if(col >= board.getDimension() || col<0)
                return false;

        // if board.getBoard().get(row).get(col) is not empty then we cannot put values in it

        if(!CellState.EMPTY.equals(board.getBoard().get(row).get(col).getCellState()))
        {
            return false;
        }

        return true;
    }
}

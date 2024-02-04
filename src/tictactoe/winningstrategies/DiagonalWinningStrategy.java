package tictactoe.winningstrategies;

import tictactoe.models.Board;
import tictactoe.models.Move;

import java.util.HashMap;
import java.util.Map;

public class DiagonalWinningStrategy implements WinningStrategy{


    Map<Character,Integer> leftDiagonalMap = new HashMap<>();
    Map<Character,Integer> rightDiagonalMap = new HashMap<>();

    @Override
    public boolean checkWinner(Board board, Move move) {
        int row = move.getCell().getRow();
        int col = move.getCell().getCol();

        char symbol = move.getPlayer().getSymbol();

        // check if cell is part of left diagonal

        if(row == col)
        {
            // check if this symbol is coming for first time in left diagonal

            if(!leftDiagonalMap.containsKey(symbol))
            {
                leftDiagonalMap.put(symbol,0);
            }
            leftDiagonalMap.put(symbol, leftDiagonalMap.get(symbol)+1);

            // check if count of current symbol is same as size of board

            if(board.getDimension() == leftDiagonalMap.get(symbol))
                return true;
        }


        // check if cell is part of right diagonal

        if((row+col) == board.getDimension() - 1)
        {
            if(!rightDiagonalMap.containsKey(symbol))
                rightDiagonalMap.put(symbol,0);

            rightDiagonalMap.put(symbol, rightDiagonalMap.get(symbol)+1);

            // check if count of current symbol is same as size of board

            if(board.getDimension() == rightDiagonalMap.get(symbol))
                return true;
        }

        return false;
    }

    @Override
    public void undo(Board board, Move lastMove) {

        int row = lastMove.getCell().getRow();
        int col = lastMove.getCell().getCol();
        char symbol = lastMove.getPlayer().getSymbol();

        if(row==col)
            leftDiagonalMap.put(symbol, leftDiagonalMap.get(symbol)-1);
        if((row+col) == board.getDimension()-1)
            rightDiagonalMap.put(symbol, rightDiagonalMap.get(symbol)-1);


    }
}

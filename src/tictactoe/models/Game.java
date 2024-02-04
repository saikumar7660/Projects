package tictactoe.models;

import tictactoe.exception.DuplicateSymbolException;
import tictactoe.exception.MoreThanOneBotException;
import tictactoe.exception.PlayerCountMismatchException;
import tictactoe.winningstrategies.WinningStrategy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Game {

    private List<Player> players;

    private Board board;
    private List<Move> moves;
    private Player winner;
    private GameState gameState;

    private int nextPlayerIndex;

    private List<WinningStrategy> winningStrategies;

    private Game(int dimension, List<Player> players, List<WinningStrategy> winningStrategies) {
        this.board = new Board(dimension);
        this.players = players;
        this.winningStrategies = winningStrategies;
        this.gameState = GameState.IN_PROGRESS;
        this.nextPlayerIndex = 0;
        this.moves = new ArrayList<>();
    }

    public static Builder getBuilder()
    {
        return new Builder();
    }

    public void printBoard() {
        board.printBoard();
    }

    public void makeMove() {

        Player player = players.get(nextPlayerIndex);
        Cell cell = player.makeMove(board);

        Move move = new Move(cell, player);
        moves.add(move);

        if(checkWinner(move, board))
        {
            gameState = GameState.SUCCESS;
            winner = player;
            return;
        }
        // if there is no winner in above method, then we check for draw
        // no. of moves in a board will be square of size of board
        if(moves.size() ==  board.getDimension() * board.getDimension()){
            gameState = GameState.DRAW;
            return;
        }


        // if above both IF loops are false then we increase nextplayerindex to let another player make move

        nextPlayerIndex++;
        // if there are 5 players and if nextplayerindex++ becomes 6 then we need to reset nextplayerindex to first player
        // by using below formula

        nextPlayerIndex = nextPlayerIndex % players.size();

    }

    private boolean checkWinner(Move move, Board board) {

        for(WinningStrategy winningStrategy : winningStrategies )
        {
            if(winningStrategy.checkWinner(board, move)) {
                return true;
            }
        }
        return false;
    }

    public void undo() {

        if(moves.size()==0){
            System.out.println("No Moves to Undo");
            return;
        }
        Move lastMove = moves.get(moves.size() - 1);
        moves.remove(lastMove);

        // after removing last move, now update Cell correspondingly

        Cell cell = lastMove.getCell();
        cell.setCellState(CellState.EMPTY);
        cell.setPlayer(null);

        for(WinningStrategy winningStrategy : winningStrategies)
        {
            winningStrategy.undo(board,lastMove);
        }


        if(nextPlayerIndex != 0)
                nextPlayerIndex--;
        else
                nextPlayerIndex = players.size()-1;
    }

    public static class Builder{
        private List<Player> players;
        private List<WinningStrategy> winningStrategies;
        private int dimension;

        private Builder() {
            this.players = new ArrayList<>();
            this.winningStrategies = new ArrayList<>();
            this.dimension = 0;
        }


        public Builder setPlayers(List<Player> players) {
            this.players = players;
            return this;
        }

        public Game build() throws MoreThanOneBotException, DuplicateSymbolException, PlayerCountMismatchException {
            //validations
            //validate bot count
            //validate symbol
            //validate dimension and player count

            validateBotCount();
            validateSymbol();
            validateDimensionAndPlayerCount();

            return new Game(dimension,players, winningStrategies);
        }

        private void validateDimensionAndPlayerCount() throws PlayerCountMismatchException {
            if(players.size() != dimension-1)
            {
                throw new PlayerCountMismatchException();
            }
        }

        private void validateSymbol() throws DuplicateSymbolException {
            Set<Character> symbols = new HashSet<>();
            for(Player player: players)
            {
                if(symbols.contains(player.getSymbol()))
                    throw new DuplicateSymbolException();
                else
                    symbols.add(player.getSymbol());
            }
        }

        private void validateBotCount() throws MoreThanOneBotException {
            int botCount=0;
            for(Player player:players)
            {
                if(player.getPlayerType().equals(PlayerType.BOT))
                    botCount++;
            }
            if(botCount>1)
            {
                throw new MoreThanOneBotException();
            }
        }

        public Builder setWinningStrategies(List<WinningStrategy> winningStrategies) {
            this.winningStrategies = winningStrategies;
            return this;
        }

        public Builder setDimension(int dimension) {
            this.dimension = dimension;
            return this;
        }
    }
    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public int getNextPlayerIndex() {
        return nextPlayerIndex;
    }

    public void setNextPlayerIndex(int nextPlayerIndex) {
        this.nextPlayerIndex = nextPlayerIndex;
    }
}

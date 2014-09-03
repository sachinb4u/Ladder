package com.sachin.game;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SachinBhosale on 7/12/2014.
 */
public class GameController {

    private static final SecureRandom random = new SecureRandom();
    private final GameBoard gameBoard;
    private List<Player> players;

    /**
     * Initialize GameController for GameBoard
     * @param gameBoard GameBoard
     */
    public GameController(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    /**
     * Get GameBoard
     * @return GameBoard
     */
    public GameBoard getGameBoard() {
        return this.gameBoard;
    }

    /**
     * Get the players of the game with default names
     * @return players
     */
    public List<Player> getPlayers() {
        if (this.players == null) {
            this.players = createNewPlayers();
        }
        return this.players;
    }

    /**
     * Create new Players with default name and initial state
     * and as per GameBoard configuration
     * @return players
     */
    private List<Player> createNewPlayers() {
        int noOfPlayers = gameBoard.getNoOfPlayers();

        List<Player> playersNew = new ArrayList<>();

        for (int i = 0; i < noOfPlayers; i++) {
            Player gamePlayer = new Player(this);
            gamePlayer.setName("Player" + (i + 1));
            playersNew.add(gamePlayer);
        }
        return playersNew;
    }

    /**
     * Reset player state to initial position
     */
    public void resetPlayers() {
        this.players = createNewPlayers();
    }

    /**
     * Get the next cell number based on current position and dicevalue
     * @param currentCell cell number
     * @param diceValue dice value
     * @return nextCell
     */
    public int getNextMove(int currentCell, int diceValue) {
        assert (diceValue > 0  && diceValue < 7) : "Invalid DiceValue";

        int nextCell = currentCell + diceValue;

        if (gameBoard.isSnakeBite(nextCell)) {
            nextCell = gameBoard.getSnakeForCell(nextCell);
        }

        if (gameBoard.isLadderJump(nextCell)) {
            nextCell = gameBoard.getLadderForCell(nextCell);
        }

        /**
         * for winning exact number is required.
         */
        if (nextCell > gameBoard.getWinningCell()) {
            return currentCell;
        }

        return nextCell;
    }

    /**
     * Returns a pseudorandom, uniformly distributed {@code int} value
     * between 1 (inclusive) and 6 (inclusive)
     * @return int
     */
    public int rollDice() {
        int randomInt;
        do {
            randomInt = random.nextInt(7);
        } while (0 == randomInt);

        return randomInt;
    }
}

package com.sachin.game.impl;

import com.sachin.game.api.GameBoard;
import com.sachin.game.api.GameController;
import com.sachin.game.api.Player;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SachinBhosale on 7/12/2014.
 */
public class GameControllerImpl implements GameController {

    private static final SecureRandom random = new SecureRandom();
    private final GameBoard gameBoard;
    private List<Player> players;

    public GameControllerImpl(GameBoard configuration) {
        this.gameBoard = configuration;
    }

    @Override
    public GameBoard getGameBoard() {
        return this.gameBoard;
    }

    @Override
    public void displayBoard() {

    }

    @Override
    public List<Player> getPlayers() {

        if (this.players == null) {
            this.players = createNewPlayers();
        }
        return this.players;
    }

    private List<Player> createNewPlayers() {
        int noOfPlayers = gameBoard.getNoOfPlayers();

        List<Player> playersNew = new ArrayList<Player>();

        for (int i = 0; i < noOfPlayers; i++) {
            Player gamePlayer = new PlayerImpl(this);
            gamePlayer.setName("Player" + (i + 1));
            playersNew.add(gamePlayer);
        }

        return playersNew;
    }

    @Override
    public void resetPlayers() {
        this.players = createNewPlayers();
    }

    @Override
    public int getNextMove(int currentCell, int diceValue) {
        if (diceValue < 1 || diceValue > 6) {
            throw new IllegalArgumentException("DiceValue cannot be greater than 6 and less than 1");
        }

        int nextCell = currentCell + diceValue;


        if (gameBoard.isSnakeBite(nextCell)) {
            int snake = gameBoard.getSnakeForCell(nextCell);

            nextCell = snake;
        }

        if (gameBoard.isLadderJump(nextCell)) {
            int ladder = gameBoard.getLadderForCell(nextCell);

            nextCell = ladder;
        }

        /**
         * for winning exact number is required.
         */
        if (nextCell > gameBoard.getMaxCell()) {
            return currentCell;
        }

        return nextCell;
    }

    /**
     * Returns a pseudorandom, uniformly distributed {@code int} value
     * between 1 (inclusive) and 6 (inclusive)
     *
     * @return int
     */
    @Override
    public int rollDice() {
        int randomInt;
        do {
            randomInt = random.nextInt(7);
        } while (0 == randomInt);

        return randomInt;
    }
}

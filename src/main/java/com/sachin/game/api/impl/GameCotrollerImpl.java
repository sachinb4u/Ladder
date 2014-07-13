package com.sachin.game.api.impl;

import com.sachin.game.api.DiceRoller;
import com.sachin.game.api.GameController;
import com.sachin.game.api.GameRule;
import com.sachin.game.api.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by C5203803 on 7/12/2014.
 */
public class GameCotrollerImpl implements GameController {

    private Set<Cell> boardCells = new HashSet<Cell>();
    private DiceRoller diceRoller;

    private final GameConfiguration gameConfiguration;
    private final GameRule gameRule;

    private List<Player> players;

    public GameCotrollerImpl(GameConfiguration configuration) {

        this.gameConfiguration = configuration;

        int totalCells = configuration.getRows() * configuration.getColumns();

        for (int i =0; i < totalCells ; i++){
            Cell cell = new Cell(i+1);

            boardCells.add(cell);
        }

        this.diceRoller = new RandomDiceRoller();
        this.gameRule = new GameRuleImpl(gameConfiguration);
    }

    @Override
    public GameConfiguration getGameConfiguration() {
        return this.gameConfiguration;
    }

    @Override
    public void displayBoard() {

    }

    @Override
    public List<Player> getPlayers() {

        if(this.players == null) {
           this.players = createNewPlayers();
        }
        return this.players;
    }

    private List<Player> createNewPlayers() {
        int noOfPlayers = gameConfiguration.getNoOfPlayers();

        List<Player> playersNew = new ArrayList<Player>();

        for (int i = 0; i < noOfPlayers; i++) {
            GamePlayer gamePlayer = new GamePlayer(this);
            gamePlayer.setName("Player" + (i+1));
            playersNew.add(gamePlayer);
        }

        return playersNew;
    }

    @Override
    public int rollDice() {
        return diceRoller.rollDice();
    }

    @Override
    public GameRule getGameRule() {
        return gameRule;
    }

    @Override
    public void resetPlayers() {
        this.players = createNewPlayers();
    }
}

package com.sachin.game.api.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by C5203803 on 7/11/2014.
 */
public class GameConfiguration {

    private int rows;
    private int columns;
    private List<Ladder> ladders;
    private List<Snake> snakes;

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public List<Ladder> getLadders() {
        return ladders;
    }

    public List<Snake> getSnakes() {
        return snakes;
    }

    public GameConfiguration getConfiguration(GameConfigurationBuilder builder){

        GameConfiguration gameConfiguration = new GameConfiguration();

        gameConfiguration.rows = builder.rows;
        gameConfiguration.columns = builder.columns;
        gameConfiguration.ladders = new ArrayList<>(builder.ladders);
        gameConfiguration.snakes = new ArrayList<>(builder.snakes);

        return  gameConfiguration;
    }

    public GameConfiguration getDefaultConfiguration(){

        GameConfiguration defaultGameConfiguration = new GameConfiguration();

        defaultGameConfiguration.rows = 10;
        defaultGameConfiguration.columns = 10;
        defaultGameConfiguration.ladders = new ArrayList<>();
        defaultGameConfiguration.snakes = new ArrayList<>();

        return  defaultGameConfiguration;
    }

    public static class GameConfigurationBuilder {

        private int rows;
        private int columns;
        private List<Ladder> ladders;
        private List<Snake> snakes;

        public int getRows() {
            return rows;
        }

        public void setRows(int rows) {
            this.rows = rows;
        }

        public int getColumns() {
            return columns;
        }

        public void setColumns(int columns) {
            this.columns = columns;
        }

        public List<Ladder> getLadders() {
            return ladders;
        }

        public void setLadders(List<Ladder> ladders) {
            this.ladders = ladders;
        }

        public List<Snake> getSnakes() {
            return snakes;
        }

        public void setSnakes(List<Snake> snakes) {
            this.snakes = snakes;
        }
    }
}

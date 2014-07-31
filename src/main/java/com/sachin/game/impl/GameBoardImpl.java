package com.sachin.game.impl;

import com.sachin.game.api.GameBoard;

import java.util.*;

/**
 * Created by SachinBhosale on 7/11/2014.
 *
 * GameBoard contains Snake and Ladder Game configuration properties
 *
 * ImmutableClass
 *
 * Update 25/07/14: final removed for test stubbing
 */
public class GameBoardImpl implements GameBoard {

    private static final ResourceBundle configBundle = ResourceBundle.getBundle("config");

    public enum ConfigConstants {
        rows, columns, ladders, snakes, noOfPlayers
    }

    private int rows;
    private int columns;
    private Map<Integer, Integer> ladders;
    private Map<Integer, Integer> snakes;
    private int noOfPlayers;
    private int maxCell;

    @Override
    public int getRows() {
        return rows;
    }

    @Override
    public int getColumns() {
        return columns;
    }

    @Override
    public Map<Integer, Integer> getLadders() {
        return ladders;
    }

    @Override
    public Map<Integer, Integer> getSnakes() {
        return snakes;
    }

    @Override
    public int getNoOfPlayers() {
        return noOfPlayers;
    }

    @Override
    public int getLadderForCell(int cell){

        return ladders.containsKey(cell) ?  ladders.get(cell) : -1 ;
    }

    @Override
    public int getSnakeForCell(int cell){

        return snakes.containsKey(cell) ?  snakes.get(cell) : -1 ;
    }

    @Override
    public int getMaxCell() {
        return maxCell;
    }

    @Override
    public boolean isSnakeBite(int cell) {
        int snake = getSnakeForCell(cell);
        return snake == -1 ? false : true;
    }

    @Override
    public boolean isLadderJump(int cell) {
        int ladder = getLadderForCell(cell);
        return ladder == -1 ? false : true;
    }
    /**
     * All GameBoard instances should come from GameConfigurationBuilder
     */
    private GameBoardImpl(){
        // no default public constructor.
    }

    /**
     * Builder class for building GameBoard
     *
     */
    public static final class GameConfigurationBuilder {

        private int rows;
        private int columns;
        private Map<Integer, Integer> ladders = new HashMap<>();
        private Map<Integer, Integer> snakes = new HashMap<>();
        private int noOfPlayers;

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

        public void removeLadder(int from, int to) {
            this.ladders.remove(from);
        }

        public void addLadder(int from, int to) {
            this.ladders.put(from, to);
        }

        public void removeSnake(int from, int to) {
            snakes.remove(from);
        }

        public void addSnake(int from, int to) {
            this.snakes.put(from, to);
        }

        public int getNoOfPlayers() {
            return noOfPlayers;
        }

        public void setNoOfPlayers(int noOfPlayers) {
            this.noOfPlayers = noOfPlayers;
        }

        /**
         * Get configuration building on GameConfigurationBuilder values
         *
         * @throws java.lang.IllegalStateException
         * @return GameBoard
         */
        public GameBoardImpl buildCongifuration(){
            GameBoardImpl gameBoard = new GameBoardImpl();

            gameBoard.rows = this.rows;
            gameBoard.columns = this.columns;
            gameBoard.ladders = new HashMap<>(ladders);
            gameBoard.snakes = new HashMap<>(snakes);
            gameBoard.maxCell = this.rows * this.columns;
            gameBoard.noOfPlayers = this.noOfPlayers;

            validateGameConfiguration(gameBoard);

            return gameBoard;
        }

        /**
         * Get default configuration based on properties set in config.properties
         *
         * @throws java.lang.IllegalStateException
         * @return GameBoard
         */
        public static GameBoardImpl getDefaultGameConfiguration(){

            GameConfigurationBuilder builder = new GameConfigurationBuilder();

            builder.setRows(Integer.parseInt(configBundle.getString(ConfigConstants.rows.name())));
            builder.setColumns(Integer.parseInt(configBundle.getString(ConfigConstants.columns.name())));
            builder.ladders = getDefaultLadders();
            builder.snakes = getDefaultSnakes();
            builder.setNoOfPlayers(Integer.parseInt(configBundle.getString(ConfigConstants.noOfPlayers.name())));

            GameBoardImpl defaultGameBoard = builder.buildCongifuration();

            validateGameConfiguration(defaultGameBoard);

            return defaultGameBoard;
        }

        /**
         * Validate GameBoard before returning the instance.
         *
         * @throws IllegalStateException
         * @param configuration
         */
        private static void validateGameConfiguration(GameBoardImpl configuration) {
            if(configuration.rows < 1 || configuration.rows > 99){
                throw new IllegalStateException("Rows number should be between 1 and 99");
            }

            if(configuration.columns < 1 || configuration.columns > 99){
                throw new IllegalStateException("Columns number should be between 1 and 99");
            }

            if(configuration.maxCell > 999){
                throw new IllegalStateException("Cells greater than 999 are not supported");
            }

            if(configuration.noOfPlayers > 5){
                throw new IllegalStateException("Game players more than 5 are not supported");
            }

            if(configuration.ladders.keySet().contains(configuration.snakes.keySet())){
                throw new IllegalStateException("Ladder and Snake cannot have at the same cell");
            }
        }


        /**
         * Read ladders property from config bundle
         *
         * @return
         */
        private static Map<Integer, Integer> getDefaultLadders() {
            String ladderString = configBundle.getString(ConfigConstants.ladders.name());
            StringTokenizer tokenizer = new StringTokenizer(ladderString, ",");
            Map<Integer, Integer> ladderMap = new HashMap<Integer, Integer>();

            while(tokenizer.hasMoreTokens()){
                String token = tokenizer.nextToken();
                String[] cells = token.split(":");

                ladderMap.put(Integer.valueOf(cells[0]), Integer.valueOf(cells[1]));
            }
            return ladderMap;
        }

        /**
         * Read snakes property from config bundle
         *
         * @return
         */
        private static Map<Integer, Integer> getDefaultSnakes() {
            String snakesStr = configBundle.getString(ConfigConstants.snakes.name());

            StringTokenizer tokenizer = new StringTokenizer(snakesStr, ",");

            Map<Integer, Integer> snakeMap = new HashMap<Integer, Integer>();
            while(tokenizer.hasMoreTokens()){
                String token = tokenizer.nextToken();
                String[] cells = token.split(":");

                snakeMap.put(Integer.valueOf(cells[0]), Integer.valueOf(cells[1]));
            }
            return snakeMap;
        }
    }


    @Override
    public String toString() {
        return "GameBoard{" +
                "rows=" + rows +
                ", columns=" + columns +
                ", ladders=" + ladders.values() +
                ", snakes=" + snakes.values() +
                ", noOfPlayers=" + noOfPlayers +
                ", maxCell=" + maxCell +
                '}';
    }
}

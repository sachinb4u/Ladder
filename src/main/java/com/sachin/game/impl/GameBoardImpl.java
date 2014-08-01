package com.sachin.game.impl;

import com.sachin.game.api.GameBoard;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

/**
 * Created by SachinBhosale on 7/11/2014.
 * <p/>
 * GameBoard contains Snake and Ladder Game configuration properties
 * <p/>
 * ImmutableClass
 * <p/>
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
    public int getLadderForCell(int cell) {

        return ladders.containsKey(cell) ? ladders.get(cell) : -1;
    }

    @Override
    public int getSnakeForCell(int cell) {

        return snakes.containsKey(cell) ? snakes.get(cell) : -1;
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
    private GameBoardImpl() {
        // no default public constructor.
    }

    /**
     * Builder class for building GameBoard
     */
    public static final class GameBoardBuilder {

        private int rows;
        private int columns;
        private Map<Integer, Integer> ladders1 = new HashMap<>();
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
            this.ladders1.remove(from);
        }

        public void addLadder(int from, int to) {
            this.ladders1.put(from, to);
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
         * Copy constructor
         *
         * @param builder
         */
        public GameBoardBuilder(GameBoardBuilder builder) {
            this.rows = builder.rows;
            this.columns = builder.columns;
            this.ladders1 = new HashMap<>(builder.ladders1);
            this.snakes = new HashMap<>(builder.snakes);
            this.noOfPlayers = builder.noOfPlayers;
        }

        public GameBoardBuilder() {

        }

        /**
         * Get configuration building on GameConfigurationBuilder values
         *
         * @return GameBoard
         * @throws java.lang.IllegalStateException
         */
        public GameBoardImpl buildGame() {
            GameBoardImpl gameBoard = new GameBoardImpl();

            gameBoard.rows = this.rows;
            gameBoard.columns = this.columns;
            gameBoard.ladders = new HashMap<>(ladders1);
            gameBoard.snakes = new HashMap<>(snakes);
            gameBoard.maxCell = this.rows * this.columns;
            gameBoard.noOfPlayers = this.noOfPlayers;

            validateGameConfiguration(gameBoard);

            return gameBoard;
        }

        /**
         * Get default configuration based on properties set in config.properties
         *
         * @return GameBoard
         * @throws java.lang.IllegalStateException
         */
        public static GameBoardImpl getDefaultGameConfiguration() {

            GameBoardBuilder builder = new GameBoardBuilder();

            builder.setRows(Integer.parseInt(configBundle.getString(ConfigConstants.rows.name())));
            builder.setColumns(Integer.parseInt(configBundle.getString(ConfigConstants.columns.name())));
            builder.ladders1 = getDefaultLadders();
            builder.snakes = getDefaultSnakes();
            builder.setNoOfPlayers(Integer.parseInt(configBundle.getString(ConfigConstants.noOfPlayers.name())));

            GameBoardImpl defaultGameBoard = builder.buildGame();

            validateGameConfiguration(defaultGameBoard);

            return defaultGameBoard;
        }

        /**
         * Validate GameBoard before returning the instance.
         *
         * @param board
         * @throws IllegalStateException
         */
        private static void validateGameConfiguration(GameBoardImpl board) {
            if (board.rows < 1 || board.rows > 99) {
                throw new IllegalStateException("Rows number should be between 1 and 99");
            }

            if (board.columns < 1 || board.columns > 99) {
                throw new IllegalStateException("Columns number should be between 1 and 99");
            }

            if (board.maxCell > 999) {
                throw new IllegalStateException("Cells greater than 999 are not supported");
            }

            if (board.noOfPlayers > 5) {
                throw new IllegalStateException("Game players more than 5 are not supported");
            }

            if (board.ladders.keySet().contains(board.snakes.keySet())) {
                throw new IllegalStateException("Ladder and Snake cannot have at the same cell");
            }

            if (board.ladders.containsKey(0) || board.ladders.containsKey(board.columns * board.rows)) {
                throw new IllegalStateException("Ladder cannot be present from initial cell and winning cell");
            }
            if (board.snakes.containsKey(0) || board.snakes.containsKey(board.columns * board.rows)) {
                throw new IllegalStateException("Snake cannot be present from initial cell and winning cell");
            }
        }


        /**
         * Read ladders1 property from config bundle
         *
         * @return
         */
        private static Map<Integer, Integer> getDefaultLadders() {
            String ladderString = configBundle.getString(ConfigConstants.ladders.name());
            StringTokenizer tokenizer = new StringTokenizer(ladderString, ",");
            Map<Integer, Integer> ladderMap = new HashMap<Integer, Integer>();

            while (tokenizer.hasMoreTokens()) {
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
            while (tokenizer.hasMoreTokens()) {
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
                ", ladders1=" + ladders.values() +
                ", snakes=" + snakes.values() +
                ", noOfPlayers=" + noOfPlayers +
                ", maxCell=" + maxCell +
                '}';
    }
}

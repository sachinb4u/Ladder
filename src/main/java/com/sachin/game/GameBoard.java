package com.sachin.game;

import java.util.*;

/**
 * Created by SachinBhosale on 7/11/2014.
 * <p/>
 * GameBoard contains Snake and Ladder Game configuration properties
 * <p/>
 * ImmutableClass
 * <p/>
 * Update 25/07/14: final removed for test stubbing
 */
public class GameBoard {

    private static final ResourceBundle configBundle = ResourceBundle.getBundle("config");
    private static final int NOT_FOUND = -1;

    private int rows;
    private int columns;
    private Map<Integer, Integer> ladders;
    private Map<Integer, Integer> snakes;
    private int noOfPlayers;
    private int winningCell;

    /**
     * All GameBoard instances should come from GameBoardBuilder
     */
    private GameBoard() {
        // no default public constructor.
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    /**
     * Return unmodifiable map
     * @return
     */
    public Map<Integer, Integer> getLadders() {
        return Collections.unmodifiableMap(ladders);
    }

    /**
     * Return unmodifiable map
     * @return
     */
    public Map<Integer, Integer> getSnakes() {
        return Collections.unmodifiableMap(snakes);
    }

    public int getNoOfPlayers() {
        return noOfPlayers;
    }

    public int getLadderForCell(int cell) {
        return ladders.containsKey(cell) ? ladders.get(cell) : -1;
    }

    public int getSnakeForCell(int cell) {

        return snakes.containsKey(cell) ? snakes.get(cell) : NOT_FOUND;
    }

    public int getWinningCell() {
        return winningCell;
    }

    /**
     * Check if current cell has snake-bite
     * @param cell
     * @return
     */
    public boolean isSnakeBite(int cell) {
        int snake = getSnakeForCell(cell);
        return snake != NOT_FOUND;
    }

    /**
     * Check if current cell has ladder-jump
     * @param cell
     * @return
     */
    public boolean isLadderJump(int cell) {
        int ladder = getLadderForCell(cell);
        return ladder != NOT_FOUND;
    }

    public String toString() {
        return "GameBoard{" +
                "rows=" + rows +
                ", columns=" + columns +
                ", ladders1=" + ladders.values() +
                ", snakes=" + snakes.values() +
                ", noOfPlayers=" + noOfPlayers +
                ", winningCell=" + winningCell +
                '}';
    }

    public enum ConfigConstants {
        rows, columns, ladders, snakes, noOfPlayers
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

        /**
         * Copy constructor
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
         * Get default configuration based on properties set in config.properties
         * @return GameBoard
         * @throws java.lang.IllegalStateException
         */
        public static GameBoard getDefaultGameConfiguration() {

            GameBoardBuilder builder = new GameBoardBuilder();

            builder.setRows(Integer.parseInt(configBundle.getString(ConfigConstants.rows.name())));
            builder.setColumns(Integer.parseInt(configBundle.getString(ConfigConstants.columns.name())));
            builder.ladders1 = getDefaultLadders();
            builder.snakes = getDefaultSnakes();
            builder.setNoOfPlayers(Integer.parseInt(configBundle.getString(ConfigConstants.noOfPlayers.name())));

            GameBoard defaultGameBoard = builder.buildGame();

            validateGameConfiguration(defaultGameBoard);

            return defaultGameBoard;
        }

        /**
         * Validate GameBoard before returning the instance.
         *
         * @param board
         * @throws IllegalStateException
         */
        private static void validateGameConfiguration(GameBoard board) {
            if (board.rows < 1 || board.rows > 99) {
                throw new IllegalStateException("Rows number should be between 1 and 99");
            }

            if (board.columns < 1 || board.columns > 99) {
                throw new IllegalStateException("Columns number should be between 1 and 99");
            }

            if (board.winningCell > 999) {
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
         * @return
         */
        private static Map<Integer, Integer> getDefaultLadders() {
            String ladderString = configBundle.getString(ConfigConstants.ladders.name());
            StringTokenizer tokenizer = new StringTokenizer(ladderString, ",");
            Map<Integer, Integer> ladderMap = new HashMap<>();

            while (tokenizer.hasMoreTokens()) {
                String token = tokenizer.nextToken();
                String[] cells = token.split(":");

                ladderMap.put(Integer.valueOf(cells[0]), Integer.valueOf(cells[1]));
            }
            return ladderMap;
        }

        /**
         * Read snakes property from config bundle
         * @return
         */
        private static Map<Integer, Integer> getDefaultSnakes() {
            String snakesStr = configBundle.getString(ConfigConstants.snakes.name());

            StringTokenizer tokenizer = new StringTokenizer(snakesStr, ",");

            Map<Integer, Integer> snakeMap = new HashMap<>();
            while (tokenizer.hasMoreTokens()) {
                String token = tokenizer.nextToken();
                String[] cells = token.split(":");

                snakeMap.put(Integer.valueOf(cells[0]), Integer.valueOf(cells[1]));
            }
            return snakeMap;
        }

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
         * Get configuration building on GameConfigurationBuilder values
         *
         * @return GameBoard
         * @throws java.lang.IllegalStateException
         */
        public GameBoard buildGame() {
            GameBoard gameBoard = new GameBoard();

            gameBoard.rows = this.rows;
            gameBoard.columns = this.columns;
            gameBoard.ladders = new HashMap<>(ladders1);
            gameBoard.snakes = new HashMap<>(snakes);
            gameBoard.winningCell = this.rows * this.columns;
            gameBoard.noOfPlayers = this.noOfPlayers;

            validateGameConfiguration(gameBoard);

            return gameBoard;
        }
    }
}

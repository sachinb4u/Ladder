package com.sachin.game.api.beans;

import com.sachin.game.api.util.ConfigConstants;

import java.util.*;

/**
 * Created by SachinBhosale on 7/11/2014.
 *
 * GameConfiguration contains Snake and Ladder Game configuration properties
 *
 * @ImmutableClass
 */
public final class GameConfiguration {

    private static final ResourceBundle configBundle = ResourceBundle.getBundle("config");

    private int rows;
    private int columns;
    private Map<Integer, Ladder> ladderMap;
    private Map<Integer, Snake> snakeMap;
    private int noOfPlayers;
    private int maxCell;

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public List<Ladder> getLadders() {
        return new ArrayList<Ladder>(ladderMap.values());
    }

    public List<Snake> getSnakes() {
        return new ArrayList<Snake>(snakeMap.values());
    }

    public int getNoOfPlayers() {
        return noOfPlayers;
    }

    public Ladder getLadderForCell(Cell cell){

        return ladderMap.get(cell.getNumber());
    }

    public Snake getSnakeForCell(Cell cell){

        return snakeMap.get(cell.getNumber());
    }

    public int getMaxCell() {
        return maxCell;
    }

    /**
     * All GameConfiguration instances should come from GameConfigurationBuilder
     */
    private GameConfiguration(){
        // no default public constructor.
    }

    /**
     * Builder class for building GameConfiguration
     *
     */
    public static final class GameConfigurationBuilder {

        private int rows;
        private int columns;
        private List<Ladder> ladders = new ArrayList<Ladder>();
        private List<Snake> snakes = new ArrayList<Snake>();
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

        public boolean removeLadder(Ladder ladder) {
            return ladders.remove(ladder);
        }

        public void addLadder(Ladder ladder) {
            this.ladders.add(ladder);
        }

        public boolean removeSnake(Snake snake) {
            return snakes.remove(snake);
        }

        public void addSnake(Snake snake) {
            this.snakes.add(snake);
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
         * @return GameConfiguration
         */
        public GameConfiguration buildCongifuration(){
            GameConfiguration gameConfiguration = new GameConfiguration();

            gameConfiguration.rows = this.rows;
            gameConfiguration.columns = this.columns;
            gameConfiguration.ladderMap =getLadderMapFromList(this.ladders);
            gameConfiguration.snakeMap = getSnakesMapFromList(this.snakes);
            gameConfiguration.maxCell = this.rows * this.columns;
            gameConfiguration.noOfPlayers = this.noOfPlayers;

            validateGameConfiguration(gameConfiguration);

            return  gameConfiguration;
        }

        /**
         * Get default configuration based on properties set in config.properties
         *
         * @throws java.lang.IllegalStateException
         * @return GameConfiguration
         */
        public static GameConfiguration getDefaultGameConfiguration(){

            GameConfigurationBuilder builder = new GameConfigurationBuilder();

            builder.setRows(Integer.parseInt(configBundle.getString(ConfigConstants.rows.name())));
            builder.setColumns(Integer.parseInt(configBundle.getString(ConfigConstants.columns.name())));
            builder.ladders = getDefaultLadders();
            builder.snakes = getDefaultSnakes();
            builder.setNoOfPlayers(Integer.parseInt(configBundle.getString(ConfigConstants.noOfPlayers.name())));

            GameConfiguration defaultGameConfiguration = builder.buildCongifuration();

            validateGameConfiguration(defaultGameConfiguration);

            return  defaultGameConfiguration;
        }

        /**
         * Validate GameConfiguration before returning the instance.
         *
         * @throws IllegalStateException
         * @param configuration
         */
        private static void validateGameConfiguration(GameConfiguration configuration) {
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

            if(configuration.ladderMap.keySet().contains(configuration.snakeMap.keySet())){
                throw new IllegalStateException("Ladder and Snake cannot have at the same cell");
            }
        }


        /**
         * Read ladders property from config bundle
         *
         * @return
         */
        private static List<Ladder> getDefaultLadders() {
            String ladderString = configBundle.getString(ConfigConstants.ladders.name());
            StringTokenizer tokenizer = new StringTokenizer(ladderString, ",");
            List<Ladder> ladderList = new ArrayList<Ladder>();
            Map<Integer, Ladder> ladderMap = new HashMap<Integer, Ladder>();

            while(tokenizer.hasMoreTokens()){
                String token = tokenizer.nextToken();
                String[] cells = token.split(":");

                Ladder ladder = new Ladder(Integer.parseInt(cells[0]), Integer.parseInt(cells[1]));
                ladderList.add(ladder);

                ladderMap.put(ladder.getFromCell().getNumber(), ladder);
            }
            return ladderList;
        }

        /**
         * Read snakes property from config bundle
         *
         * @return
         */
        private static List<Snake> getDefaultSnakes() {
            String snakesStr = configBundle.getString(ConfigConstants.snakes.name());

            StringTokenizer tokenizer = new StringTokenizer(snakesStr, ",");

            List<Snake> snakesList = new ArrayList<Snake>();
            Map<Integer, Snake> snakeMap = new HashMap<Integer, Snake>();
            while(tokenizer.hasMoreTokens()){
                String token = tokenizer.nextToken();
                String[] cells = token.split(":");

                Snake snake = new Snake(Integer.parseInt(cells[0]), Integer.parseInt(cells[1]));
                snakesList.add(snake);
                snakeMap.put(snake.getFromCell().getNumber(), snake);
            }
            return snakesList;
        }

        /**
         * Convert ladders list to map of (fromCellNumber, Ladder)
         *
         * @param list
         * @return
         */
        private static Map<Integer, Ladder> getLadderMapFromList(List<Ladder> list){
            Map<Integer, Ladder> ladderMap = new HashMap<Integer, Ladder>();

            for(Ladder ladder :list){
                ladderMap.put(ladder.getFromCell().getNumber(), ladder);
            }

            return ladderMap;
        }

        /**
         * Convert snakes list to map of (fromCellNumber, Snake)
         *
         * @param list
         * @return
         */
        private static Map<Integer, Snake> getSnakesMapFromList(List<Snake> list){
            Map<Integer, Snake> snakeMap = new HashMap<Integer, Snake>();

            for(Snake snake :list){
                snakeMap.put(snake.getFromCell().getNumber(), snake);
            }

            return snakeMap;
        }
    }
}

package com.sachin.game.api.impl;

import java.util.*;

/**
 * Created by C5203803 on 7/11/2014.
 *
 * Immutable class
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

    public static class GameConfigurationBuilder {

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

        public int getNoOfPlayers() {
            return noOfPlayers;
        }

        public void setNoOfPlayers(int noOfPlayers) {
            this.noOfPlayers = noOfPlayers;
        }

        public GameConfiguration buildCongifuration(){
            GameConfiguration gameConfiguration = new GameConfiguration();

            gameConfiguration.rows = this.rows;
            gameConfiguration.columns = this.columns;
            gameConfiguration.ladderMap =getLadderMapFromList(this.ladders);
            gameConfiguration.snakeMap = getSnakesMapFromList(this.snakes);
            gameConfiguration.maxCell = this.rows * this.columns;
            gameConfiguration.noOfPlayers = this.noOfPlayers;

            return  gameConfiguration;
        }

        public static GameConfiguration getDefaultGameConfiguration(){

            GameConfigurationBuilder builder = new GameConfigurationBuilder();

            builder.setRows(Integer.parseInt(configBundle.getString(ConfigConstants.rows.name())));
            builder.setColumns(Integer.parseInt(configBundle.getString(ConfigConstants.columns.name())));
            builder.setLadders(getDefaultLadders());
            builder.setSnakes(getDefaultSnakes());
            builder.setNoOfPlayers(Integer.parseInt(configBundle.getString(ConfigConstants.noOfPlayers.name())));

            GameConfiguration defaultGameConfiguration = builder.buildCongifuration();

            return  defaultGameConfiguration;
        }

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

        private static Map<Integer, Ladder> getLadderMapFromList(List<Ladder> list){
            Map<Integer, Ladder> ladderMap = new HashMap<Integer, Ladder>();

            for(Ladder ladder :list){
                ladderMap.put(ladder.getFromCell().getNumber(), ladder);
            }

            return ladderMap;
        }

        private static Map<Integer, Snake> getSnakesMapFromList(List<Snake> list){
            Map<Integer, Snake> snakeMap = new HashMap<Integer, Snake>();

            for(Snake snake :list){
                snakeMap.put(snake.getFromCell().getNumber(), snake);
            }

            return snakeMap;
        }
    }
}

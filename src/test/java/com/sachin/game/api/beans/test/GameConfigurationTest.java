package com.sachin.game.api.beans.test;

import com.sachin.game.api.beans.Cell;
import com.sachin.game.api.beans.GameConfiguration;
import com.sachin.game.api.beans.Ladder;
import com.sachin.game.api.beans.Snake;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by SachinBhosale on 7/13/2014.
 */
public class GameConfigurationTest {

    private GameConfiguration defaultConfiguration;


    @Before
    public void setUp() throws Exception {
        defaultConfiguration = GameConfiguration.GameConfigurationBuilder.getDefaultGameConfiguration();
    }

    @Test
    public void testDefaultConfiguration() throws Exception {

        Assert.assertTrue("Default Columns are 12" ,12 == defaultConfiguration.getColumns());
        Assert.assertTrue("Default rows are 10" ,10 == defaultConfiguration.getRows());
        Assert.assertTrue("Default Columns are 120" ,120 == defaultConfiguration.getMaxCell());
        Assert.assertTrue("No of players is 2", 2 == defaultConfiguration.getNoOfPlayers());

    }

    @Test
    public void testCustomConfiguration(){

        GameConfiguration.GameConfigurationBuilder builder = new GameConfiguration.GameConfigurationBuilder();

        builder.setNoOfPlayers(3);
        builder.setRows(10);
        builder.setColumns(12);

        Ladder ladder = new Ladder(4,14);
        Ladder ladder1 = new Ladder(9,31);
        Ladder ladder2 = new Ladder(20,38);

        List<Ladder> ladders = Arrays.asList(ladder, ladder1, ladder2);
        builder.addLadder(ladder);
        builder.addLadder(ladder1);
        builder.addLadder(ladder2);


        Snake snake = new Snake(17, 7);
        Snake snake1 = new Snake(54, 34);
        Snake snake2 = new Snake(62, 19);

        List<Snake> snakes = Arrays.asList(snake, snake1, snake2);
        builder.addSnake(snake);
        builder.addSnake(snake1);
        builder.addSnake(snake2);

        GameConfiguration configuration = builder.buildCongifuration();

        Assert.assertTrue("Config Columns are 12" ,12 == configuration.getColumns());
        Assert.assertTrue("Config rows are 10" ,10 == configuration.getRows());
        Assert.assertTrue("Total cells are 120" ,120 == configuration.getMaxCell());
        Assert.assertTrue("No of players is 3", 3 == configuration.getNoOfPlayers());

        Assert.assertTrue(ladder.equals(configuration.getLadderForCell(new Cell(4))));
        Assert.assertTrue(ladder1.equals(configuration.getLadderForCell(new Cell(9))));
        Assert.assertTrue(ladder2.equals(configuration.getLadderForCell(new Cell(20))));

        Assert.assertTrue(snake.equals(configuration.getSnakeForCell(new Cell(17))));
        Assert.assertTrue(snake1.equals(configuration.getSnakeForCell(new Cell(54))));
        Assert.assertTrue(snake2.equals(configuration.getSnakeForCell(new Cell(62))));

//        System.out.println("Config Ladders " + configuration.getLadders() + " my ladders = " + ladders);
//        System.out.println("Config Snakes " + configuration.getSnakes() + " my snakes = " + snakes);

        Assert.assertTrue("Ladders configured are correct in number", configuration.getLadders().size() == ladders.size());
        Assert.assertTrue("Snakes configured are correct in number",configuration.getSnakes().size() == snakes.size());


    }

    @Test
    public void testInvalidConfiguration() throws Exception {

        GameConfiguration.GameConfigurationBuilder builder = new GameConfiguration.GameConfigurationBuilder();
        try {
            GameConfiguration configuration1 = builder.buildCongifuration();
        }catch (IllegalStateException ex){
            Assert.assertTrue("Invalid configuration should throw an exception", true);
            return;
        }

        Assert.assertTrue("Invalid configuration should throw an exception", false);
    }
}

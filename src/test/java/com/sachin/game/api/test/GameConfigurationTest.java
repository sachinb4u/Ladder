package com.sachin.game.api.test;

import com.sachin.game.api.GameBoard;
import com.sachin.game.impl.GameBoardImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by SachinBhosale on 7/13/2014.
 */
public class GameConfigurationTest {

    private GameBoardImpl defaultConfiguration;


    @Before
    public void setUp() throws Exception {
        defaultConfiguration = GameBoardImpl.GameConfigurationBuilder.getDefaultGameConfiguration();
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

        GameBoardImpl.GameConfigurationBuilder builder = new GameBoardImpl.GameConfigurationBuilder();

        builder.setNoOfPlayers(3);
        builder.setRows(10);
        builder.setColumns(12);

        builder.addLadder(4, 14);
        builder.addLadder(9, 31);
        builder.addLadder(20, 38);

        builder.addSnake(17, 7);
        builder.addSnake(54, 34);
        builder.addSnake(62, 19);

        GameBoardImpl configuration = builder.buildCongifuration();

        Assert.assertTrue("Config Columns are 12" ,12 == configuration.getColumns());
        Assert.assertTrue("Config rows are 10" ,10 == configuration.getRows());
        Assert.assertTrue("Total cells are 120" ,120 == configuration.getMaxCell());
        Assert.assertTrue("No of players is 3", 3 == configuration.getNoOfPlayers());

        Assert.assertTrue(14 == configuration.getLadderForCell(4));
        Assert.assertTrue(31 == configuration.getLadderForCell(9));
        Assert.assertTrue(38 == configuration.getLadderForCell(20));

        Assert.assertTrue(7 == configuration.getSnakeForCell(17));
        Assert.assertTrue(34 == configuration.getSnakeForCell(54));
        Assert.assertTrue(19 == configuration.getSnakeForCell(62));

//        System.out.println("Config Ladders " + configuration.getLadders() + " my ladders = " + ladders);
//        System.out.println("Config Snakes " + configuration.getSnakes() + " my snakes = " + snakes);

//        Assert.assertTrue("Ladders configured are correct in number", configuration.getLadders().size() == ladders.size());
//        Assert.assertTrue("Snakes configured are correct in number",configuration.getSnakes().size() == snakes.size());


    }

    @Test
    public void testInvalidConfiguration() throws Exception {

        GameBoardImpl.GameConfigurationBuilder builder = new GameBoardImpl.GameConfigurationBuilder();
        try {
            GameBoard configuration1 = builder.buildCongifuration();
        }catch (IllegalStateException ex){
            Assert.assertTrue("Invalid configuration should throw an exception", true);
            return;
        }

        Assert.assertTrue("Invalid configuration should throw an exception", false);
    }
}

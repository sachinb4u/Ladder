package com.sachin.game.api.test;

import com.sachin.game.api.GameController;
import com.sachin.game.api.GameRule;
import com.sachin.game.api.Player;
import com.sachin.game.api.beans.Cell;
import com.sachin.game.api.beans.GameConfiguration;
import com.sachin.game.api.beans.Ladder;
import com.sachin.game.api.beans.Snake;
import com.sachin.game.api.impl.GameControllerImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Created by C5203803 on 7/24/2014.
 */
public class GameControllerTest {

    private GameConfiguration configuration;

    private GameController controller;

    @Before
    public void setup(){
        configuration = initGameConfiguration();
        controller = new GameControllerImpl(configuration);
    }

    @Test
    public void testGetGameConfiguration(){

        GameConfiguration conf = controller.getGameConfiguration();

        Assert.assertEquals(conf, configuration);
    }

    @Test
    public void testGetPlayers(){
        List<Player> players = controller.getPlayers();

        Assert.assertNotNull(players);
        // no of players should match configuration
        Assert.assertEquals(players.size(), configuration.getNoOfPlayers());

        Cell zeroCell = new Cell(0);
        //All player should have current position 0 and no game history
        for(Player player : players){
            Assert.assertNotNull(player.getName());
            Assert.assertEquals(player.getCurrentPosition(), zeroCell);
            Assert.assertEquals(player.getMoveHistory().size(),0);
            Assert.assertEquals(player.isPlayerWon(), false);
        }
    }

    @Test
    public void testGetRollDice(){
        int dice = controller.rollDice();

        Assert.assertTrue(dice > 0 && dice < 7);
    }

    @Test
    public void testGameRule(){
        GameRule gameRule = controller.getGameRule();
        Assert.assertNotNull(gameRule);
    }

    @Test
    public void testResetPlayers(){
        controller.resetPlayers();

        List<Player> players = controller.getPlayers();

        Assert.assertNotNull(players);
        // no of players should match configuration
        Assert.assertEquals(players.size(), configuration.getNoOfPlayers());

        Cell zeroCell = new Cell(0);
        //All player should have current position 0 and no game history
        for(Player player : players){
            Assert.assertNotNull(player.getName());
            Assert.assertEquals(player.getCurrentPosition(), zeroCell);
            Assert.assertEquals(player.getMoveHistory().size(),0);
            Assert.assertEquals(player.isPlayerWon(), false);
        }
    }
    private GameConfiguration initGameConfiguration() {

        GameConfiguration.GameConfigurationBuilder builder = new GameConfiguration.GameConfigurationBuilder();
        builder.setColumns(10);
        builder.setRows(10);
        builder.setNoOfPlayers(2);

        Ladder ladder1 = new Ladder(5, 20);
        Ladder ladder2 = new Ladder(24, 56);
        Ladder ladder3 = new Ladder(28, 45);

        builder.addLadder(ladder1);
        builder.addLadder(ladder2);
        builder.addLadder(ladder3);

        Snake snake1 = new Snake(48, 7);
        Snake snake2 = new Snake(63, 32);
        Snake snake3 = new Snake(84, 54);

        builder.addSnake(snake1);
        builder.addSnake(snake2);
        builder.addSnake(snake3);

        return builder.buildCongifuration();
    }
}

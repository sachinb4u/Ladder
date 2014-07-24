package com.sachin.game.api.test;

import com.sachin.game.api.GameController;
import com.sachin.game.api.Player;
import com.sachin.game.api.beans.Cell;
import com.sachin.game.api.beans.GameConfiguration;
import com.sachin.game.api.beans.Ladder;
import com.sachin.game.api.beans.Snake;
import com.sachin.game.api.impl.PlayerImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * Created by C5203803 on 7/24/2014.
 */
public class PlayerTest {
    @Mock
    private Player player;

    private GameConfiguration configuration;

    @Mock
    private GameController controller;

    @Before
    public void setup(){
        configuration = initGameConfiguration();
        Assert.assertNotNull(configuration);

        MockitoAnnotations.initMocks(this);

        Assert.assertNotNull(controller);
        player = new PlayerImpl(controller);
        Assert.assertNotNull(player);
    }

    @Test
    public void testGetName(){
        player.setName("Sachin");
        Assert.assertEquals("Sachin", player.getName());
    }

    @Test
    public void testSetName(){
        player.setName("Michael");
        Assert.assertEquals("Michael", player.getName());
    }

    public void tesPlayMove(){
        Mockito.when(player.getCurrentPosition()).thenReturn(new Cell(11));
        Cell newPosition = player.playMove(4);
        Assert.assertEquals(newPosition.getNumber(), 15);
//        Mockito.verify(player).getCurrentPosition();
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

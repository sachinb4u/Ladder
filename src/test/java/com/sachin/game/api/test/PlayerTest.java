package com.sachin.game.api.test;

import com.sachin.game.api.GameController;
import com.sachin.game.api.GameRule;
import com.sachin.game.api.Player;
import com.sachin.game.api.beans.Cell;
import com.sachin.game.api.beans.GameConfiguration;
import com.sachin.game.api.beans.GameMove;
import com.sachin.game.api.impl.PlayerImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Created by SachinBhosale on 7/24/2014.
 */
public class PlayerTest {

    private Player player;

    @Mock
    private GameConfiguration configuration;

    @Mock
    private GameRule gameRule;

    @Mock
    private GameController controller;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);

        Assert.assertNotNull(controller);
        Assert.assertNotNull(gameRule);

        player = new PlayerImpl(controller);

        // Create spy
        player = spy(player);

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

    /**
     * Test that whenever player plays game move
     *  - current position is changed to the cell returned by gamerule
     *  - gameMove added in MoveHistory
     */
    @Test
    public void testPlayMove(){
        Cell expected = new Cell(5);
        Cell current = new Cell(2);

        /**
         * getCurrentPosition gets called twice while getting next-move and adding GameMove history
         * third call should return actual value for verifying
         */
        when(player.getCurrentPosition()). thenReturn(current).thenReturn(current).thenCallRealMethod();

        /**
         GameRule will return 'current' from any current-position and for any dice-value
         - Game moves are tested as part of GameRule class
         - Here only test Player logic
         */
        when(gameRule.getNextMove(any(Cell.class), anyInt())).thenReturn(expected);
        when(controller.getGameRule()).thenReturn(gameRule);

        Cell actual = player.playMove(3);

        // assert new position is set to cell returned by GameRule
        Assert.assertEquals(expected, actual);
        // Game Move should be added in move hisotory
        Assert.assertEquals(actual,player.getCurrentPosition());
        Assert.assertNotNull(player.getMoveHistory());
        System.out.println(player.getMoveHistory());
        Assert.assertTrue(player.getMoveHistory().size() > 0);

    }

    @Test
    public void testGetCurrentPosition(){
        Cell current = new Cell(2);
        when(player.getCurrentPosition()).thenReturn(current);

        Cell res = player.getCurrentPosition();

        Assert.assertEquals(res, current);
    }

    @Test
    public void testGetMoveHistory(){
        Cell current = new Cell(2);
        Cell res = new Cell(7);
        when(gameRule.getNextMove(any(Cell.class), anyInt())).thenReturn(res);
        when(controller.getGameRule()).thenReturn(gameRule);
        when(player.getCurrentPosition()). thenReturn(current).thenReturn(current).thenCallRealMethod();
        when(player.getMoveHistory()).thenCallRealMethod();

        player.playMove(5);

        List<GameMove> moveList = player.getMoveHistory();
//        System.out.println(moveList);
        Assert.assertNotNull(moveList);
        Assert.assertTrue(moveList.size() > 0);
    }

    @Test
    public void testIsPlayerWonInvalid(){
        when(controller.getGameConfiguration()).thenReturn(configuration);
        when(configuration.getMaxCell()).thenReturn(100);
        when(player.getCurrentPosition()).thenReturn(new Cell(99));

        boolean result = player.isPlayerWon();

        Assert.assertFalse(result);
    }

    @Test
    public void testIsPlayerWonInvalid2(){
        when(controller.getGameConfiguration()).thenReturn(configuration);
        when(configuration.getMaxCell()).thenReturn(100);
        // player should no go to current position greater than max cell
        when(player.getCurrentPosition()).thenReturn(new Cell(104));

        boolean result = player.isPlayerWon();

        Assert.assertFalse(result);
    }

    @Test
    public void testIsPlayerWonValid(){
        when(controller.getGameConfiguration()).thenReturn(configuration);
        when(configuration.getMaxCell()).thenReturn(100);
        when(player.getCurrentPosition()).thenReturn(new Cell(100));

        boolean result = player.isPlayerWon();

        Assert.assertTrue(result);
    }


}

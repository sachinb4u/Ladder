package com.sachin.game.test.unit;

import com.sachin.game.GameBoard;
import com.sachin.game.GameController;
import com.sachin.game.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

/**
 * Created by SachinBhosale on 7/24/2014.
 */
public class PlayerTest {

    private Player player;

    @Mock
    private GameBoard configuration;
    @Mock
    private GameController controller;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        Assert.assertNotNull(controller);

        player = new Player(controller);

        // Create spy
        player = spy(player);

        Assert.assertNotNull(player);
    }

    @Test
    public void testGetName() {
        player.setName("Sachin");
        Assert.assertEquals("Sachin", player.getName());
    }

    @Test
    public void testSetName() {
        player.setName("Michael");
        Assert.assertEquals("Michael", player.getName());
    }

    /**
     * Test that whenever player plays game move
     * - current position is changed to the cell returned by gamerule
     * - gameMove added in MoveHistory
     */
    @Test
    public void testPlayMove() {
        int expected = 5;
        int current = 2;

        /**
         * getCurrentPosition gets called twice while getting next-move and adding GameMove history
         * third call should return actual value for verifying
         */
        when(player.getCurrentPosition()).thenReturn(current).thenReturn(current).thenCallRealMethod();

        /**
         GameRule will return 'current' from any current-position and for any dice-value
         - Game moves are tested as part of GameRule class
         - Here only test Player logic
         */
        when(controller.getNextMove(anyInt(), anyInt())).thenReturn(expected);

        int actual = player.playMove(3);

        // assert new position is set to cell returned by GameRule
        Assert.assertEquals(expected, actual);
        // Game Move should be added in move hisotory
        Assert.assertEquals(actual, player.getCurrentPosition());
    }

    @Test
    public void testGetCurrentPosition() {
        int current = 2;
        when(player.getCurrentPosition()).thenReturn(current);

        int res = player.getCurrentPosition();

        Assert.assertEquals(res, current);
    }

    @Test
    public void testIsPlayerWonInvalid() {
        when(controller.getGameBoard()).thenReturn(configuration);
        when(configuration.getWinningCell()).thenReturn(100);
        when(player.getCurrentPosition()).thenReturn(99);

        boolean result = player.isPlayerWon();

        Assert.assertFalse(result);
    }

    @Test
    public void testIsPlayerWonInvalid2() {
        when(controller.getGameBoard()).thenReturn(configuration);
        when(configuration.getWinningCell()).thenReturn(100);
        // player should no go to current position greater than max cell
        when(player.getCurrentPosition()).thenReturn(104);

        boolean result = player.isPlayerWon();

        Assert.assertFalse(result);
    }

    @Test
    public void testIsPlayerWonValid() {
        when(controller.getGameBoard()).thenReturn(configuration);
        when(configuration.getWinningCell()).thenReturn(100);
        when(player.getCurrentPosition()).thenReturn(100);

        boolean result = player.isPlayerWon();

        Assert.assertTrue(result);
    }


}

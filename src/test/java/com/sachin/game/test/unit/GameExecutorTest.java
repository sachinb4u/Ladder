package com.sachin.game.test.unit;

import com.sachin.game.GameExecutor;
import com.sachin.game.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class GameExecutorTest {

    private GameExecutor executor;

    @Before
    public void setUp() throws Exception {
        executor = new GameExecutor();
    }

    @Test
    public void testExecuteGame() throws Exception {
        executor.executeGame();

        List<Player> players = executor.getController().getPlayers();
        assertEquals("Players count is 2", 2, players.size());

        if(players.get(0).isPlayerWon()){
            assertEquals("Winning Position should be 120", executor.getGameBoard().getWinningCell(),
                    players.get(0).getCurrentPosition());
            assertFalse(players.get(1).isPlayerWon());
            assertTrue(players.get(1).getCurrentPosition() < executor.getGameBoard().getWinningCell());

        }else if(players.get(1).isPlayerWon()){
            assertEquals("Winning Position should be 120", executor.getGameBoard().getWinningCell(),
                    players.get(1).getCurrentPosition());
            assertFalse(players.get(0).isPlayerWon());
            assertTrue(players.get(0).getCurrentPosition() < executor.getGameBoard().getWinningCell());
        }else {
            assert false : "Invalid GameEnd - Either of player should win the game!";
        }

    }
}
package com.sachin.game.test.unit;

import com.sachin.game.GameBoard;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameBoardTest {

    private GameBoard gameBoard;

    @Before
    public void setup(){
        gameBoard = GameBoard.GameBoardBuilder.getDefaultGameConfiguration();
    }

    @Test
    public void testGetRows() throws Exception {
        assertEquals("RowCount should be 10", 10, gameBoard.getRows());
    }

    @Test
    public void testGetColumns() throws Exception {
        assertEquals("ColumnsCount should be 12", 12, gameBoard.getColumns());
    }

    @Test
    public void testGetLadders() throws Exception {
        assertNotNull(gameBoard.getLadders());
        assertEquals("Ladders should be 8", 8, gameBoard.getLadders().size());
        assertEquals("Ladders should equal to {51=67, 71=91, 4=14, 20=38, 9=31, 40=59, 63=81, 28=84}",
                "{51=67, 71=91, 4=14, 20=38, 9=31, 40=59, 63=81, 28=84}", gameBoard.getLadders().toString());
    }

    @Test
    public void testGetSnakes() throws Exception {
        assertNotNull(gameBoard.getSnakes());
        assertEquals("Snakes should be 8", 8, gameBoard.getSnakes().size());
        assertEquals("Snakes should equal to {17=7, 87=24, 64=60, 54=34, 99=78, 93=73, 95=75, 62=19}",
                "{17=7, 87=24, 64=60, 54=34, 99=78, 93=73, 95=75, 62=19}", gameBoard.getSnakes().toString());
    }

    @Test
    public void testGetNoOfPlayers() throws Exception {
        assertEquals("NoOfPlayers should be 2", 2, gameBoard.getNoOfPlayers());
    }

    @Test
    public void testGetLadderForCell() throws Exception {
        assertEquals("Ladder from 51 should go to 67", 67, gameBoard.getLadderForCell(51));
        assertEquals("Ladder from 71 should go to 91", 91, gameBoard.getLadderForCell(71));
        assertEquals("Ladder from 4 should go to 14", 14, gameBoard.getLadderForCell(4));
        assertEquals("Ladder from 20 should go to 38", 38, gameBoard.getLadderForCell(20));
        assertEquals("Ladder from 9 should go to 31", 31, gameBoard.getLadderForCell(9));
        assertEquals("Ladder from 40 should go to 59", 59, gameBoard.getLadderForCell(40));
        assertEquals("Ladder from 63 should go to 81", 81, gameBoard.getLadderForCell(63));
        assertEquals("Ladder from 28 should go to 84", 84, gameBoard.getLadderForCell(28));

        assertEquals("Ladder from 24 should go to -1", -1, gameBoard.getLadderForCell(24));
    }

    @Test
    public void testGetSnakeForCell() throws Exception {
        assertEquals("Snake from 17 should go to 7", 7, gameBoard.getSnakeForCell(17));
        assertEquals("Snake from 87 should go to 24", 24, gameBoard.getSnakeForCell(87));
        assertEquals("Snake from 64 should go to 60", 60, gameBoard.getSnakeForCell(64));
        assertEquals("Snake from 54 should go to 34", 34, gameBoard.getSnakeForCell(54));
        assertEquals("Snake from 99 should go to 78", 78, gameBoard.getSnakeForCell(99));
        assertEquals("Snake from 93 should go to 73", 73, gameBoard.getSnakeForCell(93));
        assertEquals("Snake from 95 should go to 75", 75, gameBoard.getSnakeForCell(95));
        assertEquals("Snake from 62 should go to 19", 19, gameBoard.getSnakeForCell(62));

        assertEquals("Ladder from 24 should go to -1", -1, gameBoard.getSnakeForCell(24));
    }

    @Test
    public void testGetWinningCell() throws Exception {
        assertEquals("WinningCell should be 120", 120, gameBoard.getWinningCell());
    }

    @Test
    public void testIsSnakeBite() throws Exception {
        assertTrue("Snake bite for 17 should be true", gameBoard.isSnakeBite(17));
        assertTrue("Snake bite for 87 should be true", gameBoard.isSnakeBite(87));
        assertTrue("Snake bite for 64 should be true", gameBoard.isSnakeBite(64));
        assertTrue("Snake bite for 54 should be true", gameBoard.isSnakeBite(54));
        assertTrue("Snake bite for 99 should be true", gameBoard.isSnakeBite(99));
        assertTrue("Snake bite for 93 should be true", gameBoard.isSnakeBite(93));
        assertTrue("Snake bite for 95 should be true", gameBoard.isSnakeBite(95));
        assertTrue("Snake bite for 62 should be true", gameBoard.isSnakeBite(62));

        assertFalse("Snake bite for 24 should be false", gameBoard.isSnakeBite(24));
    }

    @Test
    public void testIsLadderJump() throws Exception {
        assertTrue("Ladder from 51 should be true", gameBoard.isLadderJump(51));
        assertTrue("Ladder from 71 should be true", gameBoard.isLadderJump(71));
        assertTrue("Ladder from 4 should be true", gameBoard.isLadderJump(4));
        assertTrue("Ladder from 20 should be true", gameBoard.isLadderJump(20));
        assertTrue("Ladder from 9 should be true", gameBoard.isLadderJump(9));
        assertTrue("Ladder from 40 should be true", gameBoard.isLadderJump(40));
        assertTrue("Ladder from 63 should be true", gameBoard.isLadderJump(63));
        assertTrue("Ladder from 28 should be true", gameBoard.isLadderJump(28));

        assertFalse("Ladder from 24 should be false", gameBoard.isLadderJump(24));

    }

    @Test
    public void testDefaultConfiguration() throws Exception {
        GameBoard gameBoard = GameBoard.GameBoardBuilder.getDefaultGameConfiguration();

        Assert.assertTrue("Default Columns are 12", 12 == gameBoard.getColumns());
        Assert.assertTrue("Default rows are 10", 10 == gameBoard.getRows());
        Assert.assertTrue("Default Columns are 120", 120 == gameBoard.getWinningCell());
        Assert.assertTrue("No of players is 2", 2 == gameBoard.getNoOfPlayers());
    }

    @Test
    public void testCustomConfiguration() {

        GameBoard.GameBoardBuilder builder = new GameBoard.GameBoardBuilder();

        builder.setNoOfPlayers(3);
        builder.setRows(10);
        builder.setColumns(12);

        builder.addLadder(4, 14);
        builder.addLadder(9, 31);
        builder.addLadder(20, 38);

        builder.addSnake(17, 7);
        builder.addSnake(54, 34);
        builder.addSnake(62, 19);

        GameBoard configuration = builder.buildGame();

        Assert.assertTrue("Config Columns are 12", 12 == configuration.getColumns());
        Assert.assertTrue("Config rows are 10", 10 == configuration.getRows());
        Assert.assertTrue("Total cells are 120", 120 == configuration.getWinningCell());
        Assert.assertTrue("No of players is 3", 3 == configuration.getNoOfPlayers());

        Assert.assertTrue(14 == configuration.getLadderForCell(4));
        Assert.assertTrue(31 == configuration.getLadderForCell(9));
        Assert.assertTrue(38 == configuration.getLadderForCell(20));

        Assert.assertTrue(7 == configuration.getSnakeForCell(17));
        Assert.assertTrue(34 == configuration.getSnakeForCell(54));
        Assert.assertTrue(19 == configuration.getSnakeForCell(62));
    }

    @Test
    public void testInvalidConfiguration() throws Exception {
        GameBoard.GameBoardBuilder builder = new GameBoard.GameBoardBuilder();

        try {
            builder.buildGame();
        } catch (IllegalStateException ex) {
            Assert.assertTrue("Invalid configuration should throw an exception", true);
            return;
        }

        Assert.assertTrue("Invalid configuration should throw an exception", false);
    }

}
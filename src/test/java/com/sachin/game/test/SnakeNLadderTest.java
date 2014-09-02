package com.sachin.game.test;

import com.sachin.game.GameBoard;
import com.sachin.game.GameController;
import com.sachin.game.Player;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by C5203803 on 7/31/2014.
 */
public class SnakeNLadderTest {

    private GameBoard.GameBoardBuilder initGame() {
        GameBoard.GameBoardBuilder builder = new GameBoard.GameBoardBuilder();
        builder.setColumns(10);
        builder.setRows(12);
        builder.setNoOfPlayers(3);

        builder.addLadder(4, 14);
        builder.addLadder(9, 31);
        builder.addLadder(20, 38);

        builder.addSnake(17, 7);
        builder.addSnake(54, 34);
        builder.addSnake(62, 19);

        return builder;
    }

    @Test
    public void testGameBoard() {
        GameBoard.GameBoardBuilder builder1 = new GameBoard.GameBoardBuilder(initGame());
        GameBoard board1 = builder1.buildGame();

        assertEquals(10, board1.getColumns());
        assertEquals(12, board1.getRows());
        assertEquals(3, board1.getNoOfPlayers());
    }

    @Test
    public void testGameBoard_Ladders() {
        GameBoard.GameBoardBuilder builder1 = new GameBoard.GameBoardBuilder(initGame());
        GameBoard board1 = builder1.buildGame();

        assertNotNull(board1.getLadders());
        assertFalse(board1.getLadders().isEmpty());

        assertEquals(board1.getLadderForCell(4), 14);
        assertEquals(board1.getLadderForCell(9), 31);
        assertEquals(board1.getLadderForCell(20), 38);

        assertEquals(board1.getLadderForCell(5), -1);
        assertEquals(board1.getLadderForCell(45), -1);
    }

    @Test
    public void testGameBoard_Snakes() {

        GameBoard.GameBoardBuilder builder1 = new GameBoard.GameBoardBuilder(initGame());
        GameBoard board1 = builder1.buildGame();

        assertNotNull(board1.getSnakes());
        assertFalse(board1.getSnakes().isEmpty());

        assertEquals(board1.getSnakeForCell(17), 7);
        assertEquals(board1.getSnakeForCell(54), 34);
        assertEquals(board1.getSnakeForCell(62), 19);

        assertEquals(board1.getSnakeForCell(53), -1);
        assertEquals(board1.getSnakeForCell(73), -1);
    }

    /**
     * building a board with ladder on initial cell should throw an exception
     */
    @Test(expected = IllegalStateException.class)
    public void testGameBoard_LadderValidation1() {
        GameBoard.GameBoardBuilder builder1 = new GameBoard.GameBoardBuilder(initGame());
        builder1.addLadder(0, 100);

        GameBoard board1 = builder1.buildGame();
    }

    /**
     * * building a board with ladder on winning cell should throw an exception
     */
    @Test(expected = IllegalStateException.class)
    public void testGameBoard_LadderValidation2() {
        GameBoard.GameBoardBuilder builder1 = new GameBoard.GameBoardBuilder(initGame());
        builder1.addLadder(120, 120);

        GameBoard board1 = builder1.buildGame();
    }

    /**
     * building a board with snake on initial cell should throw an exception
     */
    @Test(expected = IllegalStateException.class)
    public void testGameBoard_SnakeValidation1() {
        GameBoard.GameBoardBuilder builder1 = new GameBoard.GameBoardBuilder(initGame());
        builder1.addSnake(0, 0);

        GameBoard board1 = builder1.buildGame();
    }

    /**
     * * building a board with snake on winning cell should throw an exception
     */
    @Test(expected = IllegalStateException.class)
    public void testGameBoard_SnakeValidation2() {
        GameBoard.GameBoardBuilder builder1 = new GameBoard.GameBoardBuilder(initGame());
        builder1.addSnake(120, 4);

        GameBoard board1 = builder1.buildGame();
    }

    /**
     * * building a board with snake and ladder at same cell should throw an exception
     */
/*    @Test(expected = IllegalStateException.class)
    public void testGameBoard_LadderSnakeAtSameCell(){
        GameBoard.GameBoardBuilder builder1 = new GameBoard.GameBoardBuilder(builder);
        builder1.addSnake(33, 17);
        builder1.addLadder(33, 74);

        GameBoard board1 = builder1.buildGame();
    }*/
    @Test
    public void testGameController() {
        GameBoard board = initGame().buildGame();
        GameController controller = new GameController(board);
        assertEquals(controller.getGameBoard(), board);
        assertEquals(controller.getPlayers().size(), board.getNoOfPlayers());
    }

    @Test
    public void testRollDiceValidValue() {
        GameBoard board = initGame().buildGame();
        GameController controller = new GameController(board);
        int dice = controller.rollDice();
        Assert.assertTrue(dice > 0 && dice < 7);
    }

    @Test
    public void testPlayer() {
        GameBoard board = initGame().buildGame();
        GameController controller = new GameController(board);
        Player player = new Player(controller);

        assertEquals("Default name should be Player1", player.getName(), "Player1");
        assertEquals("Initial position should be 0", player.getCurrentPosition(), 0);
        assertFalse("Player winning flag should be fals", player.isPlayerWon());
    }

    @Test
    public void testPlayer_nameSet() {
        GameBoard board = initGame().buildGame();
        GameController controller = new GameController(board);
        Player player = new Player(controller);

        player.setName("Master");

        assertEquals("Master", player.getName());
    }


    @Test
    public void testPlayer_playMove() {
        GameBoard board = initGame().buildGame();
        GameController controller = new GameController(board);
        Player player = new Player(controller);

        player.playMove(3);

        assertEquals("CurrentPosition should be 3", player.getCurrentPosition(), 3);

    }


    @Test
    public void testPlayer_playMove_winWithLadders() {

        GameBoard.GameBoardBuilder builder1 = new GameBoard.GameBoardBuilder();
        builder1.setColumns(10);
        builder1.setRows(10);
        builder1.setNoOfPlayers(3);

        builder1.addLadder(4, 14);
        builder1.addLadder(9, 31);
        builder1.addLadder(20, 38);
        builder1.addLadder(42, 84);
        builder1.addLadder(87, 97);

        GameBoard board1 = builder1.buildGame();
        GameController controller1 = new GameController(board1);

        Player player1 = new Player(controller1);
        player1.playMove(3);
        player1.playMove(1);
        player1.playMove(6);
        player1.playMove(4);
        player1.playMove(3);
        player1.playMove(3);

        assertEquals("CurrentPosition should be 100", player1.getCurrentPosition(), 100);
        assertEquals("Player is winner", player1.isPlayerWon(), true);
    }

    @Test
    public void testPlayer_playMove_unsuccessfulPlayWithLadders() {

        GameBoard.GameBoardBuilder builder1 = new GameBoard.GameBoardBuilder();
        builder1.setColumns(10);
        builder1.setRows(10);
        builder1.setNoOfPlayers(3);

        builder1.addLadder(4, 14);
        builder1.addLadder(9, 31);
        builder1.addLadder(20, 85);
        builder1.addLadder(87, 97);

        GameBoard board1 = builder1.buildGame();
        GameController controller1 = new GameController(board1);

        Player player1 = new Player(controller1);
        player1.playMove(4);
        player1.playMove(6);
        player1.playMove(2);
        player1.playMove(4);

        assertEquals("CurrentPosition should be 97", player1.getCurrentPosition(), 97);
        assertEquals("Player is winner", player1.isPlayerWon(), false);
    }

    @Test
    public void testPlayer_playMove_unsuccessfulPlayWithSnakes() {

        GameBoard.GameBoardBuilder builder1 = new GameBoard.GameBoardBuilder();
        builder1.setColumns(10);
        builder1.setRows(10);
        builder1.setNoOfPlayers(3);

        builder1.addLadder(4, 14);
        builder1.addLadder(9, 16);
        builder1.addLadder(20, 85);

        builder1.addSnake(17, 7);

        GameBoard board1 = builder1.buildGame();
        GameController controller1 = new GameController(board1);

        Player player1 = new Player(controller1);
        player1.playMove(4);
        player1.playMove(3);
        player1.playMove(2);
        player1.playMove(4);
        player1.playMove(5);

        assertEquals("CurrentPosition should be 90", player1.getCurrentPosition(), 90);
        assertEquals("Player is winner", player1.isPlayerWon(), false);
    }

    @Test
    public void testPlayer_playMove_multiPlayer() {

        GameBoard.GameBoardBuilder builder1 = new GameBoard.GameBoardBuilder();
        builder1.setColumns(10);
        builder1.setRows(10);
        builder1.setNoOfPlayers(3);

        builder1.addLadder(4, 14);
        builder1.addLadder(9, 16);
        builder1.addLadder(20, 85);

        builder1.addSnake(17, 7);

        GameBoard board1 = builder1.buildGame();
        GameController controller1 = new GameController(board1);

        Player player1 = new Player(controller1);
        Player player2 = new Player(controller1);

        player1.playMove(5);
        player2.playMove(3);

        assertEquals("CurrentPosition should be 5", player1.getCurrentPosition(), 5);
        assertEquals("CurrentPosition should be 3", player2.getCurrentPosition(), 3);

        player1.playMove(2);
        player2.playMove(6);

        assertEquals("CurrentPosition should be 7", player1.getCurrentPosition(), 7);
        assertEquals("CurrentPosition should be 16", player2.getCurrentPosition(), 16);

        player1.playMove(5);
        player2.playMove(5);

        assertEquals("CurrentPosition should be 12", player1.getCurrentPosition(), 12);
        assertEquals("CurrentPosition should be 21", player2.getCurrentPosition(), 21);

        player1.playMove(5);
        player2.playMove(1);

        assertEquals("CurrentPosition should be 7", player1.getCurrentPosition(), 7);
        assertEquals("CurrentPosition should be 22", player2.getCurrentPosition(), 22);

        player1.playMove(5);
        player2.playMove(3);

        assertEquals("CurrentPosition should be 12", player1.getCurrentPosition(), 12);
        assertEquals("CurrentPosition should be 25", player2.getCurrentPosition(), 25);

        assertEquals("Player is winner", player1.isPlayerWon(), false);
        assertEquals("Player is winner", player2.isPlayerWon(), false);

    }

    @Test
    public void testPlayer_playMove_multiPlayer_Player2Wins() {

        GameBoard.GameBoardBuilder builder1 = new GameBoard.GameBoardBuilder();
        builder1.setColumns(10);
        builder1.setRows(10);
        builder1.setNoOfPlayers(3);

        builder1.addLadder(4, 14);
        builder1.addLadder(9, 16);
        builder1.addLadder(20, 95);

        builder1.addSnake(17, 7);

        GameBoard board1 = builder1.buildGame();
        GameController controller1 = new GameController(board1);

        Player player1 = new Player(controller1);
        Player player2 = new Player(controller1);

        player1.playMove(5);
        player2.playMove(3);

        assertEquals("CurrentPosition should be 5", player1.getCurrentPosition(), 5);
        assertEquals("CurrentPosition should be 3", player2.getCurrentPosition(), 3);

        player1.playMove(2);
        player2.playMove(6);

        assertEquals("CurrentPosition should be 7", player1.getCurrentPosition(), 7);
        assertEquals("CurrentPosition should be 16", player2.getCurrentPosition(), 16);

        player1.playMove(5);
        player2.playMove(4);

        assertEquals("CurrentPosition should be 12", player1.getCurrentPosition(), 12);
        assertEquals("CurrentPosition should be 95", player2.getCurrentPosition(), 95);

        player1.playMove(5);
        player2.playMove(3);

        assertEquals("CurrentPosition should be 7", player1.getCurrentPosition(), 7);
        assertEquals("CurrentPosition should be 98", player2.getCurrentPosition(), 98);

        player1.playMove(5);
        player2.playMove(2);

        assertEquals("CurrentPosition should be 12", player1.getCurrentPosition(), 12);
        assertEquals("CurrentPosition should be 25", player2.getCurrentPosition(), 100);

        assertEquals("Player is winner", player1.isPlayerWon(), false);
        assertEquals("Player is winner", player2.isPlayerWon(), true);

    }

    @Test
    public void testPlayer_winWithExactNumber() {
        GameBoard.GameBoardBuilder builder1 = new GameBoard.GameBoardBuilder();
        builder1.setColumns(10);
        builder1.setRows(10);
        builder1.setNoOfPlayers(3);

        builder1.addLadder(4, 14);
        builder1.addLadder(15, 97);

        GameBoard board1 = builder1.buildGame();
        GameController controller1 = new GameController(board1);

        Player player1 = new Player(controller1);

        player1.playMove(4);
        player1.playMove(1);
        player1.playMove(6);
        assertEquals("CurrentPosition should be 97", player1.getCurrentPosition(), 97);

        player1.playMove(4);
        assertEquals("CurrentPosition should be 97", player1.getCurrentPosition(), 97);

        player1.playMove(3);
        assertEquals("CurrentPosition should be 100", player1.getCurrentPosition(), 100);
        assertTrue("Player1 wins the game.", player1.isPlayerWon());
    }

}

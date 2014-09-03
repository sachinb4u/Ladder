package com.sachin.game.test;

import com.sachin.game.GameBoard;
import com.sachin.game.GameController;
import com.sachin.game.Player;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

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
/*   @Test(expected = IllegalStateException.class)
    public void testGameBoard_LadderSnakeAtSameCell(){
        GameBoard.GameBoardBuilder builder1 = new GameBoard.GameBoardBuilder(initGame());
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
    public void testPlay_playMove_unsuccessfulPlayWithSnakes() {

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
    public void testPlay_playMove_multiPlayer() {

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
    public void testPlay_playMove_multiPlayer_Player2Wins() {

        GameBoard.GameBoardBuilder builder1 = new GameBoard.GameBoardBuilder();
        builder1.setColumns(10);
        builder1.setRows(10);
        builder1.setNoOfPlayers(2);

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

    /**
     * TestPlay0 - Winning path
     *
     * Board : 10 x 10
     * Snakes :
     * Ladders : 4-14, 15-97
     * Players : 3
     * WinningPosition : 100
     * Players Dice Rolls :
     *      Player1 : 4, 1, 6, 4, 3
     *      Player2 : 6, 3, 5, 4, 2
     *      Player3 : 3, 2, 6, 5, 1
     * Expected Position :
     *      Player1 : 100
     *      Player2 : 20
     *      Player3 : 17
     * Expected Winner : Player1
     */
    @Test
    public void testPlay_winWithExactNumber() {
        GameBoard.GameBoardBuilder builder1 = new GameBoard.GameBoardBuilder();
        builder1.setColumns(10);
        builder1.setRows(10);
        builder1.setNoOfPlayers(3);

        builder1.addLadder(4, 14);
        builder1.addLadder(15, 97);

        GameBoard board1 = builder1.buildGame();
        GameController controller1 = new GameController(board1);

        List<Player> players = controller1.getPlayers();
        assertEquals("NoOfPlayers should be 3",players.size(), 3);

        Player player1 = players.get(0);
        Player player2 = players.get(1);
        Player player3 = players.get(2);

        player1.playMove(4);
        player2.playMove(6);
        player3.playMove(3);

        player1.playMove(1);
        player2.playMove(3);
        player3.playMove(2);

        player1.playMove(6);
        player2.playMove(5);
        player3.playMove(6);

        player1.playMove(4);
        player2.playMove(4);
        player3.playMove(5);

        player1.playMove(3);
        player2.playMove(2);
        player3.playMove(1);

        assertEquals("CurrentPosition should be 100", player1.getCurrentPosition(), 100);
        assertEquals("CurrentPosition should be 20", player2.getCurrentPosition(), 20);
        assertEquals("CurrentPosition should be 17", player3.getCurrentPosition(), 17);

        assertTrue("Player1 wins the game.", player1.isPlayerWon());
        assertFalse("Player2 loses the game.", player2.isPlayerWon());
        assertFalse("Player3 loses the game.", player3.isPlayerWon());
    }

    /**
     * TestPlay1
     *
     * Board : 5 x 5
     * Snakes : 13-7, 23-4, 18-12
     * Ladders : 3-11, 14-21, 9-24
     * Players : 1
     * WinningPosition : 25
     * Players Dice Rolls : 3, 5, 3
     * Expected Position : 19
     * Winner : False
     */
    @Test
    public void testPlay1(){
        //setup board
        GameBoard.GameBoardBuilder builder1 = new GameBoard.GameBoardBuilder();
        builder1.setColumns(5);
        builder1.setRows(5);
        builder1.setNoOfPlayers(1);
        builder1.addLadder(3, 11);
        builder1.addLadder(14, 21);
        builder1.addLadder(9, 24);
        builder1.addSnake(13, 7);
        builder1.addSnake(23, 4);
        builder1.addSnake(18, 12);

        //initialize controller and player
        GameController controller1 = new GameController(builder1.buildGame());
        List<Player> players = controller1.getPlayers();
        assertEquals("NoOfPlayers should be 1",players.size(), 1);

        Player player = players.get(0);
        //play the game
        player.playMove(3);
        player.playMove(5);
        player.playMove(3);

        //validate
        assertEquals("CurrentPosition should be 19", 19 , player.getCurrentPosition());
        assertFalse("Player has not won", player.isPlayerWon());
    }

    /**
     * TestPlay2
     *
     * Board : 5 x 5
     * Snakes : 13-7, 23-4, 18-12
     * Ladders : 3-11, 14-21, 9-24
     * Players : 1
     * WinningPosition : 25
     * Players Dice Rolls : 4, 6, 2, 2
     * Expected Position : 21
     * Winner : False
     */
    @Test
    public void testPlay2(){
        //setup board
        GameBoard.GameBoardBuilder builder1 = new GameBoard.GameBoardBuilder();
        builder1.setColumns(5);
        builder1.setRows(5);
        builder1.setNoOfPlayers(1);
        builder1.addLadder(3, 11);
        builder1.addLadder(14, 21);
        builder1.addLadder(9, 24);
        builder1.addSnake(13, 7);
        builder1.addSnake(23, 4);
        builder1.addSnake(18, 12);

        //initialize controller and player
        GameController controller1 = new GameController(builder1.buildGame());
        List<Player> players = controller1.getPlayers();
        assertEquals("NoOfPlayers should be 1",players.size(), 1);

        Player player = players.get(0);
        //play the game
        player.playMove(4);
        player.playMove(6);
        player.playMove(2);
        player.playMove(2);

        //validate
        assertEquals("CurrentPosition should be 21", 21 , player.getCurrentPosition());
        assertFalse("Player has not won", player.isPlayerWon());
    }

    /**
     * TestPlay3
     * Board : 5 x 5
     * Snakes : 13-7, 23-4, 18-12
     * Ladders : 3-11, 14-21, 9-24
     * Players : 1
     * WinningPosition : 25
     * Players Dice Rolls : 1, 5, 3, 6, 1
     * Expected Position : 25
     * Winner : True
     */
    @Test
    public void testPlay3(){
        //setup board
        GameBoard.GameBoardBuilder builder1 = new GameBoard.GameBoardBuilder();
        builder1.setColumns(5);
        builder1.setRows(5);
        builder1.setNoOfPlayers(1);
        builder1.addLadder(3, 11);
        builder1.addLadder(14, 21);
        builder1.addLadder(9, 24);
        builder1.addSnake(13, 7);
        builder1.addSnake(23, 4);
        builder1.addSnake(18, 12);

        //initialize controller and player
        GameController controller1 = new GameController(builder1.buildGame());
        List<Player> players = controller1.getPlayers();
        assertEquals("NoOfPlayers should be 1",players.size(), 1);

        Player player = players.get(0);
        //play the game
        player.playMove(1);
        player.playMove(5);
        player.playMove(3);
        player.playMove(6);
        player.playMove(1);

        //validate
        assertEquals("CurrentPosition should be 25", 25 , player.getCurrentPosition());
        assertTrue("Player wins", player.isPlayerWon());
    }
}

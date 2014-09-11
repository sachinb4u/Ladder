package com.sachin.game.test.v2.test;

import com.sachin.game.v2.Game;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by SachinBhosale on 9/11/2014.
 */
public class SnakeLadderGameTest {

    @Test
    public void empty_game_start_initial_position_should_be_zero(){
        Game game = new Game();

        Assert.assertEquals(game.getCurrentPosition(), 0);
    }

    @Test
    public void move_to_five_after_dice_roll_five(){
        Game game = new Game();

        game.move(4);

        Assert.assertEquals(game.getCurrentPosition(), 4);
    }

    @Test
    public void move_to_five_from_one_after_dice_roll_four(){
        Game game = new Game();

        game.move(1);
        game.move(4);

        Assert.assertEquals(game.getCurrentPosition(), 5);
    }

    @Test
    public void dice_roll_should_between_one_and_six(){
        Game game = new Game();

        int diceValue = game.roll();

        Assert.assertTrue(diceValue >= 1 && diceValue <= 6);
    }


    @Test
    public void player_should_move_to_six_from_one_with_five_dice_value(){

        Game game = new Game();

        game.move(1);
        game.move(5);

        Assert.assertEquals(game.getCurrentPosition(), 6);
    }

    @Test
    public void player_should_jump_to_21_from_2_with_ladder(){
        Game game = new Game();
        game.addPath(2, 21);

        game.move(2);

        Assert.assertEquals(game.getCurrentPosition(), 21);
    }

    @Test(expected = IllegalArgumentException.class)
    public void player_moves_with_dice_8_throws_illegal_argument_exception(){
        Game game = new Game();

        game.move(8);
    }

    @Test
    public void player_should_go_down_to_3_from_11_with_snake(){
        Game game = new Game();
        game.addPath(11, 3);

        game.move(6);
        game.move(2);
        game.move(3);

        Assert.assertEquals(game.getCurrentPosition(), 3);
    }

    @Test
    public void player_should_win_with_position_100(){
        Game game = new Game();

        for (int i = 0; i < 16 ; i++) game.move(6);

        game.move(4);

        Assert.assertTrue(game.isPlayerWon());
    }

    @Test
    public void player_at_96_with_dice_5_does_not_win(){
        Game game = new Game();

        for (int i = 0; i < 16 ; i++) game.move(6);

        game.move(5);

        Assert.assertFalse(game.isPlayerWon());
    }

    @Test
    public void player_at_96_with_dice_5_remains_at_96(){
        Game game = new Game();

        for (int i = 0; i < 16 ; i++) game.move(6);

        game.move(5);

        Assert.assertEquals(game.getCurrentPosition(), 96);
    }
}

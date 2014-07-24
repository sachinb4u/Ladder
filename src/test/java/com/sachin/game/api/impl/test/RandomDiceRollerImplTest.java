package com.sachin.game.api.impl.test;

import com.sachin.game.api.impl.RandomDiceRollerImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * Created by SachinBhosale on 7/12/2014.
 */
public class RandomDiceRollerImplTest {

    private RandomDiceRollerImpl randomDiceRollerImpl;

    @Before
    public void setUp() throws Exception {
        randomDiceRollerImpl = new RandomDiceRollerImpl();
    }

    @Test
    public void testRollDice() throws Exception {
        int diceValue = randomDiceRollerImpl.rollDice();
//        System.out.println("DiceValue = " + diceValue);
        Assert.assertTrue("DiceValue should be less than 6 and greater than 0", diceValue <= 6 && diceValue > 0);
    }
}

package com.sachin.game.api.test;

import com.sachin.game.api.DiceRoller;
import com.sachin.game.api.impl.RandomDiceRollerImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by SachinBhosale on 7/24/2014.
 */
public class DiceRollerTest {

    private DiceRoller diceRoller;

    @Before
    public void setup() {
        diceRoller = new RandomDiceRollerImpl();
    }

    @Test
    public void testRollDiceValidValue() {
        int dice = diceRoller.rollDice();
        Assert.assertTrue(dice > 0 && dice < 7);
    }

    @Test
    public void testRollDiceRandomness() {
        List<Integer> diceValArray = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            int dice = diceRoller.rollDice();
//            System.out.println("Dice = " + dice);
            diceValArray.add(dice);
        }
        // frequency of any number should not be 4 or more than that in consecutive 6 attempts
        Assert.assertFalse("frequency of any number should not be 4 or more than that in consecutive 6 attempts", Collections.frequency(diceValArray, Integer.valueOf(1)) >= 4);
        Assert.assertFalse("frequency of any number should not be 4 or more than that in consecutive 6 attempts", Collections.frequency(diceValArray, Integer.valueOf(2)) >= 4);
        Assert.assertFalse("frequency of any number should not be 4 or more than that in consecutive 6 attempts", Collections.frequency(diceValArray, Integer.valueOf(3)) >= 4);
        Assert.assertFalse("frequency of any number should not be 4 or more than that in consecutive 6 attempts", Collections.frequency(diceValArray, Integer.valueOf(4)) >= 4);
        Assert.assertFalse("frequency of any number should not be 4 or more than that in consecutive 6 attempts", Collections.frequency(diceValArray, Integer.valueOf(5)) >= 4);
        Assert.assertFalse("frequency of any number should not be 4 or more than that in consecutive 6 attempts", Collections.frequency(diceValArray, Integer.valueOf(6)) >= 4);
    }
}

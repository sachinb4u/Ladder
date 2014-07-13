package com.sachin.game.api.impl.test;

import com.sachin.game.api.impl.RandomDiceRoller;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by C5203803 on 7/12/2014.
 */
public class RandomDiceRollerTest {

    private RandomDiceRoller randomDiceRoller;
    private static final Logger logger = LoggerFactory.getLogger(RandomDiceRollerTest.class);

    @Before
    public void setUp() throws Exception {
        randomDiceRoller = new RandomDiceRoller();
    }

    @Test
    public void testRollDice() throws Exception {
        int diceValue = randomDiceRoller.rollDice();
        logger.debug("DiceValue = " + diceValue);
        Assert.assertTrue("DiceValue should be less than 6 and greater than 0", diceValue <= 6 && diceValue > 0);
    }
}

package com.sachin.game.api.impl.test;

import com.sachin.game.api.impl.RandomDiceRollerImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by C5203803 on 7/12/2014.
 */
public class RandomDiceRollerImplTest {

    private RandomDiceRollerImpl randomDiceRollerImpl;
    private static final Logger logger = LoggerFactory.getLogger(RandomDiceRollerImplTest.class);

    @Before
    public void setUp() throws Exception {
        randomDiceRollerImpl = new RandomDiceRollerImpl();
    }

    @Test
    public void testRollDice() throws Exception {
        int diceValue = randomDiceRollerImpl.rollDice();
        logger.debug("DiceValue = " + diceValue);
        Assert.assertTrue("DiceValue should be less than 6 and greater than 0", diceValue <= 6 && diceValue > 0);
    }
}

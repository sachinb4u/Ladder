package com.sachin.game.api.impl;

import com.sachin.game.api.DiceRoller;

import java.security.SecureRandom;

/**
 * Created by SachinBhosale on 7/12/2014.
 */
public class RandomDiceRollerImpl implements DiceRoller {

    private static final SecureRandom random = new SecureRandom();

    /**
     * Returns a pseudorandom, uniformly distributed {@code int} value
     * between 1 (inclusive) and 6 (inclusive)
     *
     * @return int
     */
    @Override
    public int rollDice() {
        int randomInt;
        do{
            randomInt = random.nextInt(7);
        }while (0 == randomInt);

        return randomInt;
    }
}

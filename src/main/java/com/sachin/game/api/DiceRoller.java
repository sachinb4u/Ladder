package com.sachin.game.api;

/**
 * Created by SachinBhosale on 7/12/2014.
 */
public interface DiceRoller {

    /**
     * Returns a pseudorandom, uniformly distributed int value
     * between 1 (inclusive) and 6 (inclusive)
     *
     * @return int
     */
    int rollDice();
}

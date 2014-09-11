package com.sachin.game.v2;

import java.security.SecureRandom;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by SachinBhosale on 9/11/2014.
 */
public class Game {
    int currentPosition;
    private final SecureRandom random;
    private final Map<Integer,Integer> paths;
    private final int winningPosition;

    public Game(){
        random = new SecureRandom();
        paths = new ConcurrentHashMap<>();
        winningPosition = 100;
    }

    public Game(int winningPosition){
        random = new SecureRandom();
        paths = new ConcurrentHashMap<>();
        this.winningPosition = winningPosition;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int position){
        currentPosition = position;
    }

    public void move(int dice) {

        if(dice > 6 || dice < 1){
            throw new IllegalArgumentException("Dice value should be in between 1 and 6 (both inclusive)");
        }

        if((currentPosition + dice) <= winningPosition) {
            currentPosition += dice;
        }

        if(paths.containsKey(currentPosition)){
            currentPosition = paths.get(currentPosition);
        }
    }

    public int roll() {
        int value;
        do {
            value = random.nextInt(7);
        }while (value == 0);
        return value;
    }


    public void addPath(int from, int to) {
        paths.put(from,to);
    }

    public boolean isPlayerWon() {
        return currentPosition == winningPosition;
    }
}

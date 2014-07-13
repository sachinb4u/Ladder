package com.sachin.game.client;

import com.sachin.game.api.GameExecutor;
import com.sachin.game.api.impl.GameExecutorImpl;

/**
 * Created by C5203803 on 7/13/2014.
 */
public class SnakeLadderGameMain {

    public static void main(String[] args){
        GameExecutor gameExecutor = new GameExecutorImpl();

        gameExecutor.executeGame();
    }
}
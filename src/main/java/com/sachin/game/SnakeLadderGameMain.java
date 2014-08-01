package com.sachin.game;

import com.sachin.game.api.GameExecutor;
import com.sachin.game.impl.CommandLineGameExecutorImpl;

/**
 * Created by SachinBhosale on 7/13/2014.
 */
public class SnakeLadderGameMain {

    public static void main(String[] args) {
        GameExecutor gameExecutor = new CommandLineGameExecutorImpl();

        gameExecutor.executeGame();
    }
}

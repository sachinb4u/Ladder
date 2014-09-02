package com.sachin.game;

import java.util.List;
import java.util.Scanner;

/**
 * Created by SachinBhosale on 7/13/2014.
 */
public class GameExecutor {

    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Get user input from commandline
     * @return {String}
     */
    private static String getUserInput() {
        String input = scanner.next();

        if ("quit".equalsIgnoreCase(input)) {
            System.exit(0);
        }

        return input;
    }

    /**
     * Print Message on command line
     *
     * @param str
     */
    private static void showUserMessage(String str) {
        System.out.println(str);
    }

    /**
     * Run the show
     */
    public void executeGame() {
        /**
         * Get / Build Game Configuration
         */
        GameBoard configuration = GameBoard.GameBoardBuilder.getDefaultGameConfiguration();

        /**
         * Get the GameController to run the game
         */
        GameController controller = new GameController(configuration);

        do {
            showUserMessage("Let's start a game.");

            /**
             * Reset players with new players with initial position 0 and default names
             */
            controller.resetPlayers();

            List<Player> players = controller.getPlayers();

            /**
             * Get Names for players
             */
            getNamesForPlayer(players);

            /**
             * while player is not winning ,
             * roll the dice and go to next cell
             * Each player gets his turn in round robin manner
             */
            while (!isPlayerWonInRoundRobinPlay(controller, players)) ;

            showUserMessage("Game Summary !!");

            /**
             * Show summary of each player's game
             */
            for (Player player : players) {
                showUserMessage("Player : " + player.getName() + "'s Game History: ##");
            }

            showUserMessage("GameSetup : " + configuration.toString());
            showUserMessage("Play New Game ?? ( Yes / No ) ");

        } while (scanner.next().matches("[yY][\\w]*"));
    }

    /**
     * Get Names for players and set it
     *
     * @param players
     */
    private void getNamesForPlayer(List<Player> players) {
        for (Player player : players) {
            showUserMessage("Enter name for " + player.getName());
            String name = getUserInput();
            player.setName(name);
        }
    }

    /**
     * @param controller
     * @param players
     * @return flag if any player wins
     */
    private boolean isPlayerWonInRoundRobinPlay(GameController controller, List<Player> players) {
        for (Player player : players) {
            /**
             * Show current Position
             */
            showUserMessage(player.getName() + " - CurrentPosition: " + player.getCurrentPosition());

            /**
             * roll dice to get players dice value
             */
            int diceValue = controller.rollDice();

            showUserMessage("Dice Rolled : " + diceValue);

            /**
             * Play a move from current position for dice value
             */
            player.playMove(diceValue);

            showUserMessage(player.getName() + " - NewPosition: " + player.getCurrentPosition());

            showUserMessage("******************************************");

            /**
             * Show a message if player wins
             */
            if (player.isPlayerWon()) {
                showUserMessage("Congratulations " + player.getName() + "! You won !! ");
                return true;
            }
        }
        return false;
    }

}

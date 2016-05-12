package com.egyed.adam.rubikscube;

import com.egyed.adam.rubikscube.engine.GameEngine;
import com.egyed.adam.rubikscube.engine.GameLogic;
import com.egyed.adam.rubikscube.game.RubiksGame;


/**
 * Created by Adam on 5/10/16.
 * Starter Class - Entry point at main
 */

public class Main {

    public static void main(String[] args) {

        try {
            // Add lwjgl native libraries to the correct path
            System.setProperty("org.lwjgl.librarypath","lib/native");

            GameLogic gameLogic = new RubiksGame();

            // Construct the Game Engine with the game logic defined by 'gameLogic', an instance of RubiksGame
            GameEngine gameEngine = new GameEngine("Rubik's Cube", 600, 480, true, gameLogic);

            gameEngine.start();

        }
        catch (Exception e) {

            e.printStackTrace();
            System.exit(-1);

        }

    }
}

package com.egyed.adam.rubikscube.game;

import com.egyed.adam.rubikscube.engine.GameLogic;
import com.egyed.adam.rubikscube.engine.MainWindow;
import static org.lwjgl.opengl.GL11.glViewport;

/**
 * Created by Adam on 5/11/16.
 * Game Logic for Rubik's Cube
 */
public class RubiksGame implements GameLogic {

    private final Renderer renderer;

    public RubiksGame() {
        renderer = new Renderer();
    }


    @Override
    public void init() throws Exception {
        renderer.init();
    }

    @Override
    public void input(MainWindow mainWindow) {

    }

    @Override
    public void update(float interval) {

    }

    @Override
    public void render(MainWindow mainWindow) {

        // Adjust viewport if resized, and set 'resized' to false after completion
        if (mainWindow.isResized()) {
            glViewport(0,0, mainWindow.getWidth(), mainWindow.getHeight());
            mainWindow.setResized(false);
        }

        renderer.clear();

    }
}

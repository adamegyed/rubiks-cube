package com.egyed.adam.rubikscube.game;


import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

/**
 * Created by Adam on 5/11/16.
 * Handles OpenGL rendering for RubiksGame
 */
public class Renderer {

    public Renderer() {

    }

    public void init() throws Exception {


    }

    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

}

package com.egyed.adam.rubikscube.game;

import com.egyed.adam.rubikscube.engine.GameItem;
import com.egyed.adam.rubikscube.engine.GameLogic;
import com.egyed.adam.rubikscube.engine.MainWindow;
import com.egyed.adam.rubikscube.engine.graphics.Camera;
import com.egyed.adam.rubikscube.engine.graphics.Mesh;
import com.egyed.adam.rubikscube.game.piece.CenterPiece;
import com.egyed.adam.rubikscube.game.piece.Piece;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glClearColor;

/**
 * Created by Adam on 5/11/16.
 * Game Logic for Rubik's Cube
 */
public class RubiksGame implements GameLogic {

    private final Renderer renderer;

    private GameItem[] gameItems;

    private ArrayList<GameItem> moving = new ArrayList<GameItem>();

    private Camera camera;

    private Piece[][][] pieces;

    private int scaleInc = 0;

    private int rotateXDirection;
    private int rotateYDirection;
    private int rotateZDirection;

    private float rotateXAngle;
    private float rotateYAngle;
    private float rotateZAngle;


    private final Vector3f cameraInc;
    private final Vector3f cameraRotInc;
    private static final float CAMERA_POS_STEP = 0.05f;
    private static final float CAMERA_ROT_STEP = 1.5f;

    public RubiksGame() {
        renderer = new Renderer();
        camera = new Camera();
        cameraInc = new Vector3f(0,0,0);
        cameraRotInc = new Vector3f(0,0,0);
    }


    @Override
    public void init(MainWindow mainWindow) throws Exception {
        renderer.init(mainWindow);

        ArrayList<GameItem> itemList = new ArrayList<>();


        for (int i = 0; i < 8; i++) {

            int color = i;
            while (color>5) color-=5;
            GameItem center = CenterPiece.createCenterPiece(color);
            center.setPosition(0.4f,0.2f*i,-2);
            center.setRotationDegrees(i * 45,0,0);
            itemList.add(center);
            moving.add(center);
        }
        for (int i = 0; i < 8; i++) {

            int color = i;
            while (color>5) color-=5;
            GameItem center = CenterPiece.createCenterPiece(color);
            center.setPosition(0,0,-2);
            center.setRotationDegrees(0,i * 45,0);
            itemList.add(center);
            //moving[i] = center;
        }

        GameItem center = CenterPiece.createCenterPiece(4);
        center.setPosition(0,0,-2);
        center.setRotationDegrees(0,90,0);
        itemList.add(center);
        GameItem center2 = CenterPiece.createCenterPiece(5);
        center2.setPosition(0,0,-2);
        center2.setRotationDegrees(0,270,0);
        itemList.add(center2);

        gameItems = new GameItem[itemList.size()];

        gameItems = itemList.toArray(gameItems);

        glClearColor(0.45f, 0.5f, 0.5f, 0.7f);
    }

    @Override
    public void input(MainWindow mainWindow) {
        cameraInc.set(0, 0, 0);
        cameraRotInc.set(0,0,0);


        rotateXDirection = 0;
        rotateYDirection = 0;
        rotateZDirection = 0;

        scaleInc = 0;


        if (mainWindow.isKeyPressed(GLFW_KEY_W)) {
            cameraInc.z = -1;
        } else if (mainWindow.isKeyPressed(GLFW_KEY_S)) {
            cameraInc.z = 1;
        }
        if (mainWindow.isKeyPressed(GLFW_KEY_A)) {
            cameraInc.x = -1;
        } else if (mainWindow.isKeyPressed(GLFW_KEY_D)) {
            cameraInc.x = 1;
        }
        if (mainWindow.isKeyPressed(GLFW_KEY_Z)) {
            cameraInc.y = -1;
        } else if (mainWindow.isKeyPressed(GLFW_KEY_X)) {
            cameraInc.y = 1;
        }
        if (mainWindow.isKeyPressed(GLFW_KEY_N)) {
            cameraRotInc.y = 1;
        } else if (mainWindow.isKeyPressed(GLFW_KEY_B)) {
            cameraRotInc.y = -1;
        }
        if (mainWindow.isKeyPressed(GLFW_KEY_F)) {
            cameraRotInc.x = -1;
        } else if (mainWindow.isKeyPressed(GLFW_KEY_V)) {
            cameraRotInc.x = 1;
        }



        /*
        displyInc = 0;
        displxInc = 0;
        displzInc = 0;
        scaleInc = 0;

        if (mainWindow.isKeyPressed(GLFW_KEY_UP)) {
            displyInc = 1;
        } else if (mainWindow.isKeyPressed(GLFW_KEY_DOWN)) {
            displyInc = -1;
        } else if (mainWindow.isKeyPressed(GLFW_KEY_LEFT)) {
            displxInc = -1;
        } else if (mainWindow.isKeyPressed(GLFW_KEY_RIGHT)) {
            displxInc = 1;
        } else if (mainWindow.isKeyPressed(GLFW_KEY_A)) {
            displzInc = -1;
        } else if (mainWindow.isKeyPressed(GLFW_KEY_Q)) {
            displzInc = 1;
        } else*/


        if (mainWindow.isKeyPressed(GLFW_KEY_UP)) {
            scaleInc = -1;
        } else if (mainWindow.isKeyPressed(GLFW_KEY_DOWN)) {
            scaleInc = 1;
        }

        if (mainWindow.isKeyPressed(GLFW_KEY_I)) {
            rotateXDirection = 1;
        } else
        if (mainWindow.isKeyPressed(GLFW_KEY_K)) {
            rotateXDirection = -1;
        }
        if (mainWindow.isKeyPressed(GLFW_KEY_J)) {
            rotateYDirection = 1;
        } else
        if (mainWindow.isKeyPressed(GLFW_KEY_L)) {
            rotateYDirection = -1;
        }
        if (mainWindow.isKeyPressed(GLFW_KEY_P)) {
            rotateZDirection = 1;
        } else
        if (mainWindow.isKeyPressed(GLFW_KEY_SEMICOLON)) {
            rotateZDirection = -1;
        }

        if (mainWindow.getShouldCameraReset()) {
            camera.setPosition(0,0,0);
            camera.setRotation(0,0,0);
            mainWindow.setShouldCameraReset(false);
        }
    }

    @Override
    public void update(float interval) {

        camera.movePosition(cameraInc.x * CAMERA_POS_STEP, cameraInc.y * CAMERA_POS_STEP, cameraInc.z * CAMERA_POS_STEP);
        camera.moveRotation(cameraRotInc.x * CAMERA_ROT_STEP, cameraRotInc.y * CAMERA_ROT_STEP, 0);

        rotateXAngle = (float) Math.toRadians(1.5 * rotateXDirection);
        rotateYAngle = (float) Math.toRadians(1.5 * rotateYDirection);
        rotateZAngle = (float) Math.toRadians(1.5 * rotateZDirection);

        Matrix4f rotationUpdate = new Matrix4f().identity();

        if (rotateXAngle!=0) {
            Matrix4f xRotation = new Matrix4f().identity();
            float sinAlpha = (float) Math.sin(rotateXAngle);
            float cosAlpha = (float) Math.cos(rotateXAngle);
            xRotation.m11 = cosAlpha;
            xRotation.m12 = -sinAlpha;
            xRotation.m21 = sinAlpha;
            xRotation.m22 = cosAlpha;

            rotationUpdate.mul(xRotation);
        }
        if (rotateYAngle!=0) {
            Matrix4f yRotation = new Matrix4f().identity();
            float sinBeta = (float) Math.sin(rotateYAngle);
            float cosBeta = (float) Math.cos(rotateYAngle);
            yRotation.m00 = cosBeta;
            yRotation.m02 = sinBeta;
            yRotation.m20 = -sinBeta;
            yRotation.m22 = cosBeta;

            rotationUpdate.mul(yRotation);
        }
        if (rotateZAngle!=0) {
            Matrix4f zRotation = new Matrix4f().identity();
            float sinGamma = (float) Math.sin(rotateZAngle);
            float cosGamma = (float) Math.cos(rotateZAngle);
            zRotation.m00 = cosGamma;
            zRotation.m01 = -sinGamma;
            zRotation.m10 = sinGamma;
            zRotation.m11 = cosGamma;

            rotationUpdate.mul(zRotation);
        }



        for (GameItem gameItem : moving) {

            // Update scale
            float scale = gameItem.getScale();
            scale += scaleInc * 0.05f;
            if ( scale < 0 ) {
                scale = 0;
            }
            gameItem.setScale(scale);


            // Update rotations
            //gameItem.addRotation((float) rotateXDirection, (float) rotateYDirection, (float) rotateZDirection);
            gameItem.addRotation(rotationUpdate);
        }
    }

    @Override
    public void render(MainWindow mainWindow) {

        renderer.render(mainWindow, camera, gameItems);

    }

    @Override
    public void cleanup() {
        renderer.cleanup();
        for (GameItem gameItem : gameItems) {
            gameItem.getMesh().cleanUp();
        }
    }

    public static void getRotationUpdate(float x, float y, float z) {


    }
}

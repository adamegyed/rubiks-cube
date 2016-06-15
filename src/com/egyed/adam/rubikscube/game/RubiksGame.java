package com.egyed.adam.rubikscube.game;

import com.egyed.adam.rubikscube.engine.GameItem;
import com.egyed.adam.rubikscube.engine.GameLogic;
import com.egyed.adam.rubikscube.engine.MainWindow;
import com.egyed.adam.rubikscube.engine.graphics.Camera;
import com.egyed.adam.rubikscube.game.piece.CenterPiece;
import com.egyed.adam.rubikscube.game.piece.CornerPiece;
import com.egyed.adam.rubikscube.game.piece.EdgePiece;
import com.egyed.adam.rubikscube.game.piece.Piece;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.*;

/**
 * Created by Adam on 5/11/16.
 * Game Logic for Rubik's Cube
 */
public class RubiksGame implements GameLogic {

    private final Renderer renderer;

    private GameItem[] gameItems;

    private Camera camera;

    private PieceManager pieceManager;

    private boolean inManeuver = false;
    private int stepsLeft = 0;
    private Matrix4f maneuverMultiplier;
    private LinkedList<Piece> piecesInManeuver;

    Random randGen = new Random();

    private final Vector3f cameraInc;
    private final Vector3f cameraRotInc;

    private boolean wireframe = false;
    private boolean wireframeMod = false;
    private boolean culling = true;
    private boolean cullingMod = false;

    private boolean slowedCamera = false;
    private boolean slowedCameraMod = false;

    // Distances each should move per step
    private static final float CAMERA_POS_STEP = 0.05f;
    private static final float CAMERA_ROT_STEP = 2.0f;
    private static final float SHIFT_STEP = 3f;

    // TYPES OF MANEUVERS
    private static final int SHIFT = 0;
    private static final int ROTATE = 1;


    public RubiksGame() {
        renderer = new Renderer();
        camera = new Camera();
        cameraInc = new Vector3f(0,0,0);
        cameraRotInc = new Vector3f(0,0,0);
        pieceManager = new PieceManager();
    }


    @Override
    public void init(MainWindow mainWindow) throws Exception {
        renderer.init(mainWindow);

        ArrayList<GameItem> itemList = new ArrayList<>();

        class Coordinate {

            public Coordinate (int x, int y, int z) {
                this.x = x;
                this.y = y;
                this.z = z;
            }

            int x;
            int y;
            int z;
        }

        LinkedList<Coordinate> coords = new LinkedList<Coordinate>();

        // First set of center pieces
        coords.add(new Coordinate(1,1,0));
        coords.add(new Coordinate(1,0,1));
        coords.add(new Coordinate(1,1,2));
        coords.add(new Coordinate(1,2,1));

        // Next 2 center pieces
        coords.add(new Coordinate(0,1,1));
        coords.add(new Coordinate(2,1,1));

        //First set of edge pieces (front face)
        coords.add(new Coordinate(0,1,0));
        coords.add(new Coordinate(1,0,0));
        coords.add(new Coordinate(2,1,0));
        coords.add(new Coordinate(1,2,0));

        // Second set of edge pieces (back face)
        coords.add(new Coordinate(0,1,2));
        coords.add(new Coordinate(1,0,2));
        coords.add(new Coordinate(2,1,2));
        coords.add(new Coordinate(1,2,2));

        // Third set of edge pieces (middle slice)
        coords.add(new Coordinate(0,0,1));
        coords.add(new Coordinate(2,0,1));
        coords.add(new Coordinate(2,2,1));
        coords.add(new Coordinate(0,2,1));

        // First set of Corner pieces (front face)
        coords.add(new Coordinate(0,0,0));
        coords.add(new Coordinate(2,0,0));
        coords.add(new Coordinate(2,2,0));
        coords.add(new Coordinate(0,2,0));

        //Second set of corner pieces (back face)
        coords.add(new Coordinate(0,2,2));
        coords.add(new Coordinate(0,0,2));
        coords.add(new Coordinate(2,0,2));
        coords.add(new Coordinate(2,2,2));

        Iterator<Coordinate> coordIterator = coords.iterator();


        for (int i = 0; i < 4; i++) {
            Piece center = CenterPiece.createCenterPiece(i);
            center.setPosition(0,0,-2);
            center.setRotationDegrees(i * 90,0,0);
            itemList.add(center);

            Coordinate c = coordIterator.next();
            pieceManager.addPiece(center,c.x,c.y,c.z);
        }

        Piece center1 = CenterPiece.createCenterPiece(Piece.ORANGE);
        center1.setPosition(0,0,-2);
        center1.setRotationDegrees(0,90,0);
        itemList.add(center1);

        Coordinate c1 = coordIterator.next();
        pieceManager.addPiece(center1,c1.x,c1.y,c1.z);

        Piece center2 = CenterPiece.createCenterPiece(Piece.WHITE);
        center2.setPosition(0,0,-2);
        center2.setRotationDegrees(0,270,0);
        itemList.add(center2);

        Coordinate c2 = coordIterator.next();
        pieceManager.addPiece(center2,c2.x,c2.y,c2.z);


        int[] colors = {Piece.ORANGE,Piece.GREEN,Piece.WHITE,Piece.YELLOW};
        for (int i = 0; i < 4; i++) {
            Piece edge = EdgePiece.createEdgePiece(Piece.RED,colors[i]);
            edge.setPosition(0,0,-2);
            edge.setRotationDegrees(0,0,90*i);
            itemList.add(edge);
            Coordinate c = coordIterator.next();
            pieceManager.addPiece(edge,c.x,c.y,c.z);
        }
        for (int i = 0; i < 4; i++) {
            Piece edge = EdgePiece.createEdgePiece(Piece.BLUE,colors[i]);
            edge.setPosition(0,0,-2);
            edge.addRotation(180,0,0);
            edge.addRotation(0,0,90*i);
            itemList.add(edge);
            Coordinate c = coordIterator.next();
            pieceManager.addPiece(edge,c.x,c.y,c.z);
        }
        int[] colors2 = {Piece.GREEN,Piece.WHITE,Piece.YELLOW,Piece.ORANGE};
        for (int i = 0; i < 4; i++) {
            Piece edge = EdgePiece.createEdgePiece(colors2[i],colors[i]);
            edge.setPosition(0,0,-2);
            edge.addRotation(90,0,0);
            edge.addRotation(0,0,90*i);
            itemList.add(edge);
            Coordinate c = coordIterator.next();
            pieceManager.addPiece(edge,c.x,c.y,c.z);
        }

        for (int i = 0; i < 4; i++) {
            Piece corner = CornerPiece.createCornerPiece(Piece.RED,colors[i],colors2[i]);
            corner.setPosition(0,0,-2);
            //corner.addRotation(0,0,0);
            corner.addRotation(0,0,90*i);
            itemList.add(corner);
            Coordinate c = coordIterator.next();
            pieceManager.addPiece(corner,c.x,c.y,c.z);
        }
        int[] colors3 = {Piece.YELLOW,Piece.ORANGE,Piece.GREEN,Piece.WHITE};
        for (int i = 0; i < 4; i++) {
            Piece corner = CornerPiece.createCornerPiece(Piece.BLUE,colors[i],colors3[i]);
            corner.setPosition(0,0,-2);
            corner.addRotation(180,0,0);
            corner.addRotation(0,0,90*i);
            itemList.add(corner);
            Coordinate c = coordIterator.next();
            pieceManager.addPiece(corner,c.x,c.y,c.z);
        }





        gameItems = new GameItem[itemList.size()];

        gameItems = itemList.toArray(gameItems);

        glClearColor(0.45f, 0.5f, 0.5f, 0.7f);
    }

    @Override
    public void input(MainWindow mainWindow) {
        cameraInc.set(0, 0, 0);
        cameraRotInc.set(0,0,0);

        if (!inManeuver) {

            if (mainWindow.isKeyPressed(GLFW_KEY_W)) {
                startManeuver(SHIFT,PieceManager.DOWN);
            }
            else if (mainWindow.isKeyPressed(GLFW_KEY_S)) {
                startManeuver(SHIFT,PieceManager.UP);
            }
            else if (mainWindow.isKeyPressed(GLFW_KEY_A)) {
                startManeuver(SHIFT,PieceManager.RIGHT);
            }
            else if (mainWindow.isKeyPressed(GLFW_KEY_D)) {
                startManeuver(SHIFT,PieceManager.LEFT);
            }
            else if (mainWindow.isKeyPressed(GLFW_KEY_Q)) {
                startManeuver(SHIFT,PieceManager.COUNTERCLOCKWISE);
            }
            else if (mainWindow.isKeyPressed(GLFW_KEY_E)) {
                startManeuver(SHIFT,PieceManager.CLOCKWISE);
            }
            else if (mainWindow.isKeyPressed(GLFW_KEY_I)) {
                startManeuver(ROTATE,PieceManager.DOWN);
            }
            else if (mainWindow.isKeyPressed(GLFW_KEY_K)) {
                startManeuver(ROTATE,PieceManager.UP);
            }
            else if (mainWindow.isKeyPressed(GLFW_KEY_J)) {
                startManeuver(ROTATE,PieceManager.RIGHT);
            }
            else if (mainWindow.isKeyPressed(GLFW_KEY_L)) {
                startManeuver(ROTATE,PieceManager.LEFT);
            }
            else if (mainWindow.isKeyPressed(GLFW_KEY_U)) {
                startManeuver(ROTATE,PieceManager.COUNTERCLOCKWISE);
            }
            else if (mainWindow.isKeyPressed(GLFW_KEY_O)) {
                startManeuver(ROTATE,PieceManager.CLOCKWISE);
            }


        }



        if (mainWindow.isKeyPressed(GLFW_KEY_UP)) {
            cameraInc.z = -1;
        } else if (mainWindow.isKeyPressed(GLFW_KEY_DOWN)) {
            cameraInc.z = 1;
        }
        if (mainWindow.isKeyPressed(GLFW_KEY_LEFT)) {
            cameraInc.x = -1;
        } else if (mainWindow.isKeyPressed(GLFW_KEY_RIGHT)) {
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
        if (mainWindow.isKeyPressed(GLFW_KEY_Y)) {

            if (!wireframe && !wireframeMod) {
                glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
                wireframe = true;
                wireframeMod = true;
            }
            else if (!wireframeMod){
                glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
                wireframe = false;
                wireframeMod = true;
            }
        }
        else if (mainWindow.isKeyReleased(GLFW_KEY_Y)) {
            wireframeMod = false;
        }

        if (mainWindow.isKeyPressed(GLFW_KEY_H)) {
            if (!culling && !cullingMod) {
                glEnable(GL_CULL_FACE);
                glCullFace(GL_BACK);
                culling = true;
                cullingMod = true;
            }
            else if (!cullingMod) {
                glDisable(GL_CULL_FACE);
                culling = false;
                cullingMod = true;
            }
        }
        else if (mainWindow.isKeyReleased(GLFW_KEY_H)) cullingMod = false;

        if (mainWindow.isKeyPressed(GLFW_KEY_LEFT_SHIFT)) {
            if (!slowedCameraMod) {
                slowedCamera = !slowedCamera;
                slowedCameraMod = true;
            }

        }
        else if (mainWindow.isKeyReleased(GLFW_KEY_LEFT_SHIFT)) slowedCameraMod = false;




        if (mainWindow.getShouldCameraReset()) {
            camera.setPosition(0,0,0);
            camera.setRotation(0,0,0);
            mainWindow.setShouldCameraReset(false);
        }
    }

    @Override
    public void update(float interval) {

        if (!slowedCamera) {
            camera.movePosition(cameraInc.x * CAMERA_POS_STEP, cameraInc.y * CAMERA_POS_STEP, cameraInc.z * CAMERA_POS_STEP);
            camera.moveRotation(cameraRotInc.x * CAMERA_ROT_STEP, cameraRotInc.y * CAMERA_ROT_STEP, 0);

        }
        else {
            camera.movePosition(cameraInc.x * CAMERA_POS_STEP * 0.15f, cameraInc.y * CAMERA_POS_STEP * 0.15f, cameraInc.z * CAMERA_POS_STEP * 0.15f);
            camera.moveRotation(cameraRotInc.x * CAMERA_ROT_STEP * 0.45f, cameraRotInc.y * CAMERA_ROT_STEP * 0.45f, 0);
        }


        if (stepsLeft==0) {
            inManeuver = false;
        }

        if (inManeuver) {

            piecesInManeuver.stream().forEach(piece -> {
                //if (piece==null) System.out.println("Piece is alread null");
                if (piece!=null) piece.addRotation(maneuverMultiplier);
            });

            stepsLeft--;
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
            if (gameItem!=null) gameItem.getMesh().cleanUp();
        }
    }

    private void startManeuver(int type, int direction) {
        inManeuver = true;
        stepsLeft = (int)( 90f / SHIFT_STEP);


        if (type==SHIFT) {
            if (direction==PieceManager.UP) {
                maneuverMultiplier = GameItem.getRotationMultiplier(SHIFT_STEP,0,0);
            }
            else if (direction==PieceManager.DOWN) {
                maneuverMultiplier = GameItem.getRotationMultiplier(-SHIFT_STEP,0,0);
            }
            else if (direction==PieceManager.LEFT) {
                maneuverMultiplier = GameItem.getRotationMultiplier(0,SHIFT_STEP,0);
            }
            else if (direction==PieceManager.RIGHT) {
                maneuverMultiplier = GameItem.getRotationMultiplier(0,-SHIFT_STEP,0);
            }
            else if (direction==PieceManager.CLOCKWISE) {
                maneuverMultiplier = GameItem.getRotationMultiplier(0,0,SHIFT_STEP);
            }
            else if (direction==PieceManager.COUNTERCLOCKWISE) {
                maneuverMultiplier = GameItem.getRotationMultiplier(0,0,-SHIFT_STEP);
            }
            piecesInManeuver = pieceManager.getAllPieces();
            pieceManager.shiftAll(direction);



        }
        else if (type==ROTATE) {
            if (direction==PieceManager.UP) {
                maneuverMultiplier = GameItem.getRotationMultiplier(-SHIFT_STEP,0,0);
                piecesInManeuver = pieceManager.getVerticalSlice();

            }
            else if (direction==PieceManager.DOWN) {
                maneuverMultiplier = GameItem.getRotationMultiplier(SHIFT_STEP,0,0);
                piecesInManeuver = pieceManager.getVerticalSlice();
            }
            else if (direction==PieceManager.LEFT) {
                maneuverMultiplier = GameItem.getRotationMultiplier(0,-SHIFT_STEP,0);
                piecesInManeuver = pieceManager.getHorizontalSlice();
            }
            else if (direction==PieceManager.RIGHT) {
                maneuverMultiplier = GameItem.getRotationMultiplier(0,SHIFT_STEP,0);
                piecesInManeuver = pieceManager.getHorizontalSlice();
            }
            else if (direction==PieceManager.CLOCKWISE) {
                maneuverMultiplier = GameItem.getRotationMultiplier(0,0,SHIFT_STEP);
                piecesInManeuver = pieceManager.getFrontFace();
            }
            else if (direction==PieceManager.COUNTERCLOCKWISE) {
                maneuverMultiplier = GameItem.getRotationMultiplier(0,0,-SHIFT_STEP);
                piecesInManeuver = pieceManager.getFrontFace();
            }

            pieceManager.rotate(direction);
        }

    }

}

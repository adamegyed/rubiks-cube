package com.egyed.adam.rubikscube.game.piece;

import com.egyed.adam.rubikscube.engine.graphics.Mesh;

/**
 * Created by Adam on 5/26/16.
 */
public class CornerPiece extends Piece {

    public static CornerPiece createCornerPiece(int frontColor, int sideColor, int topColor) {


        float[] positions = new float[]{
                //Grey Cube
                // VO
                -0.5f, 0.2f,  0.5f,
                // V1
                -0.2f, 0.5f,  0.5f,
                // V2
                -0.5f, 0.5f,  0.5f,
                // V3
                -0.2f,  0.2f,  0.5f,
                // V4
                -0.5f, 0.2f,  0.2f,
                // V5
                -0.2f, 0.5f,  0.2f,
                // V6
                -0.5f, 0.5f,  0.2f,
                // V7
                -0.2f,  0.2f,  0.2f,

                //Colored Front Panel
                // V8
                -0.49f, 0.21f,  0.502f,
                // V9
                -0.21f, 0.49f,  0.502f,
                // V10
                -0.49f, 0.49f,  0.502f,
                // V11
                -0.21f,  0.21f,  0.502f,

                //Colored Side Panel
                // V12
                -0.502f, 0.21f,  0.21f,
                // V13
                -0.502f, 0.49f,  0.49f,
                // V14
                -0.502f, 0.49f,  0.21f,
                // V15
                -0.502f,  0.21f,  0.49f,

                //Colored Top Panel
                // V16
                -0.49f, 0.502f,  0.49f,
                // V17
                -0.21f, 0.502f,  0.21f,
                // V18
                -0.49f, 0.502f,  0.21f,
                // V19
                -0.21f,  0.502f,  0.49f,


        };
        float[] colors = new float[]{
                0.42f, 0.4f, 0.4f,
                0.4f, 0.42f, 0.4f,
                0.4f, 0.4f, 0.42f,
                0.42f, 0.42f, 0.4f,
                0.42f, 0.42f, 0.42f,
                0.4f, 0.42f, 0.42f,
                0.4f, 0.4f, 0.4f,
                0.42f, 0.4f, 0.4f,
                // Front Panel
                1.0f, 0.0f, 0.0f,
                1.0f, 0.0f, 0.0f,
                1.0f, 0.0f, 0.0f,
                1.0f, 0.0f, 0.0f,
                //Side Panel
                1.0f, 0.0f, 0.0f,
                1.0f, 0.0f, 0.0f,
                1.0f, 0.0f, 0.0f,
                1.0f, 0.0f, 0.0f,
                // Top Panel
                1.0f, 0.0f, 0.0f,
                1.0f, 0.0f, 0.0f,
                1.0f, 0.0f, 0.0f,
                1.0f, 0.0f, 0.0f,

        };

        // FRONT PIECE RECOLOR

        if (frontColor==Piece.GREEN) {

            for (int i = 24; i <= 33; i+=3) {
                colors[i] = 0.0f;
                colors[i+1] = 1.0f;
                colors[i+2] = 0.0f;
            }

        }
        else if (frontColor==Piece.BLUE) {
            for (int i = 24; i <= 33; i+=3) {
                colors[i] = 0.0f;
                colors[i+1] = 0.0f;
                colors[i+2] = 1.0f;
            }

        }
        else if (frontColor==Piece.YELLOW) {
            for (int i = 24; i <= 33; i+=3) {
                colors[i] = 1.0f;
                colors[i+1] = 1.0f;
                colors[i+2] = 0.0f;
            }

        }
        else if (frontColor==Piece.ORANGE) {
            for (int i = 24; i <= 33; i+=3) {
                colors[i] = 1.0f;
                colors[i+1] = 0.5f;
                colors[i+2] = 0.0f;
            }

        }
        else if (frontColor==Piece.WHITE) {
            for (int i = 24; i <= 33; i+=3) {
                colors[i] = 1.0f;
                colors[i+1] = 1.0f;
                colors[i+2] = 1.0f;
            }

        }

        // SIDE PIECE RECOLOR

        if (sideColor==Piece.GREEN) {

            for (int i = 36; i <= 45; i+=3) {
                colors[i] = 0.0f;
                colors[i+1] = 1.0f;
                colors[i+2] = 0.0f;
            }

        }
        else if (sideColor==Piece.BLUE) {
            for (int i = 36; i <= 45; i+=3) {
                colors[i] = 0.0f;
                colors[i+1] = 0.0f;
                colors[i+2] = 1.0f;
            }

        }
        else if (sideColor==Piece.YELLOW) {
            for (int i = 36; i <= 45; i+=3) {
                colors[i] = 1.0f;
                colors[i+1] = 1.0f;
                colors[i+2] = 0.0f;
            }

        }
        else if (sideColor==Piece.ORANGE) {
            for (int i = 36; i <= 45; i+=3) {
                colors[i] = 1.0f;
                colors[i+1] = 0.5f;
                colors[i+2] = 0.0f;
            }

        }
        else if (sideColor==Piece.WHITE) {
            for (int i = 36; i <= 45; i+=3) {
                colors[i] = 1.0f;
                colors[i+1] = 1.0f;
                colors[i+2] = 1.0f;
            }

        }

        // TOP PIECE RECOLOR

        if (topColor==Piece.GREEN) {

            for (int i = 48; i <= 57; i+=3) {
                colors[i] = 0.0f;
                colors[i+1] = 1.0f;
                colors[i+2] = 0.0f;
            }

        }
        else if (topColor==Piece.BLUE) {
            for (int i = 48; i <= 57; i+=3) {
                colors[i] = 0.0f;
                colors[i+1] = 0.0f;
                colors[i+2] = 1.0f;
            }

        }
        else if (topColor==Piece.YELLOW) {
            for (int i = 48; i <= 57; i+=3) {
                colors[i] = 1.0f;
                colors[i+1] = 1.0f;
                colors[i+2] = 0.0f;
            }

        }
        else if (topColor==Piece.ORANGE) {
            for (int i = 48; i <= 57; i+=3) {
                colors[i] = 1.0f;
                colors[i+1] = 0.5f;
                colors[i+2] = 0.0f;
            }

        }
        else if (topColor==Piece.WHITE) {
            for (int i = 48; i <= 57; i+=3) {
                colors[i] = 1.0f;
                colors[i+1] = 1.0f;
                colors[i+2] = 1.0f;
            }

        }

        int[] indices = new int[]{
                //Front
                0,1,2, 0,3,1,
                //Top
                2,5,6, 2,1,5,
                //Back
                6,7,4, 6,5,7,
                //Bottom
                4,3,0, 4,7,3,
                //Left
                4,2,6, 4,0,2,
                //Right
                3,5,1, 3,7,5,
                //Front panel
                8,9,10, 8,11,9,
                //Side panel
                12,13,14, 12,15,13,
                //Top panel
                16,17,18, 16,19,17,
        };




        Mesh mesh = new Mesh(positions,colors,indices);
        return new CornerPiece(mesh);
    }

    public CornerPiece(Mesh mesh) {
        super(mesh);
    }
}

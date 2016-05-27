package com.egyed.adam.rubikscube.game.piece;
import com.egyed.adam.rubikscube.engine.graphics.Mesh;

/**
 * Created by Adam on 5/19/16.
 */
public class CenterPiece extends Piece {


    public static CenterPiece createCenterPiece(int frontColor) {

        float[] positions = new float[]{
                //Grey Cube
                // VO
                -0.15f, -0.15f,  0.5f,
                // V1
                0.15f, 0.15f,  0.5f,
                // V2
                -0.15f, 0.15f,  0.5f,
                // V3
                0.15f,  -0.15f,  0.5f,
                // V4
                -0.15f, -0.15f,  0.2f,
                // V5
                0.15f, 0.15f,  0.2f,
                // V6
                -0.15f, 0.15f,  0.2f,
                // V7
                0.15f,  -0.15f,  0.2f,

                //Colored Panel
                // V8
                -0.14f, -0.14f,  0.502f,
                // V9
                0.14f, 0.14f,  0.502f,
                // V10
                -0.14f, 0.14f,  0.502f,
                // V11
                0.14f,  -0.14f,  0.502f,

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

        };

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
                8,9,10, 8,11,9
        };




        Mesh mesh = new Mesh(positions,colors,indices);

        return new CenterPiece(mesh);

    }

    private CenterPiece(Mesh mesh) {
        super(mesh);

    }
}

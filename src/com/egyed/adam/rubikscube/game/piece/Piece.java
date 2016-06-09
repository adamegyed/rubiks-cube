package com.egyed.adam.rubikscube.game.piece;

import com.egyed.adam.rubikscube.engine.GameItem;
import com.egyed.adam.rubikscube.engine.graphics.Mesh;

/**
 * Created by Adam on 5/19/16.
 */
public class Piece extends GameItem {

    public static final int RED = 0;
    public static final int GREEN = 1;
    public static final int BLUE = 2;
    public static final int YELLOW = 3;
    public static final int ORANGE = 4;
    public static final int WHITE = 5;





    public Piece(Mesh mesh) {
        super(mesh);
    }


}

package com.egyed.adam.rubikscube.game;

import com.egyed.adam.rubikscube.game.piece.Piece;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Adam on 5/27/16.
 */
public class PieceManager {

    public static final int UP = 0;
    public static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    public static final int CLOCKWISE = 4;
    public static final int COUNTERCLOCKWISE = 5;


    private Piece[][][] pieces;

    private LinkedList<Piece> allPieces;

    public PieceManager() {
        super();

        pieces = new Piece[3][3][3];

        allPieces = new LinkedList<>();

    }


    public void addPiece(Piece piece, int xCoord, int yCoord, int zCoord) {
        allPieces.add(piece);
        pieces[xCoord][yCoord][zCoord] = piece;
    }


    public void shiftAll(int direction) {

        Piece[][][] shifted = new Piece[3][3][3];

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                for (int z = 0; z < 3; z++) {
                    shifted[x][y][z] = pieces[x][y][z];
                }
            }
        }

        if (direction==UP) {

            for (int x = 0; x < 3; x++) {
                for (int y = 0; y < 3; y++) {

                    // Front Face Shift
                    shifted[x][0][2-y] = pieces[x][y][0];
                    // Back Face
                    shifted[x][2][2-y] = pieces[x][y][2];
                    //Side 1


                }

                shifted[x][1][2] = pieces[x][0][1];
                shifted[x][1][0] = pieces[x][2][1];
            }
            
        }
        else if (direction == DOWN) {
            for (int x = 0; x < 3; x++) {
                for (int y = 0; y < 3; y++) {

                    // Front Face Shift
                    shifted[x][2][y] = pieces[x][y][0];
                    // Back Face
                    shifted[x][0][y] = pieces[x][y][2];
                    //Side 1


                }

                // Top Stripe
                shifted[x][1][0] = pieces[x][0][1];
                // Bottom Stripe
                shifted[x][1][2] = pieces[x][2][1];
            }
        }
        else if (direction == RIGHT) {
            for (int y = 0; y < 3; y++) {
                for (int x = 0; x < 3; x++) {

                    // Front Face Shift
                    shifted[2][y][x] = pieces[x][y][0];
                    // Back Face
                    shifted[0][y][x] = pieces[x][y][2];


                }

                // Right Stripe
                shifted[1][y][2] = pieces[2][y][1];
                // Left Stripe
                shifted[1][y][0] = pieces[0][y][1];
            }
        }
        else if (direction == LEFT) {
            for (int y = 0; y < 3; y++) {
                for (int x = 0; x < 3; x++) {

                    // Front Face Shift
                    shifted[0][y][2-x] = pieces[x][y][0];
                    // Back Face
                    shifted[2][y][2-x] = pieces[x][y][2];


                }

                // Right Stripe
                shifted[1][y][0] = pieces[2][y][1];
                // Left Stripe
                shifted[1][y][2] = pieces[0][y][1];
            }
        }
        else if (direction == CLOCKWISE) {
            for (int x = 0; x < 3; x++) {
                for (int y = 0; y < 3; y++) {
                    for (int z = 0; z < 3; z++) {
                        shifted[2-y][x][z] = pieces[x][y][z];
                        // x becomes 2 - y, y becomes x, and z remains constant
                    }

                }

            }
        }

        else if (direction == COUNTERCLOCKWISE) {
            for (int x = 0; x < 3; x++) {
                for (int y = 0; y < 3; y++) {
                    for (int z = 0; z < 3; z++) {
                        shifted[y][2-x][z] = pieces[x][y][z];
                    }

                }

            }
        }

        this.pieces = shifted;


    }

    public void rotate(int direction) {

        Piece[][][] modified = new Piece[3][3][3];

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                for (int z = 0; z < 3; z++) {
                    modified[x][y][z] = pieces[x][y][z];
                }
            }
        }

        if (direction==UP) {
            for (int y = 0; y < 3; y++) {
                for (int z = 0; z < 3; z++) {
                    modified[1][z][2-y] = pieces[1][y][z];
                }
            }
        }
        else if (direction==DOWN) {
            for (int y = 0; y < 3; y++) {
                for (int z = 0; z < 3; z++) {
                    modified[1][2-z][y] = pieces[1][y][z];
                }
            }
        }
        else if (direction==LEFT) {
            for (int x = 0; x < 3; x++) {
                for (int z = 0; z < 3; z++) {
                    modified[2-z][1][x] = pieces[x][1][z];
                }
            }

        }
        else if (direction==RIGHT) {
            for (int x = 0; x < 3; x++) {
                for (int z = 0; z < 3; z++) {
                    modified[z][1][2-x] = pieces[x][1][z];
                }
            }

        }
        else if (direction==CLOCKWISE) {
            for (int x = 0; x < 3; x++) {
                for (int y = 0; y < 3; y++) {
                    modified[2-y][x][0] = pieces[x][y][0];
                }
            }

        }
        else if (direction==COUNTERCLOCKWISE) {
            for (int x = 0; x < 3; x++) {
                for (int y = 0; y < 3; y++) {
                    modified[y][2-x][0] = pieces[x][y][0];
                }
            }

        }

        this.pieces = modified;
    }

    public LinkedList<Piece> getAllPieces() {
        return allPieces;
    }

    public LinkedList<Piece> getFrontFace() {
        LinkedList<Piece> frontFace = new LinkedList<>();

        int i = 0;
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3;y++) {
                frontFace.add(pieces[x][y][0]);
                //if (pieces[x][y][0]==null) System.out.println("Piecemanager could not find piece: ");
                i++;
                //System.out.println(i);
            }
        }

        return frontFace;
    }

    public LinkedList<Piece> getHorizontalSlice() {
        LinkedList<Piece> slice = new LinkedList<>();

        for (int x = 0; x < 3; x++) {
            for (int z = 0; z < 3; z++) {
                slice.add(pieces[x][1][z]);
            }
        }

        //System.out.println("Length of slices: "+slice.size());
        return slice;
    }

    public LinkedList<Piece> getVerticalSlice() {
        LinkedList<Piece> slice = new LinkedList<>();

        for (int z = 0; z < 3; z++) {
            for (int y = 0; y < 3;y++) {
                slice.add(pieces[1][y][z]);
            }
        }

        return slice;
    }

    public Piece getPiece(int x, int y, int z) {
        return pieces[x][y][z];
    }








}

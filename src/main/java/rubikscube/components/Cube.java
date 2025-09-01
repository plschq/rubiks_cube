package rubikscube.components;


import javafx.scene.layout.AnchorPane;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

import rubikscube.App;
import rubikscube.Config;
import rubikscube.Solver;
import rubikscube.dataclasses.*;


public final class Cube {

    public final AnchorPane turnAnchor = new AnchorPane();
    public final AnchorPane anchor = new AnchorPane(this.turnAnchor);
    public Piece[] pieces = new Piece[27];

    public final Side sideL = new Side(0);
    public final Side sideR = new Side(1);
    public final Side sideU = new Side(2);
    public final Side sideD = new Side(3);
    public final Side sideF = new Side(4);
    public final Side sideB = new Side(5);
    public final Side[] sides = new Side[] {
            this.sideL,
            this.sideR,
            this.sideU,
            this.sideD,
            this.sideF,
            this.sideB,
    };


    public Cube() {
        this.initPieces();
        this.initSides();
    }


    private void initPieces() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    Piece piece = new Piece(this, new XYZ(i - 1, j - 1, k - 1));
                    this.pieces[i * 9 + j * 3 + k] = piece;
                    this.anchor.getChildren().add(piece.anchor);
                }
            }
        }
    }

    private void initSides() {
        for (int i = 0; i < 6; i++) {
            this.sides[i].setCube(this);
        }
    }

    public Piece getPiece(XYZ position) {
        for (int i = 0; i < 27; i++) {
            if (XYZ.match(position, this.pieces[i].getPosition())) {
                return this.pieces[i];
            }
        }

        return null;
    }

    public void turn(Side side, TurnType turnType) {
        Turn.make(this.sides[side.id], turnType);
    }

    public void execute(Turn[] turns) {
        for (Turn turn : turns) {
            this.turn(this.sides[turn.side.id], turn.turnType);
        }
    }

    public String getSeed() {
        int edgesEncoded = 0;
        int cornersEncoded = 0;

        ArrayList<Integer> edgePositions = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11));
        ArrayList<Integer> cornerPositions = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7));

        int edgeFlipsSeed = 0;  // 2,048 possibilities
        int cornerTwistsSeed = 0;  // 2,187 possibilities
        int edgePermutationsSeed = 0;  // 479,001,600 possibilities (it can be 239,500,800 in theory)
        int cornerPermutationsSeed = 0;  // 40,320 possibilities

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    Piece piece = this.getPiece(new XYZ(i - 1, j - 1, k - 1));
                    assert piece != null;
                    int orientation = piece.getOrientation();

                    if (piece.isEdge && edgesEncoded < 11) {
                        edgeFlipsSeed += (int) Math.pow(2, edgesEncoded) * orientation;

                        int edgeId = piece.getTypeId();
                        int index = edgePositions.indexOf(edgeId);
                        edgePermutationsSeed += index;
                        edgePermutationsSeed *= edgePositions.size() - 1;
                        edgePositions.remove(Integer.valueOf(edgeId));

                        edgesEncoded++;
                    } else if (piece.isCorner && cornersEncoded < 7) {
                        cornerTwistsSeed += (int) Math.pow(3, cornersEncoded) * orientation;

                        int cornerId = piece.getTypeId();
                        int index = cornerPositions.indexOf(cornerId);
                        cornerPermutationsSeed += index;
                        cornerPermutationsSeed *= cornerPositions.size() - 1;
                        cornerPositions.remove(Integer.valueOf(cornerId));

                        cornersEncoded++;
                    }
                }
            }
        }

        BigInteger seed = BigInteger.valueOf(edgeFlipsSeed);
        seed = seed.multiply(BigInteger.valueOf(2_187)).add(BigInteger.valueOf(cornerTwistsSeed));
        seed = seed.multiply(BigInteger.valueOf(479_001_600)).add(BigInteger.valueOf(edgePermutationsSeed));
        seed = seed.multiply(BigInteger.valueOf(40_320)).add(BigInteger.valueOf(cornerPermutationsSeed));

        return App.toBaseN(Config.SEED_BASE, seed);
    }

    public void setSeed(String seed) {
        BigInteger seedBase10 = App.fromBaseN(Config.SEED_BASE, seed);
        BigInteger[] divisionResult;

        divisionResult = seedBase10.divideAndRemainder(BigInteger.valueOf(40_320));
        seedBase10 = divisionResult[0];
        int cornerPermutationsSeed = divisionResult[1].intValueExact();

        divisionResult = seedBase10.divideAndRemainder(BigInteger.valueOf(479_001_600));
        seedBase10 = divisionResult[0];
        int edgePermutationsSeed = divisionResult[1].intValueExact();

        divisionResult = seedBase10.divideAndRemainder(BigInteger.valueOf(2_187));
        int edgeFlipsSeed = divisionResult[0].intValueExact();
        int cornerTwistsSeed = divisionResult[1].intValueExact();

        ArrayList<Integer> edgePermutations = new ArrayList<>();
        ArrayList<Integer> edgePositions = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11));
        for (int i = 0; i < 11; i++) {
            int index = edgePermutationsSeed % (i + 2);
            edgePermutations.add(index);
            edgePermutationsSeed -= index;
            edgePermutationsSeed /= i + 2;
        }
        Collections.reverse(edgePermutations);
        for (int i = 0; i < 11; i++) {
            edgePermutations.set(i, edgePositions.get(edgePermutations.get(i)));
            edgePositions.remove(edgePermutations.get(i));
        }
        edgePermutations.add(edgePositions.get(0));

        ArrayList<Integer> cornerPermutations = new ArrayList<>();
        ArrayList<Integer> cornerPositions = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7));
        for (int i = 0; i < 7; i++) {
            int index = cornerPermutationsSeed % (i + 2);
            cornerPermutations.add(index);
            cornerPermutationsSeed -= index;
            cornerPermutationsSeed /= i + 2;
        }
        Collections.reverse(cornerPermutations);
        for (int i = 0; i < 7; i++) {
            cornerPermutations.set(i, cornerPositions.get(cornerPermutations.get(i)));
            cornerPositions.remove(cornerPermutations.get(i));
        }
        cornerPermutations.add(cornerPositions.get(0));

        ArrayList<Integer> edgeFlips = new ArrayList<>();
        int edgeFlipsSum = 0;
        for (int i = 0; i < 11; i++) {
            int orientation = edgeFlipsSeed % 2;
            edgeFlips.add(orientation);
            edgeFlipsSum += orientation;
            edgeFlipsSeed /= 2;
        }
        edgeFlips.add(edgeFlipsSum % 2);

        ArrayList<Integer> cornerTwists = new ArrayList<>();
        int cornerTwistsSum = 0;
        for (int i = 0; i < 7; i++) {
            int orientation = cornerTwistsSeed % 3;
            cornerTwists.add(orientation);
            cornerTwistsSum += orientation;
            cornerTwistsSeed /= 3;
        }
        cornerTwists.add(cornerTwistsSum % 3 == 0 ? 0 : 3 - cornerTwistsSum % 3);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    Piece piece = this.getPiece(new XYZ(i - 1, j - 1, k - 1));
                    assert piece != null;

                     if (piece.isEdge) {
                         piece.setTypeId(edgePermutations.get(0));
                         edgePermutations.remove(0);

                         piece.setOrientation(edgeFlips.get(0));
                         edgeFlips.remove(0);
                     } else if (piece.isCorner) {
                         piece.setTypeId(cornerPermutations.get(0));
                         cornerPermutations.remove(0);

                         piece.setOrientation(cornerTwists.get(0));
                         cornerTwists.remove(0);
                     }
                }
            }
        }
    }

    public void scramble(int turnsN) {
        assert turnsN >= 0;

        Axis lastTurnAxis = Axis.random();

        for (int i = 0; i < turnsN; i++) {
            Turn turn = Turn.random();
            while (turn.side.axis.id == lastTurnAxis.id || turn.turnType.value == -2) {
                turn = Turn.random();
            }
            lastTurnAxis = turn.side.axis;

            turn.side.setCube(this);
            turn.make();
        }
    }

    public void solve() {
        int[][][] cubeData = new int[6][3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cubeData[0][i][j] = Objects.requireNonNull(this.getPiece(new XYZ(-1, i - 1, 1 - j))).stickers[0].getColorSide().id + 1;
                cubeData[1][i][j] = Objects.requireNonNull(this.getPiece(new XYZ(+1, i - 1, j - 1))).stickers[0].getColorSide().id + 1;

                cubeData[2][i][j] = Objects.requireNonNull(this.getPiece(new XYZ(j - 1, -1, 1 - i))).stickers[1].getColorSide().id + 1;
                cubeData[3][i][j] = Objects.requireNonNull(this.getPiece(new XYZ(j - 1, +1, i - 1))).stickers[1].getColorSide().id + 1;

                cubeData[4][i][j] = Objects.requireNonNull(this.getPiece(new XYZ(j - 1, i - 1, -1))).stickers[2].getColorSide().id + 1;
                cubeData[5][i][j] = Objects.requireNonNull(this.getPiece(new XYZ(1 - j, i - 1, +1))).stickers[2].getColorSide().id + 1;
            }
        }

        String moves = Solver.solve(cubeData);
        Notation notation = new Notation(moves);
        this.execute(notation.parsed());
    }

}

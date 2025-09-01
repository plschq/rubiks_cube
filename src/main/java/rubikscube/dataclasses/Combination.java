package rubikscube.dataclasses;


public final class Combination {

    // Algorithms from https://ruwix.com/the-rubiks-cube/rubiks-cube-patterns-algorithms/
    // These are not all the patterns from this source
    // Some names are changed
    /* #0 */ public static final Notation SUPERFLIP = new Notation("U R2 F B R B2 R U2 L B2 R U' D' R2 F R' L B2 U2 F2");
    /* #1 */ public static final Notation CHECKERBOARD = new Notation("L2 R2 U2 D2 F2 B2");
    /* #2 */ public static final Notation WIRE = new Notation("R L F B R L F B R L F B R2 B2 L2 R2 B2 L2");
    /* #3 */ public static final Notation CHECKERBOARD_IN_THE_CUBE = new Notation("B D F' B' D L2 U L U' B D' R B R D' R L' F U2 D");
    /* #4 */ public static final Notation PERPENDICULAR_LINES = new Notation("R2 U2 L2 R2 U2 L2 U2 D2");
    /* #5 */ public static final Notation FLIPPED_CORNERS = new Notation("U B D' F2 D B' U' R2 D F2 D' R2 D F2 D' R2");
    /* #6 */ public static final Notation PLUS_MINUS = new Notation("U2 R2 L2 U2 R2 L2");
    /* #7 */ public static final Notation SPIRAL = new Notation("L' B' D U R U' R' D2 R2 D L D' L' R' F U");
    /* #8 */ public static final Notation VERTICAL_STRIPES = new Notation("F U F R L2 B D' R D2 L D' B R2 L F U F");
    /* #9 */ public static final Notation GIFT_BOX = new Notation("U B2 R2 B2 L2 F2 R2 D' F2 L2 B F' L F2 D U' R2 F' L' R'");
    /* #10 */ public static final Notation OPPOSITE_CORNERS = new Notation("R L U2 F2 D2 F2 R L F2 D2 B2 D2");
    /* #11 */ public static final Notation CROSSES = new Notation("R2 L' D F2 R' D' R' L U' D R D B2 R' U D2");
    /* #12 */ public static final Notation UNION_JACK = new Notation("U F B' L2 U2 L2 F' B U2 L2 U");
    /* #13 */ public static final Notation CUBE_IN_A_CUBE = new Notation("F L F U' R U F2 L2 U' L' B D' B' L2 U");
    /* #14 */ public static final Notation CUBE_IN_A_CUBE_IN_A_CUBE = new Notation("U' L' U' F' R2 B' R F U B2 U B' L U' F U R F'");
    /* #15 */ public static final Notation SNAKE = new Notation("L U B' U' R L' B R' F B' D R D' F'");
    /* #16 */ public static final Notation DOTS = new Notation("U D' R L' F B' U D'");
    /* #17 */ public static final Notation TWISTER = new Notation("F R' U L F' L' F U' R U L' U' L F'");
    /* #18 */ public static final Notation KLIT = new Notation("U' R2 L2 F2 B2 U' R L F B' U F2 D2 R2 L2 F2 U2 F2 U' F2");
    /* #19 */ public static final Notation HI_AROUND = new Notation("U2 R2 F2 U2 D2 F2 L2 U2");
    /* #20 */ public static final Notation HI_AGAIN = new Notation("U2 D2 L2 U2 D2 R2 F2 B2 L2 F2 B2 R2 U2 D2 F2 U2 D2 B2");
    /* #21 */ public static final Notation DISPLACED_MOTIF = new Notation("L2 B2 D' B2 D L2 U R2 D R2 B U R' F2 R U' B' U'");
    /* #22 */ public static final Notation C_U_AROUND = new Notation("U' B2 U L2 D L2 R2 D' B' R D' L R' B2 U2 F' L' U'");
    /* #23 */ public static final Notation OPPOSITE_PILLARS = new Notation("R2 F2 L2 R2 F2 L2");
    /* #24 */ public static final Notation VIADUCT = new Notation("R2 U2 L2 D B2 L2 B2 R2 D' U L' D F' U' R2 F' U B2 U2 R'");
    /* #25 */ public static final Notation SOLVED_IN_SCRAMBLED = new Notation("U2 L' U2 L' U2 F2 D L F' L F R2 D' B2 D' L2 D' L2 F2 R2");
    /* #26 */ public static final Notation STAIRCASE = new Notation("L2 F2 D' L2 B2 D' U' R2 B2 U' L' B2 L D L B' D L' U");
    /* #27 */ public static final Notation WRAPPED_2X2 = new Notation("D' B2 F2 L2 U' F2 R2 D F2 U2 L' B R' U' L' F D' F L D2");
    /* #28 */ public static final Notation FLOWER_FIELD = new Notation("L R' D U' B F' L R L2 U2 D2 F2 B2");
    /* #29 */ public static final Notation QUOTE = new Notation("U2 F2 D2 B2 R2 U2 B2 R2 U2 R2");

    // Other combinations
    /* #30 */ public static final Notation REVERSE = new Notation("U R U2 R F2 L U2 R F' B' R2 D R' L U2 F2 D2 F R2 D");

    public static final Notation TEST = new Notation("R U R' U'");
    public static final Notation[] ALL = new Notation[] {
            SUPERFLIP, CHECKERBOARD, WIRE, CHECKERBOARD_IN_THE_CUBE, PERPENDICULAR_LINES, FLIPPED_CORNERS, PLUS_MINUS,
            SPIRAL, VERTICAL_STRIPES, GIFT_BOX, OPPOSITE_CORNERS, CROSSES, UNION_JACK, CUBE_IN_A_CUBE,
            CUBE_IN_A_CUBE_IN_A_CUBE, SNAKE, DOTS, TWISTER, KLIT, HI_AROUND, HI_AGAIN, DISPLACED_MOTIF, C_U_AROUND,
            OPPOSITE_PILLARS, VIADUCT, SOLVED_IN_SCRAMBLED, STAIRCASE, WRAPPED_2X2, FLOWER_FIELD, QUOTE, REVERSE,
    };

}

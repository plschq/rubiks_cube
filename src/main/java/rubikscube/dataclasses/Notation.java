package rubikscube.dataclasses;


import java.util.Objects;


public final class Notation {

    private String notation;


    public Notation(String notation) {
        this.notation = notation;
    }


    private static TurnType parseTurnType(String str) {
        return switch (str) {
            case "'" -> TurnType.B;
            case "2", "2'" -> TurnType.D;
            default -> TurnType.F;
        };
    }

    private static Turn[] parse(String notation) {
        String[] array = notation.split(" ");
        Turn[] parsed = new Turn[array.length];

        for (int i = 0; i < array.length; i++) {
            String move = array[i];
            char sideLetter = move.charAt(0);
            String turnType = move.substring(1);
            parsed[i] = new Turn(new Side(sideLetter), Notation.parseTurnType(turnType));
        }

        return parsed;
    }

    private static String simplify(String notation) {
        final String[] N = new String[] {
                "X X'",
                "X' X",
                "X2 X2",
                "X2 X2'",
                "X2' X2",
                "X2' X2'"
        };
        final String[] F = new String[] {
                "X' X2",
                "X' X2'",
                "X2 X'",
                "X2' X'"
        };
        final String[] B = new String[] {
                "X X2",
                "X X2'",
                "X2 X",
                "X2' X"
        };

        String prevNotation;
        do {
            prevNotation = notation;
            for (String i : new String[] {"L", "R", "U", "D", "F", "B"}) {
                for (String j : N) {
                    notation = notation.replace(j.replace("X", i) + " ", "");
                }
                for (String j : F) {
                    notation = notation.replace(j.replace("X", i), i);
                }
                for (String j : B) {
                    notation = notation.replace(j.replace("X", i), i + "'");
                }

                notation = notation.replace(i + " " + i, i + "2");
                notation = notation.replace(i + "' " + i + "'", i + "2'");
            }
        } while (!Objects.equals(prevNotation, notation));

        return notation;
    }

    public String raw() {
        return this.notation;
    }

    public Turn[] parsed() {
        return parse(simplify(this.notation));
    }

    public void add(String moves) {
        this.notation += " " + moves;
    }

}

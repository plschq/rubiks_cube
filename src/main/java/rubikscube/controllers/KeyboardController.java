package rubikscube.controllers;


import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;

import rubikscube.App;
import rubikscube.Config;
import rubikscube.components.Cube;
import rubikscube.dataclasses.Combination;
import rubikscube.dataclasses.Notation;
import rubikscube.dataclasses.Turn;

import java.util.Scanner;


public final class KeyboardController extends Controller {

    public static void applyTo(Cube cube) {
        App.scene.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (KeyCombination.keyCombination("s").match(event)) {
                System.out.println("Scrambling the cube...");
                cube.scramble(20);
            } else if (KeyCombination.keyCombination("r").match(event)) {
                cube.setSeed("0");
                System.out.println("The cube has been reset to its solved state");
            } else if (KeyCombination.keyCombination("c").match(event)) {
                Turn.queue.clear();
                System.out.println("All pending turns have been cleared");
            } else if (KeyCombination.keyCombination("i").match(event)) {
                System.out.println("Current cube's seed is " + cube.getSeed());
                cube.setSeed(cube.getSeed());
            } else if (KeyCombination.keyCombination("Up").match(event)) {
                String seed = cube.getSeed();
                App.savedSeed = seed;
                System.out.println("Saved current cube's state (seed: " + seed + ")");
            } else if (KeyCombination.keyCombination("Down").match(event)) {
                if (App.savedSeed != null) {
                    cube.setSeed(App.savedSeed);
                    System.out.println("Loaded saved cube's state (seed: " + cube.getSeed() + ")");
                } else {
                    System.out.println("\033[0;31mThere is no saved cube's state at the moment. " +
                            "You can save current state by pressing Up key.\033[0m");
                }
            } else if (KeyCombination.keyCombination("/").match(event)) {
                System.out.print("Enter cube's seed: ");
                Scanner scanner = new Scanner(System.in);
                String seed = scanner.nextLine();
                cube.setSeed(seed);
            } else if (KeyCombination.keyCombination("t").match(event) && Config.DEV_MODE) {
                System.out.println("[DEV] Executing test sequence of moves");
                cube.execute(Combination.TEST.parsed());
            } else if (KeyCombination.keyCombination("Space").match(event)) {
                System.out.println("Solving the cube...");
                cube.solve();
            } else if (KeyCombination.keyCombination("Right").match(event)) {
                if (App.turnDuration <= Config.TURN_DURATION_DELTA) {
                    System.out.println("\033[0;31mTurn duration must be a positive number\033[0m");
                    return;
                }
                App.turnDuration -= Config.TURN_DURATION_DELTA;
                System.out.println("Turn duration has been set to " + App.turnDuration + "ms");
            } else if (KeyCombination.keyCombination("Left").match(event)) {
                App.turnDuration += Config.TURN_DURATION_DELTA;
                System.out.println("Turn duration has been set to " + App.turnDuration + "ms");
            } else if (KeyCombination.keyCombination("x").match(event)) {
                System.out.print("Enter numerical combination ID: ");
                Scanner scanner = new Scanner(System.in);
                int combinationId = Integer.parseInt(scanner.nextLine());
                if (combinationId >= Combination.ALL.length) {
                    System.out.println("\033[0;31mCombination ID out of range. " +
                            "Largest possible value - " + (Combination.ALL.length - 1) + "\033[0m");
                    return;
                }
                Notation notation = Combination.ALL[combinationId];
                cube.execute(notation.parsed());
            }
        });
    }

}

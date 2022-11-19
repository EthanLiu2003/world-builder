package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import edu.princeton.cs.algs4.StdDraw;


import java.awt.*;
import java.util.Random;


public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    private ZaWarudo world;
    private Random seed;
    private Interact interact;
    private int xPos;
    private int yPos;

    public Engine() {
    }

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        // renders world for each move...
        // get the input keys i guess
        StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
        Font bigFont = new Font("Arial", Font.PLAIN, 40);

        StdDraw.setPenColor(new Color(246, 74, 170));
        StdDraw.setFont(bigFont);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
        StdDraw.text(WIDTH / 2, HEIGHT - 5, "CS61B: THE GAME");
        Font smallFont = new Font("Arial", Font.PLAIN, 20);
        StdDraw.setPenColor(new Color(246, 74, 170));
        StdDraw.setFont(smallFont);
        StdDraw.text(WIDTH / 2, HEIGHT - 15, "New Game (N)");
        StdDraw.text(WIDTH / 2, HEIGHT - 16.5, "Load Game (L)");
        StdDraw.text(WIDTH / 2, HEIGHT - 18, "Quit (Q)");
        StdDraw.show();

        TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];
        world = new ZaWarudo(finalWorldFrame, seed, HEIGHT, WIDTH);
        world.generateEmptyWorld(seed, finalWorldFrame, WIDTH, HEIGHT);

        while (true) { // don't need to break since user always entering key
            if (StdDraw.hasNextKeyTyped()) {
                char in = StdDraw.nextKeyTyped();
                if (in == 'n' || in == 'N') {
                    StdDraw.clear(Color.BLACK);
                    StdDraw.text(WIDTH / 2, HEIGHT / 2, "Please enter a seed: ");
                    StdDraw.show();
                    String seedTot = "";
                    while (true) {
                        if (StdDraw.hasNextKeyTyped()) {
                            char seedIn = StdDraw.nextKeyTyped();
                            if ((seedIn == 's' || seedIn == 'S')) {
                                seedTot += Character.toString(seedIn);
                                System.out.println("Seed: " + seedTot);
                                world.world = interactWithInputString(seedTot);
                                break;
                            } else {
                                seedTot += Character.toString(seedIn);
                                StdDraw.text(WIDTH / 2 - 5, HEIGHT / 2 - 5, "");
                                StdDraw.clear(Color.BLACK);
                                StdDraw.text(WIDTH / 2, HEIGHT / 2, "Please enter a seed: ");
                                StdDraw.text(WIDTH / 2 - 5, HEIGHT / 2 - 5, seedTot);
                                StdDraw.show();
                            }
                        }
                    }
                    Interact interact = new Interact(world);
                    Position p = interact.placeAvatar(WIDTH, HEIGHT);
                    ter.renderFrame(world.world);
                    while (true) {
                        xPos = (int) StdDraw.mouseX();
                        yPos = (int) StdDraw.mouseY();
                        String posDesc = "";
                        String saved = "";
                        if (xPos > -1 && xPos < WIDTH && yPos > -1 && yPos < HEIGHT) {
                            StdDraw.setPenColor(new Color(246, 74, 170));
                            StdDraw.setFont(smallFont);
                            System.out.println("why not working?");
                            posDesc = world.world[xPos][yPos].description();
                            if (!posDesc.equals(saved)) {
                                saved = posDesc; // save old pos, compare to new one7
                                System.out.println(saved + posDesc);
                                StdDraw.textLeft(1, HEIGHT - 1, posDesc);
                                StdDraw.show();
                                ter.renderFrame(world.world); // only call render frame once?
                            }
                        }
                        if (StdDraw.hasNextKeyTyped()) { // calls the movement of avatar
                            char next = StdDraw.nextKeyTyped();
                            Position save = interact.move(next, p);
                            ter.renderFrame(world.world);
                            p = save;
                        }
                    }
                }
//                        if (in == 'L' || in == 'l') {
//                    // load saved game --> use In to load
//                }
                else if (in == ':') { // make sure it can quit while the game is being played
                    System.out.println("Trying to quit");
                    while (true) {
                        if (StdDraw.hasNextKeyTyped()) {
                            char next = StdDraw.nextKeyTyped();
                            if (next == 'Q' || next == 'q') {
                                // figure out how to save
                                System.exit(0);
                            }
                        }
                    }
                }
            }
        }
    }


    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     * <p>
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     * <p>
     * In other words, running both of these:
     * - interactWithInputString("n123sss:q")
     * - interactWithInputString("lww")
     * <p>
     * should yield the exact same world state as:
     * - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        // TODO: Fill out this method so that it run the engine using the input
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.
        String saveString = "";
        if (input.charAt(0) == 'N' || input.charAt(0) == 'n') {
            for (int i = 1; i < input.length(); i++) {
                char c = input.charAt(i);
                if (Character.isDigit(c)) {
                    saveString += c;
                }
            }
            long seedSave = Long.parseLong(saveString);
            seed = new Random((seedSave));
            TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];
            world = new ZaWarudo(finalWorldFrame, seed, HEIGHT, WIDTH);
            world.generateEmptyWorld(seed, finalWorldFrame, WIDTH, HEIGHT);
            world.fillWithRooms();
            world.connectRooms();
            world.addWalls();
        }
        //world.placeDoor(WIDTH, HEIGHT); // would have to add more conditions for going off the board here and not adding floor
        return world.world;
    }
}

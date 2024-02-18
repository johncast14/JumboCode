/*
 * GameDriver.java
 * Collaborators: Tamir Poindexter, Nancy Chen, John Castillo, Ohene Nkansah,
 *                Mark Zheng, Lawer Nyako
 * Catch It, Recycle It
 * Started: 2/17/2024
 * Finished: 2/18/2024
 * Purpose: The GameDriver class extends the GameGUI class and serves the purpose of
 *          handling the gameplay of Catch It, Recycle It. This class is capable of
 *          handling user input during runtime, displaying custom sprites, as well as
 *          managing game variables. See README for GPT documentation.
 */
package hackathon;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.io.FileWriter;
import java.io.PrintWriter;


public class GameDriver extends JPanel {

    //Miscellaneous Game Variables
    private static int score = 0;
    private Timer timer;
    private int addSpeed = 0;
    private volatile int healthPoints = 3;
    private volatile boolean lost = false;
    private volatile int fallSpeed = 10;

    //Frame Attributes
    public final int WIDTH = 400;
    public final int HEIGHT = 600;
    public final Image bgImage = Toolkit.getDefaultToolkit().createImage("src/hackathon/images/frame.png");
    public final Image bgImage_scaled = bgImage.getScaledInstance(WIDTH, HEIGHT, Image.SCALE_DEFAULT);

    //Trash Image Attributes
    private final int TRASH_SIZE = 50;
    private volatile int trashY = 0;
    private volatile boolean trashMoving = false;
    private final String[] TRASH_IMAGE_PATH = {"src/hackathon/images/paper1.png", "src/hackathon/images/paper2.png",
            "src/hackathon/images/paper3.png", "src/hackathon/images/plastic1.png",
            "src/hackathon/images/plastic2.png", "src/hackathon/images/plastic3.png", "src/hackathon/images/glass1.png",
            "src/hackathon/images/glass2.png", "src/hackathon/images/glass3.png", "src/hackathon/images/can1.png",
            "src/hackathon/images/can2.png", "src/hackathon/images/can3.png", "src/hackathon/images/elec1.png",
            "src/hackathon/images/elec2.png", "src/hackathon/images/elec3.png"};
    private volatile int trashX;
    private BufferedImage trashImage;
    private volatile int trashType;

    //Bin Image Attributes
    private final int TRASH_BINS = 5;
    private final int BIN_HEIGHT = 100;
    private final String[] BIN_IMAGE_PATH = {"src/hackathon/images/paperBin.png",
            "src/hackathon/images/plasticBin.png", "src/hackathon/images/glassBin.png",
            "src/hackathon/images/canBin.png", "src/hackathon/images/elecBin.png",
            "src/hackathon/images/xBin.png"};
    private final int[] binX = new int[TRASH_BINS];

    /**
     * @name: GameDriver()
     * @purpose: Parameterized constructor for the GameDriver() class.
     * @arguments: A boolean specifying whether to run the game
     * @returns: None
     * @effects: Executes the main game given that run_game is true
     */
    public GameDriver(boolean run_game) {

        if (!run_game) return;

        //Executes & Terminates Catch It, Recycle It
        this.startGame();
        this.endGame();

    }


    public void startGame() {

        lost = false;

        // Create JPanel interface
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        // Set the x-position of each bin
        for (int i = 0; i < TRASH_BINS; i++) {

            binX[i] = (WIDTH / TRASH_BINS) * i;

        }

        // Uses timer to respawn a new trash element
        timer = new Timer(100, e -> {

            //Terminates timer if the player has lost
            if (lost) {

                saveScoreToFile();
                //setVisible(false);
                this.endGame();

                timer.stop();

            }

            //Creates trash if none is on screen
            if (!trashMoving) {

                int section = new Random().nextInt(TRASH_BINS); // Choose a section randomly
                trashX = section * (WIDTH / TRASH_BINS) + (WIDTH / TRASH_BINS - TRASH_SIZE) / 2;
                trashY = 0;
                trashMoving = true;
                fallSpeed = 10 + addSpeed;
                trashType = new Random().nextInt(TRASH_BINS);
                loadTrashImage(trashType);

            }

            //Moves the trash
            else {

                trashY += fallSpeed;

                if (trashY >= HEIGHT - (TRASH_SIZE/2 + BIN_HEIGHT)) {

                    trashMoving = false;
                    checkTrashBinCollision();

                }

            }

            repaint();
            if (healthPoints == 0) lost = true;

            setVisible(true);
        });

        timer.start();
        while(!lost)
        {
            continue;
        }
        

    }

    /**
     * @name:      endGame()
     * @purpose:   Remove the current frame when the game ends
     * @arguments: None
     * @returns:   None
     * @effects:   Sets the current frame's visibility to false
     */
    public void endGame() {

        setVisible(false);

    }

    /**
     * @name:      loadTrashImage()
     * @purpose:   Load in and set the trashImage variable
     * @arguments: An integer which represents the current trashType
     * @returns:   None
     * @effects:   Throws an exception if the photo path name in TRASH_IMAGE_PATH
     *             is invalid, but does not terminate the function.
     */
    private void loadTrashImage(int trashType) {

        int randImg = new Random().nextInt(3);
        String imagePath = TRASH_IMAGE_PATH[trashType * 3 + randImg];

        try {

            trashImage = ImageIO.read(new File(imagePath));

        }
        catch (IOException e) {

            System.err.println("ERROR: Cannot open image file path \"" + imagePath + "\".\n");
            throw new RuntimeException(e.getMessage());

        }

    }

    /**
     * @name:      paintComponent()
     * @purpose:   Paint/Draw the components of the game;
     * @arguments: A graphics object, g
     * @returns:   None
     * @effects:   Calls other functions to draw game components
     */
    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        g.drawImage(bgImage_scaled, 0, 0, null);
        drawTrash(g);
        drawTrashBins(g);

    }

    /**
     * @name:      drawTrashBins()
     * @purpose:   Handle the drawing of the trash bins
     * @arguments: A graphics object, g
     * @returns:   None
     * @effects:   None
     */
    private void drawTrashBins(Graphics g) {

        BufferedImage binImage;
        int BIN_WIDTH = WIDTH / TRASH_BINS;

        for (int i = 0; i < TRASH_BINS; i++) {

            try {

                binImage = ImageIO.read(new File(BIN_IMAGE_PATH[i]));
                g.drawImage(binImage, binX[i], HEIGHT - BIN_HEIGHT, BIN_WIDTH, BIN_HEIGHT, this);

            }

            catch (IOException e) {

                System.err.println("Cannot find image path \"" + BIN_IMAGE_PATH[i] + "\".\n");
                throw new RuntimeException(e.getMessage());

            }

        }

    }

    /**
     * @name:      drawTrash()
     * @purpose:   Handle the drawing of the trash
     * @arguments: A graphics object, g
     * @returns:   None
     * @effects:   Only draws if there is a trash on screen & an image for it
     */
    private void drawTrash(Graphics g) {

        if (trashMoving && trashImage != null) {

            g.drawImage(trashImage, trashX, trashY, TRASH_SIZE, TRASH_SIZE, this);

        }

    }

    /**
     * @name:      checkTrashBinCollision()
     * @purpose:   Handles trash bin and trash collision
     * @arguments: A graphics object, g
     * @returns:   None
     * @effects:   None
     */
    private void checkTrashBinCollision() {

        int binIndex = trashX / (WIDTH / TRASH_BINS);

        if (binIndex == trashType) {

            score++;
            GameGUI.GamePanel.updateScore();

        }

        else {
            healthPoints--;
            String binName;

            if (trashType == 0) binName = "paper";
            else if (trashType == 1) binName = "plastic";
            else if (trashType == 2) binName = "glass";
            else if (trashType == 3) binName = "cans/aluminum";
            else binName = "electric waste";

            System.out.println("Wrong! That trash belongs in the " + binName + " bin!");

        }
        if (score > 0 && score % 5 == 0) fallSpeed += 5;

    }

    /**
     * @name:      moveLeft()
     * @purpose:   Handles left arrow key inputs
     * @arguments: None
     * @returns:   None
     * @effects:   Repaints assets when called
     */
    public void moveLeft() {

        if (trashY <= HEIGHT - (BIN_HEIGHT + TRASH_SIZE)) {

            if (trashX > 0) {

                if (!(trashX - 80 < 0)) trashX -= 80;

            }

        }

        repaint();

    }

    /**
     * @name:      moveRight()
     * @purpose:   Handle right arrow key inputs
     * @arguments: None
     * @returns:   None
     * @effects:   Repaints assets when called
     */
    public void moveRight() {

        if (trashY <= HEIGHT - (BIN_HEIGHT + TRASH_SIZE)) {

            if (trashX < WIDTH - TRASH_SIZE) {

                if (!(trashX + 80 > WIDTH)) trashX += 80;

            }

        }

        repaint();

    }

    /**
     * @name:      moveDown()
     * @purpose:   Handles down arrow key inputs
     * @arguments: None
     * @returns:   None
     * @effects:   Repaints assets when called
     */
    public void moveDown() {

        if (trashY <= HEIGHT - (BIN_HEIGHT + TRASH_SIZE)) fallSpeed += 5;

        repaint();

    }

    /**
     * @name:      getScore()
     * @purpose:   Returns the score
     * @arguments: None
     * @returns:   An integer, representing the score
     * @effects:   None
     */
    public static int getScore() {

        return score;

    }

    /**
     * @name:       stillPlaying()
     * @purpose:    Return whether the play() function is active
     * @arguments:  None
     * @returns:    A boolean, specifying if the play() function is active
     * @effects:    None
     */
    public boolean stillPlaying() {

        return !lost;

    }

    /**
     * @name:      saveScoreToFile()
     * @purpose:   Save the current score to the specified file
     * @arguments: A string, representing the name of the file to save the score to
     * @returns:   None
     * @effects:   Writes to, or creates a file within the current file structure.
     *             Throws an exception if the file could not be opened/written to.
     */
    private void saveScoreToFile() {

        try (PrintWriter writer = new PrintWriter(new FileWriter("Scores.txt", true))) {

            writer.println(score);
            writer.close();
            System.out.println("Score saved to file.");

        }
        catch (IOException e) {

            System.err.println("Error saving score to file \"Scores.txt\"\n");
            throw new RuntimeException(e.getMessage());

        }

    }

}

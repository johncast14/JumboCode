/*
 * GameGUI.java
 * Collaborators: Tamir Poindexter, Nancy Chen, John Castillo, Ohene Nkansah,
 *                Mark Zheng, Lawer Nyako
 * Catch It, Recycle It
 * Started: 2/17/2024
 * Finished: 2/18/2024
 * Purpose: The GameGUI class extends the JPanel class and serves as the Parent
 *          class for the GameDriver class. GameGUI serves as the main graphical
 *          user interface terminal for Catch It, Recycle It. It handles frame
 *          switching, user key inputs, as well as asset painting. This class
 *          contains the class definitions of multiple different classes, all
 *          having to do with GameGUI.
 */

package hackathon;

 import java.awt.*;
 import java.awt.event.*;
 import javax.imageio.ImageIO;
 import javax.swing.*;
 import javax.swing.border.EmptyBorder;
 import java.awt.image.BufferedImage;
 import java.io.IOException;
 import java.io.InputStream;
 import java.security.Key;
 import java.awt.event.ActionEvent;
 import java.awt.event.ActionListener;
 import java.awt.event.KeyAdapter;
 import java.awt.event.KeyEvent;
 import java.io.*;
 import java.util.ArrayList;
 import java.util.Collections;
 import java.util.List;

public class GameGUI extends JPanel implements KeyListener {

    // Color Palette
    private final Color main_bg = new Color(37, 33, 33);
    private final Color main_fg = Color.white;
    private final Color button_bg = Color.lightGray;
    private final Color button_fg = Color.black;

    // Fonts Palette
    InputStream is = GameGUI.class.getResourceAsStream("PixeloidSans.ttf");
    Font pixelFont = Font.createFont(Font.TRUETYPE_FONT, is);
    private Font main_font = new Font("Comic Sans MS", Font.BOLD, 68);
    private Font panel_font = new Font("Comic Sans MS", Font.BOLD, 45);
    private Font button_font = new Font("Comic Sans MS", Font.BOLD, 20);

    // JFrame & JPanel Attributes
    private final JFrame gameGUI = new JFrame();
    private final JPanel menuPanel = new MenuPanel();
    private JPanel gamePanel = new GamePanel(false);
    private final JPanel keyPanel = new KeyPanel();
    private final JPanel leaderPanel = new LeaderboardPanel();
    private final int WIDTH = 400;
    private final int HEIGHT = 675;

    /**
     * @name:      GameGUI()
     * @purpose:   Default constructor of the GameGUI class. Initializes frame attributes
     * @arguments: None
     * @returns:   None
     * @effects:   Throws IOExceptions and Font FormatExceptions based on the initializations
     *             of frame classes within constructor
     */
    public GameGUI() throws IOException, FontFormatException {

        gameGUI.setTitle("Catch It Recycle It");
        gameGUI.setResizable(false);

        gameGUI.add(menuPanel);
        gameGUI.add(keyPanel);
        gameGUI.add(gamePanel);
        gameGUI.add(leaderPanel);

        gameGUI.pack();
        gameGUI.setLocationRelativeTo(null);
        gameGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameGUI.setSize(WIDTH, HEIGHT);
        gameGUI.setVisible(true);

    }

    //Unimplemented functions
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    /*
     * MenuPanel
     * Catch It, Recycle It
     * Started: 2/17/2024
     * Finished: 2/18/2024
     * Purpose: The MenuPanel class is a class within the GameGUI class that
     *          is responsible for the Menu Frame of Catch It Recycle It.
     */
    public class MenuPanel extends JPanel implements ActionListener {

         // MenuPanel Attibutes
         private JLabel menuTitle;
         private JButton playButton, keyButton, leaderButton, exitButton;
         private JLabel creditsText;
         private int button_width = 165;
         private int button_height = 75;

        /**
         * name:      MenuPanel()
         * purpose:   Default constructor of the MenuPanel class
         * arguments: None
         * returns:   None
         * effects:   Initializes frame attributes.
         *            Throws an IOException if playImg cannot be read
         */
         public MenuPanel() throws IOException {

             setBackground(main_bg);
             setSize(new Dimension(400, 675));
             setLayout(null);


             BufferedImage barImg = ImageIO.read(new File("src/hackathon/images/cover.png"));
             Image barScaled = barImg.getScaledInstance(400, 250, Image.SCALE_DEFAULT);
             JLabel menuTitle = new JLabel(new ImageIcon(barScaled));

//             menuTitle = new JLabel("<html> <center> Catch It <br>Recycle It </center> </html");
//             setLook(menuTitle, main_fg, main_bg, main_font);

             BufferedImage playImg = ImageIO.read(new File("src/hackathon/images/menuPlay.png"));
             Image playScaled = playImg.getScaledInstance(button_width, button_height, Image.SCALE_DEFAULT);
             playButton = new JButton(new ImageIcon(playScaled));
             setLook(playButton, button_fg, button_bg, button_font);
             playButton.setFocusable(false);
             playButton.addActionListener(this);
             playButton.setBorder(BorderFactory.createEmptyBorder());
             playButton.setContentAreaFilled(false);

             BufferedImage keyImg = ImageIO.read(new File("src/hackathon/images/menuKey.png"));
             Image keyScaled = keyImg.getScaledInstance(button_width, button_height, Image.SCALE_DEFAULT);
             keyButton = new JButton(new ImageIcon(keyScaled));
             setLook(keyButton, button_fg, button_bg, button_font);
             keyButton.setFocusable(false);
             keyButton.addActionListener(this);

             BufferedImage leaderImg = ImageIO.read(new File("src/hackathon/images/menuScore.png"));
             Image leaderScaled = leaderImg.getScaledInstance(button_width, button_height, Image.SCALE_DEFAULT);
             leaderButton = new JButton(new ImageIcon(leaderScaled));
             setLook(leaderButton, button_fg, button_bg, button_font);
             leaderButton.setFocusable(false);
             leaderButton.addActionListener(this);

             BufferedImage exitImg = ImageIO.read(new File("src/hackathon/images/menuExit.png"));
             Image exitScaled = exitImg.getScaledInstance(button_width, button_height, Image.SCALE_DEFAULT);
             exitButton = new JButton(new ImageIcon(exitScaled));
             setLook(exitButton, button_fg, button_bg, button_font);
             exitButton.setFocusable(false);
             exitButton.addActionListener(this);

             creditsText = new JLabel("<html> @2024 JumboHack: John Castillo, Ohene Nkansah, Mark Zheng, <br>Tamir Poindexter, Lawer Nyako, Nancy Chen <br>.</html>");
             setLook(creditsText, main_fg, main_bg, new Font("Comic Sans MS", Font.PLAIN, 12));

             int title_width = (int) menuTitle.getPreferredSize().getWidth();
             menuTitle.setBounds(10, 30, title_width, 250);
             playButton.setBounds(20, 312, button_width, button_height);
             keyButton.setBounds(215, 312, button_width, button_height);
             leaderButton.setBounds(20, 412, button_width, button_height);
             exitButton.setBounds(215, 412, button_width, button_height);
             creditsText.setBounds(23, 585, (int) creditsText.getPreferredSize().getWidth(), 85);

             add(menuTitle);
             add(playButton);
             add(keyButton);
             add(leaderButton);
             add(exitButton);
             add(creditsText);

             setVisible(true);

         }

        /**
         * name:      actionPerformed()
         * purpose:   Handles button interactions within the MenuPanel class
         * arguments: An ActionEvent object, e
         * returns:   None
         * effects:   Manipulates frame attributes based on user input
         */
         @Override
         public void actionPerformed(ActionEvent e) {

             if (e.getSource() == playButton) {

                 menuPanel.setVisible(false);

                 try {

                     gamePanel = new GamePanel(true);

                 }

                 catch (IOException ex) {

                     throw new RuntimeException(ex);

                 }

                 gameGUI.add(gamePanel);
                 gamePanel.setVisible(true);
                 gamePanel.setFocusable(true);
                 gamePanel.requestFocus();

             }

             else if (e.getSource() == keyButton) {

                 menuPanel.setVisible(false);
                 keyPanel.setVisible(true);

             }

             else if (e.getSource() == leaderButton) {

                 menuPanel.setVisible(false);
                 leaderPanel.setVisible(true);

             }

             else if (e.getSource() == exitButton) {

                 gameGUI.dispose();

             }

         }

     }

    /*
     * GamePanel
     * Catch It, Recycle It
     * Started: 2/17/2024
     * Finished: 2/18/2024
     * Purpose: The GamePanel class is a class within the GameGUI class that
     *          is responsible for the Game Frame of Catch It Recycle It.
     */
     public class GamePanel extends JPanel implements KeyListener {

         //Frame Attributes
         public static JLabel score;
         public static GameDriver driver;
         public static int interface_score = 0;
         public static int heart_score = 3;

         public static JButton full1, full2, full3, empty1, empty2, empty3;

        /**
         * name:      GamePanel()
         * purpose:   Parameterized constructor for the GamePanel class
         * arguments: A boolean, that represents whether to run the GameDriver.
         * returns:   None
         * effects:   Throws an IO exception if barImg cannot be read
         */
         public GamePanel(boolean b) throws IOException {
             setBackground(main_bg);
             setSize(400, 75);
             setLayout(null);

             BufferedImage barImg = ImageIO.read(new File("src/hackathon/images/playBar.png"));
             Image barScaled = barImg.getScaledInstance(400, 75, Image.SCALE_DEFAULT);
             JLabel barLabel = new JLabel(new ImageIcon(barScaled));

             score = new JLabel("<html> <center> Score: " + interface_score + " HP: " + heart_score + "</center> </html>");
             setLook(score, main_fg, main_bg, button_font);
             score.setHorizontalAlignment(JLabel.CENTER);


             driver = new GameDriver(b);
             driver.setFocusable(true);

//             barLabel.setBounds(0, 0, 400, 75);
             score.setBounds(10, 10, 300, 75);
//             barLabel.setBounds(0, 0, 400, 75);
             driver.setBounds(0, 73, driver.WIDTH, driver.HEIGHT);
             this.addKeyListener(this);


//             add(barLabel);
             add(score);
             add(driver);

             setVisible(false);
         }

         /**
         * @name:      updateScore()
         * @purpose:   Update the score displayed by the GamePanel's  frame
         * @arguments: None
         * @returns:   None
         * @effects:   None
         */
         public static void updateScore() {

             interface_score++;
             score.setText("<html> <center> Score: " + interface_score + " </center> HP: " + heart_score + "</html>");

         }

        /**
         * @name:      updateHp()
         * @purpose:   Update the HP counter and reflect the change in the game frame
         * @arguments: None
         * @returns:   None
         * @effects:   None
         */
         public static void updateHp() {

             heart_score--;
             score.setText("<html> <center> Score: " + interface_score + " HP: " + heart_score + "</center> </html>");

         }

        /**
         * @name:      keyPressed()
         * @purpose:   Handle user keyboard inputs
         * @arguments: A KeyEvent object, e
         * @returns:   None
         * @effects:   None
         */
         @Override
         public void keyPressed(KeyEvent e) {

             if (driver.stillPlaying()) {

                 int keyCode = e.getKeyCode();

                 if (keyCode == KeyEvent.VK_LEFT) {

                     driver.moveLeft();

                 }

                 else if (keyCode == KeyEvent.VK_RIGHT) {

                     driver.moveRight();

                 }

                 else if (keyCode == KeyEvent.VK_DOWN) {

                     driver.moveDown();

                 }

             }

         }

         //Non-Implemented functions
         @Override
         public void keyReleased(KeyEvent e) {}

         @Override
         public void keyTyped(KeyEvent e) {}

        /**
         * @name:
         * @purpose:
         * @arguments:
         * @returns:
         * @effects:
         */
         public static void endGame() {
             driver.setVisible(false);
         }

        public JButton getButton(String path) throws IOException {
            BufferedImage backImg = ImageIO.read(new File(path));
            Image backScaled = backImg.getScaledInstance(24, 24, Image.SCALE_DEFAULT);
            JButton button = new JButton(new ImageIcon(backScaled));
            setLook(button, button_fg, button_bg, button_font);
            button.setFocusable(false);
            button.setMargin(new Insets(10, 5, 10, 5));
            button.setBorder(BorderFactory.createEmptyBorder());
            button.setContentAreaFilled(false);

            return button;
        }
     }

     public class KeyPanel extends JPanel implements MouseListener {
         public JLabel keyTitle, keyText;
         public JButton menuButton;
         public KeyPanel() throws IOException {
             setBackground(main_bg);
             setSize(400, 675);
             setLayout(new BorderLayout(50, 0));

             keyTitle = new JLabel("<html> <center> KEY </center> </html>");
             setLook(keyTitle, main_fg, main_bg, panel_font);
             keyTitle.setHorizontalAlignment(JLabel.CENTER);
             keyTitle.setSize(400, 75);
             keyTitle.setBorder(new EmptyBorder(20, 0, 20, 0));

             keyText = new JLabel("<html><center>Do you understand how to sort your trash " +
                     "<br>into their respective recycling bins?" +
                     "<br><br>To play this game, all you need are your 4 arrow " +
                     "<br>keys to control which bin the falling trash should " +
                     "<br>be placed in. " +
                     "<br><br>If you place the trash in the wrong bin 3 times, " +
                     "<br>the game is over. " +
                     "<br><br>If you place the trash in the right bin, " +
                     "<br>the game increases your score by 1." +
                     "<br><br>Your top 3 scores will be showcased on your " +
                     "<br>personal leaderboard." +
                     "<br><br>So good luck! </center></html>");
             setLook(keyText, main_fg, main_bg, new Font("Comic Sans MS", Font.PLAIN, 16));
             keyText.setSize(300, 425);

             BufferedImage backImg = ImageIO.read(new File("src/hackathon/images/backButton.png"));
             Image backScaled = backImg.getScaledInstance(150, 75, Image.SCALE_DEFAULT);
             menuButton = new JButton(new ImageIcon(backScaled));
             setLook(menuButton, button_fg, button_bg, button_font);
             menuButton.setFocusable(false);
             menuButton.addMouseListener(this);
             menuButton.setMargin(new Insets(10, 5, 10, 5));
             menuButton.setBorder(BorderFactory.createEmptyBorder());
             menuButton.setContentAreaFilled(false);

             JPanel temp_textPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
             temp_textPanel.setBackground(main_bg);
             temp_textPanel.setSize(350, 425);
             temp_textPanel.add(keyText);

             JPanel temp_menuPanel = new JPanel();
             temp_menuPanel.setBackground(main_bg);
             temp_menuPanel.setSize(350, 175);
             temp_menuPanel.add(menuButton);
             temp_menuPanel.setBorder(new EmptyBorder(10, 20, 60, 20) );

             add(keyTitle, BorderLayout.NORTH);
             add(temp_textPanel, BorderLayout.CENTER);
             add(temp_menuPanel, BorderLayout.SOUTH);

             setVisible(false);
         }

         @Override
         public void mouseClicked(MouseEvent e) {
             if (e.getSource() == menuButton) {
                 keyPanel.setVisible(false);
                 menuPanel.setVisible(true);
             }
         }

         @Override
         public void mousePressed(MouseEvent e) {}

         @Override
         public void mouseReleased(MouseEvent e) {}

         @Override
         public void mouseEntered(MouseEvent e) {}

         @Override
         public void mouseExited(MouseEvent e) {}
     }

     public class LeaderboardPanel extends JPanel implements MouseListener{
         private static int myInterfaceScore;
         private List<Integer> scores;


         private JLabel scoreTitle, scoresText;
         private JButton menuButton;

         public LeaderboardPanel() throws IOException {
             setBackground(main_bg);
             setSize(new Dimension(WIDTH, HEIGHT));
             setLayout(new BorderLayout(50, 0));
             scores = readScoresFromFile();
             Collections.sort(scores, Collections.reverseOrder());
             StringBuilder sb = new StringBuilder();
             for (int i = 0; i < scores.size(); i++) {
                 sb.append((i + 1)).append(". ").append(scores.get(i)).append("<br>");
             }

             System.out.println(scores);

             scoreTitle = new JLabel("<html> <center> TOP SCORES </center> </html>");
             setLook(scoreTitle, main_fg, main_bg, panel_font);
             scoreTitle.setHorizontalAlignment(JLabel.CENTER);
             scoreTitle.setSize(400, 75);
             scoreTitle.setBorder(new EmptyBorder(20, 0, 20, 0));

             scoresText = new JLabel("<html><center> " +
                     sb.toString() +
                     "</center></html>");
             setLook(scoresText, main_fg, main_bg, new Font("Comic Sans MS", Font.PLAIN, 16));
             scoreTitle.setSize(400, 200);

             // BACK BUTTON
             BufferedImage backImg = ImageIO.read(new File("src/hackathon/images/backButton.png"));
             Image backScaled = backImg.getScaledInstance(150, 75, Image.SCALE_DEFAULT);
             menuButton = new JButton(new ImageIcon(backScaled));
             setLook(menuButton, button_fg, button_bg, button_font);
             menuButton.setFocusable(false);
             menuButton.addMouseListener(this);
             menuButton.setMargin(new Insets(10, 5, 10, 5));
             menuButton.setBorder(BorderFactory.createEmptyBorder());
             menuButton.setContentAreaFilled(false);

             JPanel temp_textPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
             temp_textPanel.setBackground(main_bg);
             temp_textPanel.setSize(350, 425);
             temp_textPanel.add(scoresText);

             JPanel temp_menuPanel = new JPanel();
             temp_menuPanel.setBackground(main_bg);
             temp_menuPanel.setSize(350, 175);
             temp_menuPanel.add(menuButton);
             temp_menuPanel.setBorder(new EmptyBorder(10, 20, 60, 20) );

             add(scoreTitle, BorderLayout.NORTH);
             add(temp_textPanel, BorderLayout.CENTER);
             add(temp_menuPanel, BorderLayout.SOUTH);

             setVisible(false);
         }

         private void addTheScore(int newScore) {
             scores.add(newScore);
             Collections.sort(scores, Collections.reverseOrder());
             if (scores.size() > 3) {
                 scores.remove(3); // Keep only the top 3 scores
             }
         }

         /*
          * readScoresFromFile
          * Input: None
          * Purpose: This function allows
          *
          */
         private List<Integer> readScoresFromFile() {
             List<Integer> scores = new ArrayList<>();
             try (BufferedReader reader = new BufferedReader(new FileReader("Scores.txt"))) {
                 String line;
                 while ((line = reader.readLine()) != null) {
                     scores.add(Integer.parseInt(line.trim()));
                 }
             } catch (IOException e) {
                 e.printStackTrace();
             }
             return scores;
         }

         public static void addScore(int x) {
             myInterfaceScore = x;
         }

         @Override
         public void mouseClicked(MouseEvent e) {
            if (e.getSource() == menuButton) {
                leaderPanel.setVisible(false);
                menuPanel.setVisible(true);
            }
         }

         @Override
         public void mousePressed(MouseEvent e) {}

         @Override
         public void mouseReleased(MouseEvent e) {}

         @Override
         public void mouseEntered(MouseEvent e) {}

         @Override
         public void mouseExited(MouseEvent e) {}
     }



    /**
     * @name setLook
     * @param element in game, foreground color, background color, text font
     * @purpose the look of each game element
     */
    public void setLook(JComponent element, Color foreground, Color background, Font font) {
        if (foreground != null)
            element.setForeground(foreground);
        if (background != null)
            element.setBackground(background);
        if (font != null)
            element.setFont(font);
    }
}



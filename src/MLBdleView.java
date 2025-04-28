import javax.swing.*;
import java.awt.*;

public class MLBdleView extends JFrame {
    private MLBdle backend;
    private String[][] playerData;
    private String[][] squareColors;
    private int numGuesses;

    private final int DATA_ROWS = 9;
    private final int MAX_GUESSES = 8;

    private final int X_OFFSET = 150;
    private final int Y_OFFSET = 80;

    private final int WINDOW_WIDTH = 1600;
    private final int WINDOW_HEIGHT = 1000;
    private final int RULES_OFFSET = 100;
    private final int HEADER_OFFSET = 150;
    private final int RECT_HIEGHT = 80;
    private final int RECT_WIDTH = 150;
    private final int MID_POINT = 765;
    private final String[] rowHeaders = {"Name", "Team", "Position", "Rookie Year", "Bats", "WAR", "HRs", "RBIs", "AVG"};


    public MLBdleView(MLBdle backend) {
        this.backend = backend;
        numGuesses = 0;
        playerData = new String[8][9];

        // Setup the window and the buffer strategy.
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("MLBdle");
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setVisible(true);

    }




    public void readInData(Player guess, String[][] squareColors) {
        this.squareColors = squareColors;
        playerData[numGuesses][0] = guess.getName();
        playerData[numGuesses][1] = guess.getTeam().getTeamName();
        playerData[numGuesses][2] = guess.getPosition().getPlayerPosition();
        playerData[numGuesses][3] = String.valueOf(guess.getSeason());
        playerData[numGuesses][4] = guess.getBats();
        playerData[numGuesses][5] = String.valueOf(guess.getWar());
        playerData[numGuesses][6] = String.valueOf(guess.getHr());
        playerData[numGuesses][7] = String.valueOf(guess.getRbi());
        playerData[numGuesses][8] = String.valueOf(guess.getAvg());
        numGuesses++;
    }


    public void paint(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("MLBdle", MID_POINT, RULES_OFFSET);

        for (int i = 0; i < numGuesses; i++) {
            for (int j = 0; j < DATA_ROWS; j++) {
                // Set color based on closeness
                if (squareColors[i][j] == null) {
                    g.setColor(Color.WHITE);
                } else if (squareColors[i][j].equals("green")) {
                    g.setColor(Color.GREEN);
                } else {
                    g.setColor(Color.YELLOW);
                }

                // Draw colored rectangle
                int x = X_OFFSET * (j + 1);
                int y = RULES_OFFSET + Y_OFFSET * (i + 1);
                g.fillRect(x, y, RECT_WIDTH, RECT_HIEGHT);

                // Draw black border
                g.setColor(Color.BLACK);
                g.drawRect(x, y, RECT_WIDTH, RECT_HIEGHT);

                // Draw the player data inside the rectangle

                g.setFont(new Font("Arial", Font.BOLD, 12));
                g.drawString(playerData[i][j], x + 10, y + 45);
                g.drawString(rowHeaders[j], x + 10, HEADER_OFFSET);
            }

        }
        if (backend.isPlayerHasBeenGuessed()) {
            g.setColor(Color.GREEN);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("YOU WIN: The player was " + backend.getCorrectPlayer().getName(),
                    X_OFFSET, Y_OFFSET * (numGuesses + 1) + HEADER_OFFSET);
        }
        if (numGuesses == MAX_GUESSES)
        {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("YOU LOST: The player was " + backend.getCorrectPlayer().getName(),
                    X_OFFSET, Y_OFFSET * (numGuesses + 1) + HEADER_OFFSET);
        }
    }
}

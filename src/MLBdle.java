import javax.swing.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MLBdle {

    // Instance Variables
    private MLBdleView window;
    Scanner input = new Scanner(System.in);
    public ArrayList<Player> players;
    private boolean playerHasBeenGuessed;
    private int numGuesses;
    private Player playerGuessed;
    private Player correctPlayer;
    private String [][] squareColors;

    private final int DATA_ROWS = 9;
    private final int NUM_PLAYERS = 225;


    public MLBdle(String csvFilePath)
    {
        // Create Players Arraylist
        players = new ArrayList<Player>();
        loadPlayers(csvFilePath);

        // Randomly select the correct player
        correctPlayer = players.get((int) (Math.random() * NUM_PLAYERS));

        // Declare Instance Variables
        playerHasBeenGuessed = false;
        numGuesses = 0;
        this.window = new MLBdleView(this);
        squareColors = new String[8][9];
        window.repaint();
    }

    private void loadPlayers(String filePath)
    {
        // Create a BufferedReader object which can read in the CSV given the filepath
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath)))
        {
            // Skip the first line in the CSV
            String line = reader.readLine();

            // Create Player objects out of the data from the subsequent lines
            while ((line = reader.readLine()) != null)
            {
                String[] stats = line.split(",");
                if (stats.length < DATA_ROWS) continue;
                String name = stats[0].trim();
                String team = stats[1].trim();
                String position = stats[2].trim();
                int season = Integer.parseInt(stats[3].trim());
                String bats = stats[4].trim();
                double war = Double.parseDouble(stats[5].trim());
                int hr = Integer.parseInt(stats[6].trim());
                int rbi = Integer.parseInt(stats[7].trim());
                double avg = Double.parseDouble(stats[8].trim());

                players.add(new Player(name, team, position, season, bats, war, hr, rbi, avg));
            }
        }
        // Ensure that there are no errors in the data
        catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
        }
    }

    // If the player is found in the arraylist of players, then update the window accordingly
    public void makeGuess(String playerName) {
        for (Player player : players) {
            if (player.getName().equalsIgnoreCase(playerName)) {
                this.playerGuessed = player;
                this.comparePlayers();
                window.readInData(playerGuessed, squareColors);
                numGuesses++;
                window.repaint();

                // If correct player has been guessed, then change to has won to true
                if (playerGuessed.getName().equals(correctPlayer.getName())) {
                    playerHasBeenGuessed = true;
                }
                return;
            }
        }

        // If player not found, show error message
        JOptionPane.showMessageDialog(window, "Player not found. Please try again.");
    }

    // Compare the data of the correct player to the guessed player to modify the square colors
    public void comparePlayers()
    {
        // Compare Player Names
        if (playerGuessed.getName().equals(correctPlayer.getName()))
        {
            for (int i = 0; i < DATA_ROWS; i++)
            {
                squareColors[numGuesses][i] = "green";
            }
            playerHasBeenGuessed = true;
            return;
        }

        // Compare player Teams
        if (playerGuessed.getTeam().getTeamName().equals(correctPlayer.getTeam().getTeamName())) {
            squareColors[numGuesses][1] = "green";
        } else if (this.teamsAreClose(playerGuessed.getTeam(), correctPlayer.getTeam())) {
            squareColors[numGuesses][1] = "yellow";
        }

        // Compare player Positions
        if (playerGuessed.getPosition().getPlayerPosition().equals(correctPlayer.getPosition().getPlayerPosition())) {
            squareColors[numGuesses][2] = "green";
        } else if (this.closePositions(playerGuessed.getPosition(), correctPlayer.getPosition())) {
            squareColors[numGuesses][2] = "yellow";
        }

        // Compare player Rookie Seasons
        int gSeason = playerGuessed.getSeason();
        int cSeason = correctPlayer.getSeason();
        if (gSeason == cSeason) {
            squareColors[numGuesses][3] = "green";
        } else if (Math.abs(gSeason - cSeason) <= 2) {
            squareColors[numGuesses][3] = "yellow";
        }

        // Compare player Bats
        if (playerGuessed.getBats().equals(correctPlayer.getBats())) {
            squareColors[numGuesses][4] = "green";
        }

        // Compare player WARs
        double gWar = playerGuessed.getWar();
        double cWar = correctPlayer.getWar();
        if (Math.abs(gWar - cWar) < 0.1) {
            squareColors[numGuesses][5] = "green";
        } else if (Math.abs(gWar - cWar) <= 0.5) {
            squareColors[numGuesses][5] = "yellow";
        }

        // Compare player HRs
        int gHr = playerGuessed.getHr();
        int cHr = correctPlayer.getHr();
        if (gHr == cHr) {
            squareColors[numGuesses][6] = "green";
        } else if (Math.abs(gHr - cHr) <= 5) {
            squareColors[numGuesses][6] = "yellow";
        }

        // Compare player RBIs
        int gRbi = playerGuessed.getRbi();
        int cRbi = correctPlayer.getRbi();
        if (gRbi == cRbi) {
            squareColors[numGuesses][7] = "green";
        } else if (Math.abs(gRbi - cRbi) <= 5) {
            squareColors[numGuesses][7] = "yellow";
        }

        // Compare player AVGs
        double gAvg = playerGuessed.getAvg();
        double cAvg = correctPlayer.getAvg();
        if (Math.abs(gAvg - cAvg) < 0.001) {
            squareColors[numGuesses][8] = "green";
        } else if (Math.abs(gAvg - cAvg) <= 0.010) {
            squareColors[numGuesses][8] = "yellow";
        }
    }

    // Checks if teams are either in the same direction or in the same league
    public boolean teamsAreClose(Team guess, Team answer)
    {
        return guess.getDirection().equals(answer.getDirection()) || guess.getLeague().equals(answer.getLeague());
    }

    // Checks if the positions are both infield or outfield
    public boolean closePositions(Position guess, Position answer)
    {
        return guess.getField().equals(answer.getField());
    }

    // Getters
    public boolean isPlayerHasBeenGuessed() {
        return playerHasBeenGuessed;
    }

    public Player getCorrectPlayer() {
        return correctPlayer;
    }

    // Start game by creating MLBdle object
    public static void main(String[] args)
    {
        MLBdle game1 =
                new MLBdle("/Users/kchavinson/IdeaProjects/Weddle/Data/MLBDLE_PLAYER_DATA_ALPHABETICAL.csv");

    }

}

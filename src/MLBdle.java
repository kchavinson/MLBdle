import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MLBdle {
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
        players = new ArrayList<Player>();
        loadPlayers(csvFilePath);
        correctPlayer = players.get((int) (Math.random() * NUM_PLAYERS));
        playerHasBeenGuessed = false;
        numGuesses = 0;
        this.window = new MLBdleView(this);
        squareColors = new String[8][9];
        window.repaint();
    }

    private void loadPlayers(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine(); // skip header
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens.length < 9) continue;
                String name = tokens[0].trim();
                String team = tokens[1].trim();
                String position = tokens[2].trim();
                int season = Integer.parseInt(tokens[3].trim());
                String bats = tokens[4].trim();
                double war = Double.parseDouble(tokens[5].trim());
                int hr = Integer.parseInt(tokens[6].trim());
                int rbi = Integer.parseInt(tokens[7].trim());
                double avg = Double.parseDouble(tokens[8].trim());

                players.add(new Player(name, team, position, season, bats, war, hr, rbi, avg));
            }
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
        }
    }

    public ArrayList<Player> getPlayers()
    {
        return players;
    }


    public void guessPlayer() {
        System.out.println("Please guess a player:");
        String playerName = input.nextLine();

        for (Player player : players) {
            if (player.getName().equals(playerName)) {  // Ignore case differences
                this.playerGuessed = player;
                return;
            }
        }

        // If we finish the for loop without finding a match
        System.out.println("That's incorrect, please try again.");
        guessPlayer();
    }

    public void play()
    {

        while (!playerHasBeenGuessed && numGuesses < 8)
        {
            this.guessPlayer();
            this.comparePlayers();
            window.readInData(playerGuessed, squareColors);
            numGuesses++;
            window.repaint();
        }
        window.repaint();

    }

    public void comparePlayers()
    {
        // Column 0: Name
        if (playerGuessed.getName().equals(correctPlayer.getName()))
        {
            for (int i = 0; i < DATA_ROWS; i++)
            {
                squareColors[numGuesses][i] = "green";
            }
            playerHasBeenGuessed = true;
            return;
        }

        // Column 1: Team
        if (playerGuessed.getTeam().getTeamName().equals(correctPlayer.getTeam().getTeamName())) {
            squareColors[numGuesses][1] = "green";
        } else if (this.teamsAreClose(playerGuessed.getTeam(), correctPlayer.getTeam())) {
            squareColors[numGuesses][1] = "yellow";
        }

        // Column 2: Position
        if (playerGuessed.getPosition().getPlayerPosition().equals(correctPlayer.getPosition().getPlayerPosition())) {
            squareColors[numGuesses][2] = "green";
        } else if (this.closePositions(playerGuessed.getPosition(), correctPlayer.getPosition())) {
            squareColors[numGuesses][2] = "yellow";
        }

        // Column 3: Rookie Season
        int gSeason = playerGuessed.getSeason();
        int cSeason = correctPlayer.getSeason();
        if (gSeason == cSeason) {
            squareColors[numGuesses][3] = "green";
        } else if (Math.abs(gSeason - cSeason) <= 2) {
            squareColors[numGuesses][3] = "yellow";
        }

        // Column 4: Bats
        if (playerGuessed.getBats().equals(correctPlayer.getBats())) {
            squareColors[numGuesses][4] = "green";
        }

        // Column 5: WAR
        double gWar = playerGuessed.getWar();
        double cWar = correctPlayer.getWar();
        if (Math.abs(gWar - cWar) < 0.1) {
            squareColors[numGuesses][5] = "green";
        } else if (Math.abs(gWar - cWar) <= 0.5) {
            squareColors[numGuesses][5] = "yellow";
        }

        // Column 6: HR
        int gHr = playerGuessed.getHr();
        int cHr = correctPlayer.getHr();
        if (gHr == cHr) {
            squareColors[numGuesses][6] = "green";
        } else if (Math.abs(gHr - cHr) <= 5) {
            squareColors[numGuesses][6] = "yellow";
        }

        // Column 7: RBI
        int gRbi = playerGuessed.getRbi();
        int cRbi = correctPlayer.getRbi();
        if (gRbi == cRbi) {
            squareColors[numGuesses][7] = "green";
        } else if (Math.abs(gRbi - cRbi) <= 5) {
            squareColors[numGuesses][7] = "yellow";
        }

        // Column 8: AVG
        double gAvg = playerGuessed.getAvg();
        double cAvg = correctPlayer.getAvg();
        if (Math.abs(gAvg - cAvg) < 0.001) {
            squareColors[numGuesses][8] = "green";
        } else if (Math.abs(gAvg - cAvg) <= 0.010) {
            squareColors[numGuesses][8] = "yellow";
        }
    }

    public boolean teamsAreClose(Team guess, Team answer)
    {
        return guess.getDirection().equals(answer.getDirection()) || guess.getLeague().equals(answer.getLeague());
    }
    public boolean closePositions(Position guess, Position answer)
    {
        return guess.getField().equals(answer.getField());
    }

    public boolean isPlayerHasBeenGuessed() {
        return playerHasBeenGuessed;
    }

    public Player getCorrectPlayer() {
        return correctPlayer;
    }

    public String[][] getSquareColors() {
        return squareColors;
    }

    public static void main(String[] args)
    {
        // Creates dice game object
        MLBdle game1 = new MLBdle("/Users/kchavinson/IdeaProjects/Weddle/Data/MLBDLE_PLAYER_DATA_ALPHABETICAL.csv");

        // Runs through game
        game1.play();
    }

}

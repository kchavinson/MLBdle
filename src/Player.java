public class Player {
    private final String name;
    private final Team team;
    private final Position position;
    private final int rookieSeason;
    private final String bats;
    private final double war;
    private final int hr;
    private final int rbi;
    private final double avg;

    public Player(String name, String team, String position, int season, String bats,
                  double war, int hr, int rbi, double avg) {
        this.name = name;
        this.team = new Team(team);
        this.position = new Position(position);
        this.rookieSeason = season;
        this.bats = bats;
        this.war = war;
        this.hr = hr;
        this.rbi = rbi;
        this.avg = avg;
    }


    public String getName() {
        return name;
    }

    public Team getTeam() {
        return team;
    }


    public Position getPosition() {
        return position;
    }


    public int getSeason() {
        return rookieSeason;
    }

    public String getBats() {
        return bats;
    }

    public double getWar() {
        return war;
    }

    public int getHr() {
        return hr;
    }

    public int getRbi() {
        return rbi;
    }

    public double getAvg() {
        return avg;
    }
}

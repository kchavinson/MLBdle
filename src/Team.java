public class Team {

    private final String[] east = {"NYY", "BOS", "TOR", "BAL", "TBR", "ATL", "MIA", "NYM", "PHI", "WSN"};
    private final String[] central = {"CHW", "CLE", "DET", "KCR", "MIN", "CHC", "CIN", "MIL", "PIT", "STL"};
    private final String[] west = {"HOU", "LAA", "OAK", "SEA", "TEX", "ARI", "COL", "LAD", "SDP", "SFG"};
    private final String[] AL = {"NYY", "BOS", "TOR", "BAL", "TBR", "CHW", "CLE", "DET", "KCR", "MIN", "HOU", "LAA", "OAK", "SEA", "TEX"};
    private final String[] NL = {"ATL", "MIA", "NYM", "PHI", "WSN", "CHC", "CIN", "MIL", "PIT", "STL", "ARI", "COL", "LAD", "SDP", "SFG"};
    private String direction;
    private String league;
    private String team;


    public Team(String team)
    {
        this.league = findLeague(team);
        this.direction = findDirection(team);
        this.team = team;
    }

    public String findLeague(String team)
    {
        for (String club: AL)
        {
            if (team.equals(club))
            {
                return "AL";
            }
        }
        return "NL";
    }

    public String findDirection(String team)
    {
        for (String club: east)
        {
            if (team.equals(club))
            {
                return "east";
            }
        }
        for (String club: central)
        {
            if (team.equals(club))
            {
                return "central";
            }
        }
        return "west";
    }

    public String getTeamName() {
        return team;
    }

    public String getLeague() {
        return league;
    }

    public String getDirection() {
        return direction;
    }
}

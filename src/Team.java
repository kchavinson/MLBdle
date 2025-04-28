public class Team {

    private final String[] east = {"NYY", "BOS", "TOR", "BAL", "TBR", "ATL", "MIA", "NYM", "PHI", "WSN"};
    private final String[] central = {"CHW", "CLE", "DET", "KCR", "MIN", "CHC", "CIN", "MIL", "PIT", "STL"};
    private final String[] west = {"HOU", "LAA", "ATH", "SEA", "TEX", "ARI", "COL", "LAD", "SDP", "SFG"};
    private final String[] AL = {"NYY", "BOS", "TOR", "BAL", "TBR", "CHW", "CLE", "DET", "KCR", "MIN", "HOU", "LAA", "ATH", "SEA", "TEX"};
    private final String[] NL = {"ATL", "MIA", "NYM", "PHI", "WSN", "CHC", "CIN", "MIL", "PIT", "STL", "ARI", "COL", "LAD", "SDP", "SFG"};
    private String direction;
    private String league;
    private String team;


    // Declare Instance Variables
    public Team(String team)
    {
        this.league = findLeague(team);
        this.direction = findDirection(team);
        this.team = team;
    }

    // Assign league to team
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

    // Assign direction to team
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

    // Getters
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

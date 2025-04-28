public class Position {
    String[] infieldPositions = {"1B", "2B", "3B", "SS", "C", "P"};
    String[] outfieldPositions = {"LF", "CF", "RF"};
    private String position;
    private String field;

    public Position(String position) {
        this.position = position;
        this.field = this.findField(position);
    }

    public String findField(String position)
    {
        for (String pos: infieldPositions)
        {
            if (pos.equals(position))
            {
                return "INF";
            }
        }
        return "OF";
    }

    public String getPlayerPosition() {
        return position;
    }

    public String getField() {
        return field;
    }
}

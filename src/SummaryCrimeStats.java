import java.util.ArrayList;

public class SummaryCrimeStats {
    private String crimeType;
    private ArrayList<String> offenses;
    private ArrayList<Integer> offenseCounts;

    public SummaryCrimeStats(String crimeType) {
        this.crimeType = crimeType;
        this.offenses = new ArrayList<>();
        this.offenseCounts = new ArrayList<>();
    }

    public String getCrimeType() {
        return crimeType;
    }

    public void setCrimeType(String crimeType) {
        this.crimeType = crimeType;
    }

    public ArrayList<String> getOffenses() {
        return offenses;
    }

    public ArrayList<Integer> getOffenseCounts() {
        return offenseCounts;
    }

    public void addOffense(String offense, int count) {
        offenses.add(offense);
        offenseCounts.add(count);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Crime Type: ").append(crimeType).append(" | Offenses: ");
        for (int i = 0; i < offenses.size(); i++) {
            sb.append(offenses.get(i)).append(" (").append(offenseCounts.get(i)).append("), ");
        }
        return sb.toString();
    }
}

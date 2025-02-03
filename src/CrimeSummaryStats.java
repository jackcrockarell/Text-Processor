
public class CrimeSummaryStats {
    private String type;
    private int count;

    public CrimeSummaryStats(String type, int count) {
        this.type = type;
        this.count = count;
    }

    @Override
    public String toString() {
        return type + ": " + count;
    }
}


class CrimeStats {
    String crimeType;
    String name;
    String date;
    String location;

    public CrimeStats(String crimeType, String name, String date, String location) {
        this.crimeType = crimeType;
        this.name = name;
        this.date = date;
        this.location = location;
    }
    @Override
    public String toString() {
        return String.format("Crime Type: %s Name: %s Date: %s Location: %s ",
                crimeType, name, date, location);
    }
}

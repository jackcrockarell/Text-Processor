// RegularCrimeStats class to store individual crime details
public class RegularCrimeStats {
    String crimeType;
    String name;
    String date;
    String location;

    public RegularCrimeStats(String crimeType, String name, String date, String location) {
        this.crimeType = crimeType;
        this.name = name;
        this.date = date;
        this.location = location;
    }

    @Override
    public String toString() {
        return "Crime Type: " + crimeType + " | Name: " + name + " | Date: " + date + " | Location: " + location;
    }
}
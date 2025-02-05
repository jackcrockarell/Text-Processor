/**
 * RegularCrimeStats class
 * This class represents an individual crime statistic entry.
 * It stores details such as crime type, name, date, and location.
 */
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
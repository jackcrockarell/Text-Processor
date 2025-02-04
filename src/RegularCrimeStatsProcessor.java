import java.io.*;
import java.util.*;

public class RegularCrimeStatsProcessor {

    public static void processCrimeStats(String inputFile) {
        List<RegularCrimeStats> crimeList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            String currentCrimeType = "";
            StringBuilder currentCrimeData = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.isEmpty()) {
                    continue;
                }


                if (line.matches("[A-Za-z ]+")) {
                    if (currentCrimeData.length() > 0) {

                        processCrimeData(currentCrimeType, currentCrimeData.toString(), crimeList);
                        currentCrimeData.setLength(0);
                    }
                    currentCrimeType = line;
                } else {

                    currentCrimeData.append(" ").append(line);
                }
            }

            if (currentCrimeData.length() > 0) {
                processCrimeData(currentCrimeType, currentCrimeData.toString(), crimeList);
            }

            // Output the processed crime stats
            for (RegularCrimeStats crime : crimeList) {
                System.out.println(crime);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void processCrimeData(String crimeType, String crimeData, List<RegularCrimeStats> crimeList) {

        String[] crimes = crimeData.split(";");

        for (String crime : crimes) {
            crime = crime.trim();
            String[] parts = crime.split(",", 3);

            if (parts.length == 3) {

                String date = parts[0].trim();
                String name = parts[1].trim();
                String location = parts[2].trim();

                RegularCrimeStats crimeStats = new RegularCrimeStats(crimeType, name, date, location);
                crimeList.add(crimeStats);
            }
        }
    }

    public static void main(String[] args) {
        String inputFile = "output/CrimeStats.txt";
        processCrimeStats(inputFile);
    }
}

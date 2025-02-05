/**
 * RegularCrimeStatsProcessor class
 * This class processes crime statistics from a text file and extracts structured crime data.
 * It identifies crime types, processes multiline data entries, and formats them into structured objects.
 * The processed statistics are printed to the console.
 */
import java.io.*;
import java.util.*;
import java.util.regex.*;

public class RegularCrimeStatsProcessor {

    public static void processCrimeStats(String inputFile) {
        List<RegularCrimeStats> crimeList = new ArrayList<>();
        String lastKnownState = "";

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            String currentCrimeType = "";
            StringBuilder currentCrimeData = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.isEmpty()) {
                    continue;
                }

                // Check if the line is a new crime type using a refined regex pattern
                if (isNewCrimeType(line)) {
                    if (currentCrimeData.length() > 0) {
                        processCrimeData(currentCrimeType, currentCrimeData.toString(), crimeList, lastKnownState);
                        currentCrimeData.setLength(0);
                    }
                    currentCrimeType = line;
                } else {
                    currentCrimeData.append(" ").append(line);
                }
            }

            // Process any remaining data
            if (currentCrimeData.length() > 0) {
                processCrimeData(currentCrimeType, currentCrimeData.toString(), crimeList, lastKnownState);
            }

            // Output processed crime stats
            for (RegularCrimeStats crime : crimeList) {
                System.out.println(crime);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    private static boolean isNewCrimeType(String line) {
        // Use regex to determine if a line is a crime category
        return Pattern.matches("[A-Z][A-Z ]+", line); // Checks for uppercase words
    }

    private static void processCrimeData(String crimeType, String crimeData, List<RegularCrimeStats> crimeList, String lastKnownState) {
        String[] crimes = crimeData.split(";");
        Pattern locationPattern = Pattern.compile("(.*?),\s*([^,]+),\s*([^,]+)\s+([A-Za-z.]+)");

        for (String crime : crimes) {
            crime = crime.trim();
            Matcher matcher = locationPattern.matcher(crime);

            if (matcher.matches()) {
                String date = matcher.group(1).trim();
                String name = matcher.group(2).trim();
                String location = matcher.group(3).trim();
                String state = matcher.group(4).trim();
                lastKnownState = state; // Update last known state

                RegularCrimeStats crimeStats = new RegularCrimeStats(crimeType, name, date, location + ", " + state);
                crimeList.add(crimeStats);
            } else {
                // Handle cases where the location was split, use last known state
                String[] parts = crime.split(",");
                if (parts.length >= 3) {
                    String date = parts[0].trim();
                    String name = parts[1].trim();
                    String location = parts[2].trim();
                    String state = (parts.length > 3) ? parts[3].trim() : lastKnownState;

                    if (!state.matches("[A-Za-z.]+")) {
                        state = lastKnownState; // Ensure the state is valid
                    }

                    RegularCrimeStats crimeStats = new RegularCrimeStats(crimeType, name, date, location + ", " + state);
                    crimeList.add(crimeStats);
                } else {
                    System.err.println("Skipping malformed crime entry: " + crime);
                }
            }
        }
    }

    public static void main(String[] args) {
        String inputFile = "output/CrimeStats.txt";
        processCrimeStats(inputFile);
    }
}

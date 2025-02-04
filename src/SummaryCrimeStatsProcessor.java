import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SummaryCrimeStatsProcessor {
    public static void main(String[] args) {
        processSummaryCrimeStats("output/SummarizedStats.txt");
    }

    public static void processSummaryCrimeStats(String filename) {
        try (Scanner scanner = new Scanner(new File(filename))) {
            String currentCategory = null;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();

                if (line.isEmpty()) continue;


                if (line.equals(line.toUpperCase())) {
                    currentCategory = line;
                    System.out.println("Category: " + currentCategory);
                    continue;
                }


                processAndPrintStat(currentCategory, line);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: File not found - " + filename);
        }
    }

    private static void processAndPrintStat(String category, String entry) {
        if (category == null || entry.isEmpty()) return;


        if (category.equals("OFFENSES CHARGED ARE AS FOLLOWS")) {
            // Format: Crime Type, Count
            String[] parts = entry.split(";");
            for (String part : parts) {
                String[] crimeData = part.split(",");
                if (crimeData.length == 2) {
                    String crime = crimeData[0].trim();
                    String count = crimeData[1].trim();
                    System.out.println("Crime Type: " + crime + " | Count: " + count);
                }
            }
        } else if (category.equals("LYNCHINGS BY STATES") || category.equals("LYNCHING STATES")) {
            // Format: State, Count
            String[] parts = entry.split(";");
            for (String part : parts) {
                String[] stateData = part.split(",");
                if (stateData.length == 2) {
                    String state = stateData[0].trim();
                    String count = stateData[1].trim();
                    System.out.println("State: " + state + " | Count: " + count);
                }
            }
        } else if (category.equals("LYNCHING BY THE MONTH")) {
            // Format: Month, Count
            String[] parts = entry.split(";");
            for (String part : parts) {
                String[] monthData = part.split(",");
                if (monthData.length == 2) {
                    String month = monthData[0].trim();
                    String count = monthData[1].trim();
                    System.out.println("Month: " + month + " | Count: " + count);
                }
            }
        } else if (category.equals("WOMEN LYNCHED")) {

            String[] womanParts = entry.split(",", 4);
            String date = womanParts.length > 0 ? womanParts[0].trim() : "Unknown";
            String name = womanParts.length > 1 ? womanParts[1].trim() : "Unknown";
            String cause = womanParts.length > 2 ? womanParts[2].trim() : "Unknown";
            String location = womanParts.length > 3 ? womanParts[3].trim() : "Unknown";

            System.out.println("Date: " + date + " | Name: " + name + " | Cause: " + cause + " | Location: " + location);
        }
    }
}

/**
 * SummaryCrimeStatsProcessor class
 * This class processes summarized crime statistics from a text file.
 * It categorizes and counts different crime statistics, including special cases like "WOMEN LYNCHED".
 * The processed statistics are printed to the console.
 */
import java.io.*;
import java.util.*;
public class SummaryCrimeStatsProcessor {
    public static void main(String[] args) {
        processSummaryCrimeStats("output/SummarizedStats.txt");
    }

    public static void processSummaryCrimeStats(String filename) {
        try (Scanner scanner = new Scanner(new File(filename))) {
            String currentCategory = null;
            boolean isFirstUnnamedStats = false;
            boolean isSecondUnnamedStats = false;
            Map<String, Integer> mergedCounts = new LinkedHashMap<>();
            List<String> womenLynchedEntries = new ArrayList<>();
            StringBuilder multiLineEntry = new StringBuilder();

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();

                if (line.isEmpty()) continue;

                if (line.equals(line.toUpperCase())) {
                    printMergedCounts(currentCategory, mergedCounts);
                    mergedCounts.clear();

                    currentCategory = line;
                    isFirstUnnamedStats = false;
                    isSecondUnnamedStats = false;
                    System.out.println("Category: " + currentCategory);
                    continue;
                }

                if (currentCategory != null && currentCategory.equals("WOMEN LYNCHED")) {
                    // Check if line starts a new entry
                    if (!multiLineEntry.isEmpty() && line.matches(".*\\d{4}.*")) {
                        womenLynchedEntries.add(multiLineEntry.toString().trim());
                        multiLineEntry.setLength(0);
                    }

                    multiLineEntry.append(line).append(" ");
                    continue;
                }

                processAndStoreStat(currentCategory, line, mergedCounts);
            }

            if (multiLineEntry.length() > 0) {
                womenLynchedEntries.add(multiLineEntry.toString().trim());
            }

            printMergedCounts(currentCategory, mergedCounts);
            printWomenLynched(womenLynchedEntries);
        } catch (FileNotFoundException e) {
            System.err.println("Error: File not found - " + filename);
        }
    }

    private static void processAndStoreStat(String category, String entry, Map<String, Integer> mergedCounts) {
        if (category == null || entry.isEmpty()) return;

        String[] parts = entry.split(";");
        for (String part : parts) {
            String[] data = part.split(",");
            if (data.length >= 2) {
                String key = data[0].trim();
                String countStr = data[data.length - 1].trim().replaceAll("[^0-9]", "");
                if (!countStr.isEmpty()) {
                    int count = Integer.parseInt(countStr);
                    mergedCounts.put(key, mergedCounts.getOrDefault(key, 0) + count);
                }
            }
        }
    }

    private static void printMergedCounts(String category, Map<String, Integer> mergedCounts) {
        if (category == null || mergedCounts.isEmpty()) return;
        for (Map.Entry<String, Integer> entry : mergedCounts.entrySet()) {
            System.out.println(category + " | " + entry.getKey() + " | Count: " + entry.getValue());
        }
    }

    private static void printWomenLynched(List<String> womenLynchedEntries) {
        for (String entry : womenLynchedEntries) {
            String[] parts = entry.split("; ");
            for (String subEntry : parts) {
                String[] details = subEntry.split(",", 4);
                String date = details.length > 0 ? details[0].trim() : "Unknown";
                String name = details.length > 1 ? details[1].trim() : "Unknown";
                String cause = details.length > 2 ? details[2].trim() : "Unknown";
                String location = details.length > 3 ? details[3].trim() : "Unknown";
                System.out.println("Date: " + date + " | Name: " + name + " | Cause: " + cause + " | Location: " + location);
            }
        }
    }
}

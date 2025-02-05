/**
 * UntitledSummaryStatsProcessor class
 * This class processes and categorizes 1892 unnamed summary statistics from a text file.
 * It separates lynching statistics from crime statistics and counts occurrences of each category.
 * The processed statistics are printed to the console.
 */
import java.io.*;
import java.util.*;
public class UntitledSummaryStatsProcessor {
    public static void main(String[] args) {
        processUntitledSummaryStats("output/UntitledSummarizedStats.txt");
    }

    public static void processUntitledSummaryStats(String filename) {
        try (Scanner scanner = new Scanner(new File(filename))) {
            Map<String, Integer> lynchingCounts = new LinkedHashMap<>();
            Map<String, Integer> crimeCounts = new LinkedHashMap<>();
            boolean isLynchingStats = true;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;

                // Detect transition from state stats to crime stats
                if (line.startsWith("Rape,")) {
                    isLynchingStats = false;
                }

                if (isLynchingStats) {
                    processAndStoreStat(line, lynchingCounts);
                } else {
                    processAndStoreStat(line, crimeCounts);
                }
            }

            System.out.println("=== 1892 LYNCHING STATISTICS ===");
            printMergedCounts(lynchingCounts);
            System.out.println("\n=== 1892 CRIME STATISTICS ===");
            printMergedCounts(crimeCounts);

        } catch (FileNotFoundException e) {
            System.err.println("Error: File not found - " + filename);
        }
    }

    private static void processAndStoreStat(String entry, Map<String, Integer> mergedCounts) {
        String[] parts = entry.split(";");
        for (String part : parts) {
            String[] data = part.split(",");
            if (data.length >= 2) {
                String key = String.join(",", Arrays.copyOfRange(data, 0, data.length - 1)).trim();
                String countStr = data[data.length - 1].trim().replaceAll("[^0-9]", "");
                if (!countStr.isEmpty()) {
                    int count = Integer.parseInt(countStr);
                    mergedCounts.put(key, mergedCounts.getOrDefault(key, 0) + count);
                }
            }
        }
    }

    private static void printMergedCounts(Map<String, Integer> mergedCounts) {
        if (mergedCounts.isEmpty()) return;
        for (Map.Entry<String, Integer> entry : mergedCounts.entrySet()) {
            System.out.println(entry.getKey() + " | Count: " + entry.getValue());
        }
    }
}

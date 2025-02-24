/**
 * TextProcessing class
 * This class reads a text file containing crime statistics and processes the data into separate categories.
 * It extracts regular crime stats, summarized stats, and 1892 unnamed summary statistics.
 * The results are saved into three different output files:
 * - CrimeStats.txt: Contains regular crime statistics.
 * - SummarizedStats.txt: Contains summarized crime statistics.
 * - UntitledSummarizedStats.txt: Contains unnamed 1892 summary statistics.
 */
import java.io.*;
public class TextProcessing {

    public static void processCrimeStats(String inputFile) {
        String outputDir = "output/";
        createOutputDir(outputDir);

        // File Writers for each category
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             PrintWriter writerCrimeStats = new PrintWriter(new FileWriter(outputDir + "CrimeStats.txt"));
             PrintWriter writerSummarizedStats = new PrintWriter(new FileWriter(outputDir + "SummarizedStats.txt"));
             PrintWriter writerUntitledSummarizedStats = new PrintWriter(new FileWriter(outputDir + "UntitledSummarizedStats.txt"))) {

            String line;
            int lineNumber = 0;
            StringBuilder multiLineStat = new StringBuilder(); // For handling multiline 1892 stats

            while ((line = reader.readLine()) != null) {
                lineNumber++;

                // Skip blank lines
                if (line.trim().isEmpty()) {
                    continue;
                }

                // 1892 Summarized Stats (Handle multiline)
                if ((lineNumber >= 695 && lineNumber <= 700) || (lineNumber >= 707 && lineNumber <= 711)) {
                    if (line.contains(";")) {
                        writerUntitledSummarizedStats.println(line);
                    } else {
                        multiLineStat.append(line).append(" ");
                    }
                }
                // Write completed multiline 1892 stats
                if ((lineNumber == 700 || lineNumber == 711) && multiLineStat.length() > 0) {
                    writerUntitledSummarizedStats.println(multiLineStat.toString().trim());
                    multiLineStat.setLength(0); // Reset for next use
                }

                // 1893 & 1894 Regular Crime Stats
                if ((lineNumber >= 450 && lineNumber <= 660) || (lineNumber >= 3223 && lineNumber <= 3437)) {
                    writerCrimeStats.println(line);
                }

                // 1893 & 1894 Summarized Crime Stats
                if ((lineNumber >= 663 && lineNumber <= 680) || (lineNumber >= 3453 && lineNumber <= 3472)) {
                    writerSummarizedStats.println(line);
                }
            }

            System.out.println("Processing complete. Crime statistics have been saved to 'CrimeStats.txt', 'SummarizedStats.txt', and 'UntitledSummarizedStats.txt'.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createOutputDir(String dir) {
        File directory = new File(dir);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    public static void main(String[] args) {
        String inputFile = "RedRecordIdaBWells.txt";
        processCrimeStats(inputFile);

    }
}

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TextProcessing {
    private List<CrimeStats> crimeStatsList = new ArrayList<>();
    private static final List<String> SUMMARY_HEADERS = List.of(
            "OFFENSES CHARGED FOR LYNCHING",
            "OFFENSES CHARGED ARE AS FOLLOWS",
            "LYNCHINGS BY STATES",
            "LYNCHING STATES",
            "LYNCHING BY THE MONTH",
            "WOMEN LYNCHED"
    );

    public static void main(String[] args) {
        String inputFile = "RedRecordIdaBWells.txt";
        String chapterTwoOutputFile = "ChapterTwo.txt";
        String chapterNineOutputFile = "ChapterNine.txt";
        String chapterTwoCleanedOutputFile = "ChapterTwoCleaned.txt";
        String chapterNineCleanedOutputFile = "ChapterNineCleaned.txt";

        String process1892Stats = "1892Stats.txt";
        String process1893Stats = "1893Stats.txt";
        String process1894Stats = "1894Stats.txt";

        int chapterTwoStartLine = 441;
        int chapterTwoEndLine = 717;
        int chapterNineStartLine = 3195;
        int chapterNineEndLine = 3473;

        int stat1StartLine1892 = 157;
        int stat1EndLine1892 = 162;
        int stat2StartLine1892 = 167;
        int stat2EndLine1892 = 171;
        int statStartLine1893 = 6;
        int statEndLine1893 = 147;
        int statStartLine1894 = 22;
        int statEndLine1894 = 165;

        TextProcessing textProcessing = new TextProcessing();

        //process and clean chapter 2
        textProcessing.processChapter(inputFile, chapterTwoStartLine, chapterTwoEndLine, chapterTwoOutputFile);
        textProcessing.removeBlankLines(chapterTwoOutputFile, chapterTwoCleanedOutputFile);

        //process and clean chapter 9
        textProcessing.processChapter(inputFile, chapterNineStartLine, chapterNineEndLine, chapterNineOutputFile);
        textProcessing.removeBlankLines(chapterNineOutputFile, chapterNineCleanedOutputFile);

        //process and save 1892 statistics to a file
        textProcessing.processStatYear(chapterTwoCleanedOutputFile, stat1StartLine1892, stat1EndLine1892, process1892Stats);
        textProcessing.processStatYear(chapterTwoCleanedOutputFile, stat2StartLine1892, stat2EndLine1892, process1892Stats);

        //process and save 1893 statistics to a file and print them
        textProcessing.processStatYear(chapterTwoCleanedOutputFile, statStartLine1893, statEndLine1893, process1893Stats);
        textProcessing.process1893Stats(); //call method to process 1893 stats

        //process and save 1894 statistics to a file and print them
        textProcessing.processStatYear(chapterNineCleanedOutputFile, statStartLine1894, statEndLine1894, process1894Stats);
        textProcessing.process1894Stats(); //call method to process 1894 stats
    }

    //method to process a chapter and save it to a specified output file
    public void processChapter(String inputFile, int startLine, int endLine, String outputFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {

            String line;
            int currentLine = 0;

            while ((line = reader.readLine()) != null) {
                currentLine++;
                if (currentLine >= startLine && currentLine <= endLine) {
                    writer.write(line);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Error processing chapter: " + e.getMessage());
        }
    }

    //method to remove blank lines from a file
    public void removeBlankLines(String inputFile, String outputFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {

            String line;

            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    writer.write(line);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Error removing blank lines: " + e.getMessage());
        }
    }


    //method to process statistics for a specific year and save them to a file
    public void processStatYear(String cleanedInputFile, int startLine, int endLine, String outputFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(cleanedInputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {

            String line;
            int currentLine = 0;

            while ((line = reader.readLine()) != null) {
                currentLine++;
                if (currentLine >= startLine && currentLine <= endLine) {
                    writer.write(line);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Error processing statistics: " + e.getMessage());
        }
    }


    //method to process 1892 stats
    public void process1892Stats() {
        String statsFile = "1892Stats.txt";
        System.out.println("\n1892 Statistics:\n");
        crimeStatsList.clear(); //clear previous stats
        processRegularStats(statsFile); //call a unified method for both crime and summary stats
        processSummaryStat(statsFile);
    }
    //method to process 1893 stats
    public void process1893Stats() {
        String statsFile = "1893Stats.txt";
        System.out.println("\n1893 Statistics:\n");
        crimeStatsList.clear(); //clear previous stats
        processRegularStats(statsFile);
        processSummaryStat(statsFile);
    }
    //method to process 1894 stats
    public void process1894Stats() {
        String statsFile = "1894Stats.txt";
        System.out.println("\n1894 Statistics:\n");
        crimeStatsList.clear(); //clear previous stats
        processRegularStats(statsFile);
        processSummaryStat(statsFile);
    }

    private void processRegularStats(String statsFile) {
        try {
            File file = new File(statsFile);
            Scanner scanner = new Scanner(file);
            String currentCrimeType = null;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();


                if (line.equals(line.toUpperCase()) && !line.isEmpty()) {
                    currentCrimeType = line;
                } else if (currentCrimeType != null && !line.isEmpty()) {

                    String[] statistics = line.split(";");

                    for (String stat : statistics) {
                        stat = stat.trim();
                        String[] details = stat.split(",");


                        if (details.length >= 4) {
                            String date = details[0].trim();
                            String name = details[1].trim();
                            String location = details[2].trim();


                            CrimeStats crimeStat = new CrimeStats(currentCrimeType, name, date, location);
                            crimeStatsList.add(crimeStat);
                        }
                    }
                }
            }

            printCrimeStats();
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }
    public static void processSummaryStat(String filePath) {
        List<CrimeSummaryStats> summaryStatsList = new ArrayList<>();
        String currentHeader = null;
        StringBuilder statisticLines = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isInSummarySection = false;

            while ((line = reader.readLine()) != null) {
                line = line.trim();


                if (SUMMARY_HEADERS.contains(line.toUpperCase())) {

                    if (statisticLines.length() > 0) {
                        processAndPrintSummary(currentHeader, statisticLines.toString(), summaryStatsList);
                    }

                    currentHeader = line.toUpperCase();
                    isInSummarySection = true;
                    statisticLines.setLength(0);
                    summaryStatsList.clear();

                } else if (isInSummarySection && !line.isEmpty()) {

                    statisticLines.append(line).append(" ");
                } else if (isInSummarySection && line.isEmpty()) {

                    if (statisticLines.length() > 0) {
                        processAndPrintSummary(currentHeader, statisticLines.toString(), summaryStatsList);
                    }
                    isInSummarySection = false;
                }
            }
            if (statisticLines.length() > 0) {
                processAndPrintSummary(currentHeader, statisticLines.toString(), summaryStatsList);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void processAndPrintSummary(String header, String statBlock, List<CrimeSummaryStats> summaryStatsList) {
        System.out.println();
        System.out.println(header);

        if (header.equals("WOMEN LYNCHED")) {
            processWomenLynched(statBlock);
            return;
        }

        String[] stats = statBlock.split(";");
        for (String stat : stats) {
            String[] parts = stat.trim().split(",");
            if (parts.length == 2) {
                String type = parts[0].trim();
                String countString = parts[1].trim();

                try {

                    int count = Integer.parseInt(countString.replaceAll("[^\\d]", ""));
                    summaryStatsList.add(new CrimeSummaryStats(type, count));
                } catch (NumberFormatException e) {

                    System.err.println("Warning: Invalid number format for count in stat: " + stat);
                    continue; // Skip this invalid stat
                }
            }
        }
        for (CrimeSummaryStats stat : summaryStatsList) {
            System.out.println(stat);
        }
    }
    private static void processWomenLynched(String statBlock) {

        String[] stats = statBlock.split(";");
        System.out.println();
        for (String stat : stats) {

            System.out.println(stat.trim() + ";");
        }
    }
    private void printCrimeStats() {
        System.out.println("\nCrime Statistics:");
        for (CrimeStats crimeStat : crimeStatsList) {
            System.out.println(crimeStat);
        }
    }
}
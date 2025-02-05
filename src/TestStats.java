/**
 * TestStats class
 * This class is responsible for executing the crime statistics processors and printing the results to the console.
 * It calls three processors:
 * - RegularCrimeStatsProcessor: Processes and prints regular crime statistics.
 * - SummaryCrimeStatsProcessor: Processes and prints summarized crime statistics.
 * - UntitledSummaryStatsProcessor: Processes and prints 1892 unnamed summary statistics.
 */
public class TestStats {
    public static void main(String[] args) {
        System.out.println("\n====================================\n=== REGULAR CRIME STATISTICS ===\n====================================\n");
        RegularCrimeStatsProcessor.processCrimeStats("output/CrimeStats.txt");

        System.out.println("\n====================================\n=== SUMMARY CRIME STATISTICS ===\n====================================\n");
        SummaryCrimeStatsProcessor.processSummaryCrimeStats("output/SummarizedStats.txt");

        System.out.println("\n");
        UntitledSummaryStatsProcessor.processUntitledSummaryStats("output/UntitledSummarizedStats.txt");
    }
}

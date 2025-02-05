## Text Processing Project

### Overview
This project is a Java-based text processing system that extracts, organizes, and analyzes crime statistics from historical text documents. The system processes structured and unstructured text data, separating prose from statistical information and formatting it into a structured output.

### Features
- Processes a large text file to extract crime statistics.
- Differentiates between individual crime records and summary statistics.
- Stores extracted statistics in structured formats for analysis.
- Provides essential text processing functions:
  - Read and parse historical crime data.
  - Categorize statistics into individual and summary crime reports.
  - Display extracted statistics for review.

### Project Structure
- **RegularCrimeStats.java** – Represents an individual crime statistic entry with details such as crime type, name, date, and location.
- **RegularCrimeStatsProcessor.java** – Processes crime statistics from a text file, extracts structured crime data, and formats them into structured objects.
- **SummaryCrimeStatsProcessor.java** – Processes summarized crime statistics, categorizes and counts different crimes, and handles special cases.
- **TextProcessing.java** – Reads a text file containing crime statistics and processes the data into separate categories, saving results into different output files.
- **UntitledSummaryStatsProcessor.java** – Processes and categorizes the 1892 unnamed summary statistics.
- **TestStats.java** – Executes the crime statistics processors and prints the results to the console.

### How It Works
1. **Reading Crime Data**
   - The program reads raw text files containing crime statistics.
2. **Processing Text Data**
   - The `TextProcessing` class scans text files, differentiating between individual and summary statistics.
3. **Extracting and Structuring Data**
   - The `RegularCrimeStatsProcessor`, `SummaryCrimeStatsProcessor`, and `UntitledSummaryStatsProcessor` parse crime descriptions, dates, locations, and names.
4. **Displaying and Analyzing Data**
   - Users can review extracted statistics for further analysis within the console of the `TestStats` class output.


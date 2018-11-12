package seedu.address.model.person.timetable;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Base64;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.ArrayUtils;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;


/**
 * timetable data which will process the inputs and create a timetable
 */
public class TimetableData {

    public static final String[] DAYS = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday",
        "Saturday", "Sunday"};
    public static final String[] DAYS_IN_LOWER_CASE = {"monday", "tuesday", "wednesday", "thursday",
        "friday",
        "saturday", "sunday"};
    public static final String[] TIMINGS = {"0800", "0900", "1000", "1100", "1200", "1300",
        "1400", "1500", "1600", "1700", "1800", "1900", "2000", "2100", "2200", "2300"};

    private static Logger logger = Logger.getLogger("timetable");
    private static String wrongNoOfRows = "has more rows than expected";
    private static String wrongNoOfColumns = "has more columns than expected";
    private static String noOfExpectedRows = "number of rows should be 8";
    private static String noOfExpectedColumns = "number of columns should be 17";
    private static String expectedRowToChange = "row to change should be > 0 and < 8";
    private static String expectedColumnToChange = "row to change should be > 0 and < 17";

    private final String[][] timetable;
    private final int noOfTimings = TIMINGS.length;
    private final int noOfDays = DAYS.length;
    private final int noOfRows;
    private final int noOfColumns;
    private boolean isCorrectSize;
    private boolean hasCorrectFirstRowsAndColumns;


    /**
     * uses format and timetableString to create a matrix uses the day and time to find the cell of
     * the matrix to input the message
     */
    public TimetableData(String filePath, String timetableString, int option,
        String day, String timing, String message) {
        requireAllNonNull(filePath, option);
        this.isCorrectSize = true;
        this.hasCorrectFirstRowsAndColumns = true;
        this.noOfRows = noOfDays + 1;
        this.noOfColumns = noOfTimings + 1;
        String[][] timetable;
        if (option == 1) {
            timetable = getTimetableFromString(timetableString);
        } else if (option == 2) {
            String locationFrom = filePath;
            timetable = getTimetableData(locationFrom);
        } else {
            timetable = getTimetableFromString(timetableString);
            int rowToChange = ArrayUtils.indexOf(DAYS_IN_LOWER_CASE, day.toLowerCase()) + 1;
            int columnToChange = ArrayUtils.indexOf(TIMINGS, timing) + 1;
            assert rowToChange > 0 && rowToChange < getRows() : expectedRowToChange;
            assert columnToChange > 0 && columnToChange < getColumns() : expectedColumnToChange;
            timetable[rowToChange][columnToChange] = message;
        }
        this.timetable = timetable;
        checkTimetableForCorrectRowsAndColumns();
    }

    /**
     * uses timetableString and create a timetable matrix
     */
    private String[][] getTimetableFromString(String timetableString) {
        String[][] timetableMatrix = createNewTimetable();
        if (timetableString == null || timetableString.equals("")) {
            return timetableMatrix;
        } else {
            String[] rows = timetableString.split("\n");
            if (rows.length > getRows()) {
                logger.log(Level.WARNING, wrongNoOfRows);
                this.isCorrectSize = false;
                return timetableMatrix;
            }
            for (int i = 0; i < getRows(); i++) {
                String[] decodedRows = rows[i].split(",");
                if (decodedRows.length != getColumns()) {
                    logger.log(Level.WARNING, wrongNoOfColumns);
                    this.isCorrectSize = false;
                }
                for (int j = 0; j < getColumns(); j++) {
                    byte[] decodedString = Base64.getDecoder().decode(decodedRows[j]);
                    decodedRows[j] = new String(decodedString);
                }
                timetableMatrix[i] = decodedRows;
            }
            return timetableMatrix;
        }
    }

    public int getRows() {
        assert this.noOfRows == noOfDays + 1 : noOfExpectedRows;
        return this.noOfRows;
    }

    public int getColumns() {
        assert this.noOfColumns == noOfTimings + 1 : noOfExpectedColumns;
        return this.noOfColumns;
    }

    /**
     * takes in a csv file via the location of the file and read the file
     *
     * @return string matrix of timetable in its format
     */
    public String[][] getTimetableData(String storedLocation) {
        String[][] timetableMatrix = createNewTimetable();
        File toRead = new File(storedLocation);
        if (toRead.exists()) {
            timetableMatrix = readTimetableData(storedLocation, timetableMatrix);
        }
        return timetableMatrix;
    }

    // @@author nigelngyy
    /**
     * @return a 2D boolean matrix of a timetable
     */
    public boolean[][] getBooleanTimetableData() {
        String[][] timetableMatrix = this.getTimetable();
        return stringMatrixToBooleanMatrix(timetableMatrix);
    }
    // @@author

    /**
     * read the data from the csv file in the stored location and write it to timetable Matrix
     *
     * @return timetableMatrix with values
     */

    private String[][] readTimetableData(String storedLocation, String[][] timetableMatrix) {
        // @@author souless94 -reused
        //Solution below gotten from grokonez
        // from https://grokonez.com/java/java-read-write-csv-file-opencsv-example
        int i = 0;
        try {
            FileReader fileReader = new FileReader(storedLocation);
            CSVReader csvReader = new CSVReader(fileReader);
            String[] timetableRow;
            while ((timetableRow = csvReader.readNext()) != null) {
                if (timetableRow.length != getColumns()) {
                    logger.log(Level.WARNING, wrongNoOfColumns);
                    this.isCorrectSize = false;
                    break;
                }
                if (i >= getRows()) {
                    logger.log(Level.WARNING, wrongNoOfRows);
                    this.isCorrectSize = false;
                    break;
                }
                for (int j = 0; j < getColumns(); j++) {
                    if ("".equals(timetableRow[j])) {
                        timetableRow[j] = " ";
                    }
                }
                timetableMatrix[i] = timetableRow;
                i++;
            }
            if (i != getRows()) {
                this.isCorrectSize = false;
            }
            csvReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //@@author
        return timetableMatrix;
    }

    /**
     * @return initialise a string matrix
     */
    private String[][] createNewMatrix() {
        String[][] matrix = new String[this.getRows()][this.getColumns()];
        for (int i = 0; i < this.getRows(); i++) {
            for (int j = 0; j < this.getColumns(); j++) {
                matrix[i][j] = " ";
            }
        }
        return matrix;
    }

    /**
     * fills the matrix with the days and timings according to format
     *
     * @return a string matrix of timetable
     */
    private String[][] createNewTimetable() {
        String[][] timetable = createNewMatrix();
        fillTimetableData(timetable);
        return timetable;
    }

    /**
     * set first row of timetable to be timings and set first column of timetable to be days
     */
    private void fillTimetableData(String[][] timetable) {
        // set first column to be days
        for (int i = 1; i < this.getRows(); i++) {
            timetable[i][0] = DAYS[i - 1];
        }
        // set first row  to be timings
        for (int j = 1; j < this.getColumns(); j++) {
            timetable[0][j] = TIMINGS[j - 1];
        }
    }

    /**
     * to check Timetable for correct first row and column
     */
    private void checkTimetableForCorrectRowsAndColumns() {
        String[] firstRow = this.timetable[0];
        if (!firstRow[1].equals(TIMINGS[0])
            && !firstRow[1].equals("800")) {
            this.hasCorrectFirstRowsAndColumns = false;
        }
        if (!firstRow[2].equals(TIMINGS[1])
            && !firstRow[2].equals("900")) {
            this.hasCorrectFirstRowsAndColumns = false;
        }
        for (int i = 3; i < getColumns(); i++) {
            String firstRowEntry = firstRow[i];
            if (!firstRowEntry.equals(TIMINGS[i - 1])) {
                this.hasCorrectFirstRowsAndColumns = false;
            }
        }
        for (int j = 1; j < getRows(); j++) {
            String firstColumnEntry = this.timetable[j][0];
            if (!firstColumnEntry.equals(DAYS[j - 1])) {
                this.hasCorrectFirstRowsAndColumns = false;
            }
        }
    }

    /**
     * @return true if timetable has correct number of rows and columns
     */
    public boolean isCorrectSize() {
        return this.isCorrectSize;
    }

    /**
     * @return true if timetable has correct days and correct timing
     */
    public boolean hasCorrectFirstRowsAndColumns() {
        return this.hasCorrectFirstRowsAndColumns;
    }

    /**
     * @return a string matrix of a timetable
     */
    public String[][] getTimetable() {
        return this.timetable;
    }

    /**
     * download timetable data as csv
     *
     * unable to download if same filename exists
     *
     * @param locationTo location of where to save the file
     */
    public void downloadTimetableDataAsCsv(String locationTo) {
        // @@author souless94 -reused
        //Solution below adapted from grokonez
        // from https://grokonez.com/java/java-read-write-csv-file-opencsv-example
        String filePath = locationTo;
        try {
            File toWrite = new File(filePath);
            if (!toWrite.exists()) {
                toWrite.createNewFile();
                FileWriter writer = new FileWriter(toWrite);
                CSVWriter csvWriter = new CSVWriter(writer,
                    CSVWriter.DEFAULT_SEPARATOR,
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);
                for (int i = 0; i < getRows(); i++) {
                    csvWriter.writeNext(this.timetable[i]);
                }
                csvWriter.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // @@author
    }

    // @@author nigelngyy
    /**
     * takes in 2d string matrix and converts it into a 2d boolean matrix
     *
     * @return a boolean 2D array
     */
    public boolean[][] stringMatrixToBooleanMatrix(String[][] timetable) {
        boolean[][] booleanTimetable = new boolean[this.noOfRows][this.noOfColumns];
        for (int i = 1; i < this.noOfRows; i++) {
            for (int j = 1; j < this.noOfColumns; j++) {
                if (timetable[i][j].equals(" ")) {
                    booleanTimetable[i][j] = true;
                } else {
                    booleanTimetable[i][j] = false;
                }
            }
        }
        return booleanTimetable;
    }
    // @@author
}

package seedu.address.model.person.timetable;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Base64;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;


/**
 * timetable data which will process the inputs and create a timetable
 */
public class TimetableData {

    private final String[][] timetable;
    private String[] timings = {"0800", "0900", "1000", "1100", "1200", "1300",
        "1400", "1500", "1600", "1700", "1800", "1900", "2000", "2100", "2200", "2300"};
    private String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday",
        "Saturday", "Sunday"};
    private final int noOfTimings = timings.length;
    private final int noOfDays = days.length;
    private final int rows;
    private final int columns;
    private final String format;


    /**
     * uses format and timetableString to create a
     */
    public TimetableData(String format, String filePath, String timetableString, int option) {
        this.format = format;
        int noOfRows = 0;
        int noOfColumns = 0;
        if (format.equals("vertical")) {
            noOfRows = noOfTimings;
            noOfColumns = noOfDays;
        } else if (format.equals("horizontal")) {
            noOfRows = noOfDays;
            noOfColumns = noOfTimings;
        }
        this.rows = noOfRows;
        this.columns = noOfColumns;
        if (option == 1) {
            this.timetable = getTimetableFromString(timetableString);
        } else {
            String locationFrom = filePath;
            this.timetable = getTimetableData(locationFrom);
        }
    }

    /**
     * uses timetableString and create a timetable matrix
     */
    private String[][] getTimetableFromString(String timetableString) {
        String[][] timetableMatrix = createNewTimetable();
        if (timetableString.equals("default")) {
            return timetableMatrix;
        } else {
            String[] rows = timetableString.split("\n");
            for (int i = 0; i < getRows(); i++) {
                String[] decodedRows = rows[i].split(",");
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
        return this.rows + 1;
    }

    public int getColumns() {
        return this.columns + 1;
    }

    /**
     * takes in a csv file via the location of the file and read the file
     *
     * @return string matrix of timetable in its format
     */
    public String[][] getTimetableData(String storedLocation) {
        String[][] timetableMatrix = createNewTimetable();
        timetableMatrix[0][0] = this.format;
        File toRead = new File(storedLocation);
        if (toRead.exists()) {
            timetableMatrix = readTimetableData(storedLocation, timetableMatrix);
        }
        return timetableMatrix;
    }

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
                timetableMatrix[i] = timetableRow;
                i++;
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

        timetable[0][0] = this.format;
        if (this.format.equals("horizontal")) {
            fillHorizontalTimetableData(timetable);
        } else if (this.format.equals("vertical")) {
            fillVerticalTimetableData(timetable);
        }
        return timetable;
    }

    /**
     * set first row of timetable to be timings and set first column of timetable to be days
     */
    private void fillHorizontalTimetableData(String[][] timetable) {
        // set first column to be days
        for (int i = 1; i < this.getRows(); i++) {
            timetable[i][0] = days[i - 1];
        }
        // set first row  to be timings
        for (int j = 1; j < this.getColumns(); j++) {
            timetable[0][j] = timings[j - 1];
        }
    }

    /**
     * set first row of timetable to be days and set first column of timetable to be timings
     */
    private void fillVerticalTimetableData(String[][] timetable) {
        // set first row to be days
        for (int i = 1; i < this.getColumns(); i++) {
            timetable[0][i] = days[i - 1];
        }
        // set first column to be timings
        for (int j = 1; j < this.getRows(); j++) {
            timetable[j][0] = timings[j - 1];
        }
    }

    /**
     * @return a string matrix of a timetable
     */
    public String[][] getTimetable() {
        return timetable;
    }

    /**
     * download timetable data as csv
     *
     * unable to download if same filename exists
     *
     * @param locationTo location of where to save the file
     */
    public void downloadTimetableData(String locationTo) {
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
                    csvWriter.writeNext(timetable[i]);
                }
                csvWriter.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // @@author
    }
}

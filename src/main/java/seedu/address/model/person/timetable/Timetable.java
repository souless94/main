package seedu.address.model.person.timetable;

import seedu.address.model.Entity;

/**
 * Represents a timetable in the address book. Guarantees: details are present
 */
public class Timetable extends Entity {

    // Identity fields
    private final String fileName;
    private final String format;
    private final String timetableString;

    // create timetable data
    private final TimetableData matrix;


    /**
     * Construct a timetable using the timetableString
     */
    public Timetable(String fileName, String format,
        String timetableString) {
        this.fileName = fileName + " timetable";
        this.format = format;
        this.matrix = new TimetableData(format, timetableString);
        this.timetableString = getTimetableDataString();
    }

    /**
     *   construct a timetable using a csv timetable file
     * @param fileName
     * @param format
     * @param index
     */
    public Timetable(String fileName, String format, int index) {
        this.fileName = fileName;
        this.format = format;
        this.matrix = new TimetableData(format, fileName, index);
        this.timetableString = getTimetableDataString();
    }

    /**
     * @return a timetable String for the ui
     */
    public String getTimetableAsString() {
        String timetableString = "";
        String[][] timetableMatrix = this.matrix.getTimetable();
        for (int i = 0; i < matrix.getRows(); i++) {
            for (int j = 0; j < matrix.getColumns(); j++) {
                if (i == matrix.getRows() - 1 && j == matrix.getColumns() - 1) {
                    timetableString += timetableMatrix[i][j];
                } else {
                    timetableString += timetableMatrix[i][j] + ",";
                }
            }
        }
        return timetableString;
    }

    /**
     * @return a timetable string for the xml storage
     */
    public String getTimetableDataString() {
        String timetableDataString = "";
        String[][] timetableMatrix = this.matrix.getTimetable();
        for (int i = 0; i < matrix.getRows(); i++) {
            for (int j = 0; j < matrix.getColumns(); j++) {
                if (j == matrix.getColumns() - 1) {
                    timetableDataString += timetableMatrix[i][j];
                } else {
                    timetableDataString += timetableMatrix[i][j] + ",";
                }
            }
            timetableDataString += "\n";
        }
        return timetableDataString;
    }

    public String getFormat() {
        return this.format;
    }

    public TimetableData getTimetable() {
        return this.matrix;
    }

    /**
     * download timetable to the given location
     */
    public void downloadTimetable() {
        String filepath = this.fileName;
        this.matrix.downloadTimetableData(filepath);
    }

    @Override
    public boolean isSame(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Timetable)) {
            return false;
        }
        Timetable otherTimetable = (Timetable) other;
        return otherTimetable.equals(other);
    }
}

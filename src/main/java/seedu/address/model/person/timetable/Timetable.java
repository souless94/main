package seedu.address.model.person.timetable;

import seedu.address.model.Entity;

/**
 * Represents a timetable in the address book. Guarantees: details are present
 */
public class Timetable extends Entity {

    // Identity fields
    private final String fileName;
    private final String storedLocation;
    private final String downloadLocation;
    private final String format;

    // create timetable data
    private final TimetableData matrix;


    /**
     *
     * @param fileName
     * @param format
     */
    public Timetable(String fileName, String format, String storedLocation,
        String downloadLocation) {
        this.fileName = fileName + ".csv";
        this.format = format;
        this.storedLocation = storedLocation.replace("\\", "/") + "/" + this.fileName;
        this.downloadLocation = downloadLocation + "/" + this.fileName;
        matrix = new TimetableData(format, this.storedLocation);
    }

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


    public String getFormat() {
        return this.format;
    }

    public TimetableData getTimetable() {
        return this.matrix;
    }

    /**
     * download timetable to the given location
     */
    public void downloadTimetable(String locationTo) {
        String filePath = locationTo.replace("\\", "/") + "/" + this.fileName;
        this.matrix.downloadTimetableData(filePath);
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

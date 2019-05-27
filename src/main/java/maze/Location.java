package maze;

import static java.util.Objects.hash;

public class Location {

    private int colLocation;
    private int rowLocation;

    public Location(int rowLocation, int colLocation) {
        this.rowLocation = rowLocation;
        this.colLocation = colLocation;
    }

    public int getRowLocation() {
        return rowLocation;
    }

    public int getColLocation() {
        return colLocation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return colLocation == location.colLocation &&
                rowLocation == location.rowLocation;
    }

    @Override
    public int hashCode() {

        return hash(colLocation, rowLocation);
    }

    @Override
    public String toString() {
        return "maze.Location{" +
                "colLocation=" + colLocation +
                ", rowLocation=" + rowLocation +
                '}';
    }
}

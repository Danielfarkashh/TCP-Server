package model;

import java.io.Serializable;
import java.util.Objects;

    //a class that represents location (row, column)

public class Index implements Serializable {
    int row, column;

    //constructor
    public Index(final int row, final int column) {
        this.row = row;
        this.column = column;
    }

    //checks if two indices are equal and if they are from the same type
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Index index = (Index) o;
        return row == index.row &&
                column == index.column;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

    @Override
    public String toString() {
        return "(" + row +
                "," + column +
                ')';
    }
}

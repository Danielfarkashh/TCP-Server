package model;

import java.util.*;

public class Matrix {

    int[][] primitiveMatrix; //2D array of int's

    //constructor that receive 2Darray of int's. it creats a safe clone to all the lines to the data member of p.matrix

    public Matrix(int[][] oArray) {
        List<int[]> list = new ArrayList<>();
        for (int[] row : oArray) {
            int[] clone = row.clone();
            list.add(clone);
        }
        primitiveMatrix = list.toArray(new int[0][]);
    }

    //constructor the generates a 5X5 matrix

    public Matrix() {
        Random r = new Random();
        primitiveMatrix = new int[5][5];
        for (int i = 0; i < primitiveMatrix.length; i++) {
            for (int j = 0; j < primitiveMatrix[0].length; j++) {
                primitiveMatrix[i][j] = r.nextInt(2);
            }
        }
        for (int[] row : primitiveMatrix) {
            String s = Arrays.toString(row);
            System.out.println(s);
        }
        System.out.println("\n");
    }


    //receive a matrix runs over its rows and represent it as a string
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int[] row : primitiveMatrix) {
            stringBuilder.append(Arrays.toString(row));
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    //checks if an index is in bounds of the matrix
    void tryAddIndex(Collection<Index> list, int row, int col) {
        try {
            int result = primitiveMatrix[row][col];
            list.add(new Index(row, col));
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }
    }

    // find the neighbors of the index (up, down, left, right) and also diagonal if mentioned
    public Collection<Index> getNeighbors(final Index index, boolean shouldIncludeDiagnol) {
        Collection<Index> list = new ArrayList<>();    //a list that will contain the neighbors of a specific index
        tryAddIndex(list, index.row + 1, index.column); //the down neighbor
        tryAddIndex(list, index.row, index.column + 1);  //the right neighbor
        tryAddIndex(list, index.row - 1, index.column); //the up neighbor
        tryAddIndex(list, index.row, index.column - 1);  //the left neighbor
        if (shouldIncludeDiagnol) {                          //include diagonals
            tryAddIndex(list, index.row + 1, index.column - 1); //bottom left
            tryAddIndex(list, index.row - 1, index.column + 1); //top right
            tryAddIndex(list, index.row + 1, index.column + 1); //bottom right
            tryAddIndex(list, index.row - 1, index.column - 1); //top left
        }
        return list;                                         //return the list of neighbors
    }

    //give us the value of a given index
    public int getValue(final Index index) {
        return primitiveMatrix[index.row][index.column];
    }

    //method that prints the matrix
    public void printMatrix() {
        for (int[] row : primitiveMatrix) {
            String s = Arrays.toString(row);
            System.out.println(s);
        }
    }

    //a method that return the data member of primitive matrix
    public final int[][] getPrimitiveMatrix() {
        return primitiveMatrix;
    }


}

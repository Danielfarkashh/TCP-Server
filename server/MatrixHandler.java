package server;

import model.Index;
import model.Matrix;
import model.TraversableMatrix;

import java.io.*;

    //this is a concrete handle that implements IHandler in order to handle tasks on matrices
    //this class "listens" to what the client sent


public class MatrixHandler implements IHandler {

    //the cases that MatrixHandler handles
    public final static String ACCEPT_MATRIX = "matrix";
    public final static String QUESTION_ONE = "1";
    public final static String QUESTION_TWO = "2";
    public final static String QUESTION_THREE = "3";
    public final static String QUESTION_FOUR = "4";
    public final static String STOP = "stop";
    private Matrix mMatrix = null;

    @Override
    public void handle(InputStream input, OutputStream output) throws Exception {
        ObjectInputStream in = new ObjectInputStream(input); //wraps the incoming stream of bytes
        ObjectOutputStream out = new ObjectOutputStream(output); //wraps the outgoing stream of bytes
        System.out.println("Server waiting for msg");
        while (true) {
            String msg = in.readObject().toString();   //read message from client (matrix, question number, indices, stop)
            switch (msg) {
                case ACCEPT_MATRIX:                    // "matrix" case: a matrix is about to "arrive"
                    System.out.println("matrix");
                    int[][] inMatrix = (int[][]) in.readObject(); //inMatrix(temp) will get the data of the incoming matrix
                    mMatrix = new Matrix(inMatrix);               //mMatrix gets the data of the inMatrix
                    break;
                case QUESTION_ONE:                               //"1" case: question number 1 is requested on mMatrix
                    System.out.println("1");
                    if (mMatrix != null) {                       //if mMatrix got values (if a matrix was even accepted)
                        out.writeObject(new TraversableMatrix(mMatrix).findSCC()); //send data back to client after
                                                    //using findSCC method in traversableMatrix class on the matrix
                    }
                    break;
                case QUESTION_TWO:                              //"2" case: question number 2 is requested on mMatrix
                    System.out.println("2");
                    if (mMatrix != null) {                      //if mMatrix got values
                        Index indexStart = (Index) in.readObject(); //receive source index and store it in indexStart
                        Index indexFinal = (Index) in.readObject(); //receive destination index and store it in indexFinal
                        //send data back to client after using getMinimumPath method, by sending it the matrix with the
                        //start index, destination index and true about using diagonals
                        out.writeObject(new TraversableMatrix(mMatrix).getMinimumPath(indexStart, indexFinal, true));
                    }
                    break;
                case QUESTION_THREE:                              //"3" case: question number 3 is requested on mMatrix
                    System.out.println("3");
                    if (mMatrix != null) {                        //if mMatrix got values
                        out.writeObject(new TraversableMatrix(mMatrix).getNumOfSubMarine()); //send data back to
                               //client after using getNumOfSubmarines method in traversableMatrix on the matrix
                    }
                    break;
                case QUESTION_FOUR:                              //"4" case: question number 4 is requested on mMatrix
                    System.out.println("4");
                    if (mMatrix != null) {                       //if mMatrix got values
                        Index indexStart = (Index) in.readObject(); //receive source index and store it in indexStart
                        Index indexFinal = (Index) in.readObject(); //receive destination index and store it in indexFinal
                        //send data back to client after using getMinimumPath method, by sending it the matrix with the
                        //start index, destination index and false about using diagonals
                        out.writeObject(new TraversableMatrix(mMatrix).getMinimumPath(indexStart, indexFinal, false));
                    }
                    break;
                case STOP: //"stop" case: requests to the connection
                    System.out.println("stop");
                    //close input and output streams
                    in.close();
                    out.close();
                    throw new Exception("STOP");
            }
        }
    }
}
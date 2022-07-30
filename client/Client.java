//authors: Asaf Hermann   204803159
//         Ofer Karp      209370030
//         Daniel Farkash 208388496

package client;

import model.Index;
import server.MatrixHandler;

import java.io.*;
import java.net.Socket;
import java.util.HashSet;
import java.util.List;

public class Client {
    //Example for question #1

    static int[][] QuestionOne_1 = {
            {1, 0, 0},
            {1, 0, 1},
            {0, 1, 1}
    };

    static int[][] QuestionOne_2 = {
            {1, 0, 0},
            {1, 0, 1},
            {0, 0, 1},
            {0, 1, 1}
    };

    static int[][] QuestionOne_3 = {
            {1, 0, 1, 1, 1},
            {1, 0, 1, 0, 0},
            {0, 0, 1, 0, 1}
    };
//---------------------------------------------------------------------------
    //Example for question #2

    final static Index indexStart = new Index(0, 0);
    final static Index indexFinal = new Index(16, 5);
    static int[][] QuestionTwo_1 = {
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
            {1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
            {1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0},
            {1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
            {1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
            {1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
            {1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
            {1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
            {1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
            {1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
            {1, 1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
            {1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
            {1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0},
            {1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}


    };
    final static Index indexStart2 = new Index(0, 0);
    final static Index indexFinal2 = new Index(2, 2);
    static int[][] QuestionTwo_2 = {
            {1, 1, 1},
            {1, 0, 1},
            {1, 1, 1}
    };
//---------------------------------------------------------------------------
    //Example for question #3

    static int[][] QuestionThree_1 = {
            {1, 1, 0, 1, 1},
            {1, 0, 0, 0, 1},
            {1, 0, 1, 1, 1}
    };

    static int[][] QuestionThree_2 = {
            {1, 0, 0, 1, 1},
            {1, 0, 0, 1, 1},
            {0, 1, 0, 1, 1}
    };

    static int[][] QuestionThree_3 = {
            {1, 0, 0, 1, 1},
            {1, 0, 0, 1, 1},
            {1, 0, 0, 1, 1}
    };

    static int[][] QuestionThree_4 = {
            {1, 1, 0, 1, 1},
            {0, 0, 0, 1, 1},
            {1, 1, 0, 1, 1}
    };
//---------------------------------------------------------------------------
    //Example for question #4

    final static Index indexStart4 = new Index(1, 0);
    final static Index indexFinal4 = new Index(1, 2);
    static int[][] QuestionFour_1 = {
            {100, 100, 100},
            {500, 900, 300}
    };


    //this method sends data to the server (matrix, question number and indices if needed)
    //it sends the data as a message to the switch/case in the matrix handler
    private static void sendMatrixAndQuestionNumber(int[][] matrix, String question, Index... indexes) throws Exception {
        out.writeObject(MatrixHandler.ACCEPT_MATRIX); //this "notes" the server that the next thing to get will be a matrix
        out.writeObject(matrix);                      //send matrix
        out.writeObject(question);                    //send question number
        //if there are indices then send them to the server
        if (indexes != null && indexes.length > 0) {  //check if there are indices (source and destination)
            for (int i = 0; i < indexes.length; i++) {//run over the relevant indices
                out.writeObject(indexes[i]);          //send indices
            }
        }
    }

    private static ObjectInputStream in; //receive data from the server in bytes
    private static ObjectOutputStream out; //send data to the server in bytes

    public static void main(String[] args) throws Exception {
        //localhost

        Socket socket = new Socket("127.0.0.1", 1992); //IP address and port
        InputStream inputStream = socket.getInputStream();      //receive data from through the socket
        OutputStream outputStream = socket.getOutputStream();   //deliver data through the socket
        out = new ObjectOutputStream(outputStream);
        out.flush();                                            //clean the buffer
        in = new ObjectInputStream(inputStream);

        System.out.println();
        System.out.println("Results question number 1:");
        System.out.println("-------------------------");
        //Test Question One
        sendMatrixAndQuestionNumber(QuestionOne_1, "1");
        List<HashSet<Index>> result1 = (List<HashSet<Index>>) in.readObject();
        System.out.println("Example 1: " + result1);
        sendMatrixAndQuestionNumber(QuestionOne_2, "1");
        List<HashSet<Index>> result2 = (List<HashSet<Index>>) in.readObject();
        System.out.println("Example 2: " + result2);
        sendMatrixAndQuestionNumber(QuestionOne_3, "1");
        List<HashSet<Index>> result3 = (List<HashSet<Index>>) in.readObject();
        System.out.print("Example 3: " + result3);
        System.out.println("\n");

        //Test Question Two
        System.out.println("Results question number 2:");
        System.out.println("-------------------------");
        sendMatrixAndQuestionNumber(QuestionTwo_1, "2", indexStart, indexFinal);
        List<Index> result1QuestionTwo = (List<Index>) in.readObject();
        System.out.println("Example 1: " + result1QuestionTwo);
        sendMatrixAndQuestionNumber(QuestionTwo_2, "2", indexStart2, indexFinal2);
        List<Index> result2QuestionTwo = (List<Index>) in.readObject();
        System.out.print("Example 2: " + result2QuestionTwo);
        System.out.println("\n");

        //Test Question Three
        System.out.println("Results question number 3:");
        System.out.println("-------------------------");
        sendMatrixAndQuestionNumber(QuestionThree_1, "3");
        int result1QuestionThree = (int) in.readObject();
        System.out.println("Example 1: " + result1QuestionThree);
        sendMatrixAndQuestionNumber(QuestionThree_2, "3", indexStart, indexFinal);
        int result2QuestionThree = (int) in.readObject();
        System.out.println("Example 2: " + result2QuestionThree);
        sendMatrixAndQuestionNumber(QuestionThree_3, "3", indexStart, indexFinal);
        int result3QuestionThree = (int) in.readObject();
        System.out.println("Example 3: " + result3QuestionThree);
        sendMatrixAndQuestionNumber(QuestionThree_4, "3", indexStart, indexFinal);
        int result4QuestionThree = (int) in.readObject();
        System.out.print("Example 4: " + result4QuestionThree);
        System.out.println("\n");

        System.out.println("Results question number 4:");
        System.out.println("-------------------------");
        sendMatrixAndQuestionNumber(QuestionFour_1, "4", indexStart4, indexFinal4);
        List<Index> result1QuestionFour = (List<Index>) in.readObject();
        System.out.println("Example 4: " + result1QuestionFour);

        out.writeObject(MatrixHandler.STOP);
        //Close the streamers and the socket
        in.close();
        out.close();
        socket.close();

    }
}

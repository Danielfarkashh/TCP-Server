package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TcpServer {
    private final int port;                  //server IP
    private volatile boolean stopServer;     //as long as its false continue "listening" to connection and handle clients
    private ThreadPoolExecutor threadPool;   //an queue of threads
    private IHandler requestHandler;         //handles tasks from same type (matrices in our case)

    public TcpServer(int port) {             //constructor
        this.port = port;                    //initialize the port
        // initialize data members (although they are initialized by default)
        stopServer = false;
        threadPool = null;
        requestHandler = null;
    }

    // listen to incoming connections, accept if possible and handle clients
    public void supportClients(IHandler concreteHandler) { //gets concrete handlers (matrices in our case)
        this.requestHandler = concreteHandler;

        new Thread(() -> {
            // lazy loading
            threadPool = new ThreadPoolExecutor(3, 5, 10, //initialize the threadPool
                                                                                               //min tasks can be accepted
                                                                                               //max
                                                                                //thread lifecycle od non working threads

                    TimeUnit.SECONDS, new LinkedBlockingQueue<>());         //when full block income tasks
                                                                            //when empty block outgoing tasks
                                        //is a linked list where all important methods inside are thread-safe
            try {

                ServerSocket serverSocket = new ServerSocket(port);  //create new server socket and bind ot to specific number
                                                        // the number means the amount of requests that can wait for the server
                while (!stopServer) {            //as long as stopServer is false
                    Socket serverToSpecificClient = serverSocket.accept(); // both listen to incoming requests and accepting requests
                        //this socket means the connection between the server and the current client that was able to connect to it
                        //for every successful connection with a client there will be a "operational socket" for each client
                    System.out.println("Accept connection"); //prints that connection accepted
                /*
                 server will handle each client in a separate thread
                 define every client as a Runnable task to execute
                 */
                    Runnable clientHandling = () -> {
                        try {
                            System.out.println("Handle connection");
                            requestHandler.handle(serverToSpecificClient.getInputStream(), //receive data from client
                                    serverToSpecificClient.getOutputStream());      //send results back to the client
                            // finished handling client. now close all streams
                            serverToSpecificClient.getInputStream().close();        //close input stream
                            serverToSpecificClient.getOutputStream().close();       //close output stream
                            serverToSpecificClient.close();                         //close the socket
                        } catch (Exception e) {
                            if (e.getMessage().equals("STOP")) {                //if the message received is "STOP"
                                                                                //then close end the connection
                                stop();
                                return;
                            }
                            e.printStackTrace();                                //location (path) of where the exception accrued
                            System.out.println("Error");                        //print string error
                        }
                    };

                    threadPool.execute(clientHandling);                        //execute the processes in clientHandling
                }
                serverSocket.close();                                          //close socket

            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }).start();                        //starts the entire supportClients method
    }

    //to close the server
    public void stop() {
        if (!stopServer)
            stopServer = true;    //change stopServer to true and that closes the server
        if (threadPool != null) threadPool.shutdown(); //checks if supportClients was executed and close threadPool
    }

    public static void main(String[] args) {
        TcpServer matrixServer = new TcpServer(1992); //defines the number of the port
        matrixServer.supportClients(new MatrixHandler());

    }
}



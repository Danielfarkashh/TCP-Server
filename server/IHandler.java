package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
    //concrete handlers will implement this interface
public interface IHandler {
    void handle(InputStream fromClient,
                OutputStream toClient) throws Exception;
}
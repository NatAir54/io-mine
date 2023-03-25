package com.studying.io.echo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(3000);
             Socket socket = server.accept();
             InputStream inputStream = socket.getInputStream();
             OutputStream outputStream = socket.getOutputStream()) {

            byte[] buffer = new byte[100];
            while (true) {
                int count = inputStream.read(buffer);
                String messageFromClient = new String(buffer, 0, count);
                System.out.println("Received message from the client: " + messageFromClient);

                String feedbackMessage = "echo " + messageFromClient;
                outputStream.write(feedbackMessage.getBytes());
                System.out.println("Message sent to the client: " + feedbackMessage);
            }
        } catch (IOException ex) {
            ex.getStackTrace();
        }
    }
}

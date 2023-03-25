package com.studying.io.echo_lesson13;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 3000);
             BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             OutputStream outputStream = socket.getOutputStream();
             InputStream inputStream = socket.getInputStream()) {

            byte[] buffer = new byte[100];
            while (true) {
                String inputMessage = readClientInputMessage(reader);
                outputStream.write(inputMessage.getBytes());
                System.out.println("Sent message to server: " + inputMessage);

                int count = inputStream.read(buffer);
                String messageFromServer = new String(buffer, 0, count);
                System.out.println("Received message from server: " + messageFromServer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static String readClientInputMessage(BufferedReader reader) throws IOException {
        System.out.println("Enter your message please!");
        return reader.readLine();
    }
}

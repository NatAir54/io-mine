package com.studying.io.echo.readerwriter;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Serverrw {
    public static void main(String[] args) throws IOException {
        try (ServerSocket server = new ServerSocket(3000);
             Socket socket = server.accept();
             BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))
        ) {
            while (true) {
                String messageFromClient = br.readLine();
                System.out.println(messageFromClient);
                if (messageFromClient.equals("")) {
                    break;
                }
//                bw.write("ECHO " + messageFromClient);
//                bw.newLine();
//                bw.flush();
            }
            System.out.println("Request obtained");

            bw.write("HTTP/1.1 200 OK");
            bw.newLine();
            bw.newLine();
            bw.write("Hello Chrome!");

//            or
//            String response = "HTTP/1.1 200 OK\n\nHello Chrome!";
//            bw.write(response);
        }
    }
}

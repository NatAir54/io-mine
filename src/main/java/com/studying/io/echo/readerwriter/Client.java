package com.studying.io.echo.readerwriter;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        try (Socket socket = new Socket("localhost", 3000);
             BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))
        ) {
            while(true) {
                bw.write("Hello!");
                bw.newLine();
                bw.flush();

                String echo = br.readLine();
                System.out.println("Answer from the server: " + echo);
            }
        }
    }
}

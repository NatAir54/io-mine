package com.studying.io.webserver;



public class Starter {
    public static void main(String[] args) {
        Server server = new Server();
//        server.setPort(3000);
//        server.setWebAppPath("src/main/resources/webapp");
//        server.start();
    }
}

// GET index.html
// response -> "src/main/resources/webapp" + "index.html"

// GET css\styles.css
// response -> "src/main/resources/webapp" + "css\styles.css"

// GET uri
// response -> webAppPath + uri

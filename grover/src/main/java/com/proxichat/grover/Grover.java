package com.proxichat.grover;

import java.io.IOException;

/**
 *
 */
public class Grover {
    public static void main(String[] args) {
        // Process command line arguments.

        // Load configuration file.

        // Open the socket and start listening.
        HTTPServer server = new HTTPServer(8080);
        try {
            server.listenAndServe();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

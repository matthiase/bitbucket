package com.proxichat.grover;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.ServerSocket;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

/**
 *
 */
public class HTTPServer {

    private final ExecutorService executorService;
    private final int port;
    private boolean receivedShutdownSignal = false;


    public HTTPServer(int port) {
        this.port = port;
        this.executorService = Executors.newFixedThreadPool(20);
        this.receivedShutdownSignal = false;
    }

    public int getPort() {
        return this.port;
    }

    public String getTimestamp() {
        return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
    }

    public BufferedReader getInputReader(Socket socket) throws IOException {
        BufferedInputStream in = new BufferedInputStream(socket.getInputStream());
        return new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
    }

    public BufferedWriter getOutputWriter(Socket socket) throws IOException {
        BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream());
        return new BufferedWriter(new OutputStreamWriter(out, StandardCharsets.UTF_8));
    }

    public void listenAndServe() throws IOException {
        log(String.format("Listening for connections on port %d.", this.getPort()));
        ServerSocket serverSocket = new ServerSocket(port);

        while (!receivedShutdownSignal) {
            Socket socket = serverSocket.accept();
            this.executorService.execute(new RequestHandler(this, socket));
        }
    }

    public void log(String message) {
        System.out.printf("[%s] %s\n", this.getTimestamp(), message);
        System.out.flush();
    }

}

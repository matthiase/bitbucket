package com.proxichat.grover;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.StringTokenizer;


/**
 *
 */
public class RequestHandler implements Runnable {
    private HTTPServer server;
    private Socket socket;
    private final static String lineSeparator;

    static {
        lineSeparator = "\r\n";
    }

    public RequestHandler(HTTPServer server, Socket socket) {
        this.server = server;
        this.socket = socket;
    }

    @Override
    public void run() {
        try( BufferedReader in = this.server.getInputReader(socket) ) {
            String requestLine = in.readLine();
            if (requestLine != null) {
                server.log(requestLine);
                StringTokenizer tokenizer = new StringTokenizer(requestLine);
                String httpMethod = tokenizer.nextToken().toUpperCase();
                if (httpMethod.equals("GET")) {
                    writeResponse("HTTP/1.1 200 OK", "text/plain", "Hello everybodee!");
                }
                else {
                    writeResponse("HTTP/1.1 501 Not Implemented", "text/plain", "");
                }
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void writeResponse(String status, String contentType, String content) {
        String header = new StringBuilder()
                .append(status).append(lineSeparator)
                .append("Server: Grover HTTP Server 1.0").append(lineSeparator)
                .append("Date: ").append(server.getTimestamp()).append(lineSeparator)
                .append("Content-type: ").append(contentType).append(lineSeparator)
                .append("Content-length: ").append(content.length()).append(lineSeparator)
                .append(lineSeparator)
                .toString();

        try( BufferedWriter out = server.getOutputWriter(socket) ) {
            out.write(header);
            out.write(content);
            out.flush();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}

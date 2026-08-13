package com.httpserver.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class HttpConnectionWorker extends Thread {

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpConnectionWorker.class);
    private Socket socket;

    public HttpConnectionWorker(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();

            int _byte;

            while ((_byte = inputStream.read()) >= 0) {
                System.out.print((char) _byte);
            }

            String html = "<html><head><title>Simple Java Server</title></head><body><h1>Using java http server</h1></body></html>";

            final String CRLF = "\n\r"; // 13, 10

            String response =
                    "HTTP/1.1 200 OK" + CRLF + // Status line : HTTP_Version RESPONSE_CODE RESPONSE_MESSAGE
                            "Content length: " + html.getBytes().length + CRLF + // header
                            CRLF + html + CRLF + CRLF;

            outputStream.write(response.getBytes());

            LOGGER.info("Connection processing finished");
        } catch (IOException e) {
            LOGGER.info("Problem with communication", e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) { }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) { }
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) { }
            }
        }

    }
}

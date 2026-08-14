package com.httpserver.http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HttpParserTest {

    private HttpParser httpParser;

    @BeforeEach
    public void beforeClass() {
        httpParser = new HttpParser();
    }

    @Test
    void parseHttpRequest() {
        try {
            HttpRequest request = httpParser.parseHttpRequest(generateValidGETTest());
            assertEquals(request.getMethod(), HttpMethod.GET);
        } catch (HttpParsingException e) {
            fail(e);
        }
    }

    @Test
    void parseHttpRequestInvalid() {
        try {
            HttpRequest request = httpParser.parseHttpRequest(generateInvalidGETTest());
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getErrorCode(), HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED);
        }
    }

    @Test
    void parseHttpRequestInvalid2() {
        try {
            HttpRequest request = httpParser.parseHttpRequest(generateInvalidGETTest2());
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getErrorCode(), HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED);
        }
    }

    @Test
    void parseHttpRequestInvalidNumberOfItems() {
        try {
            HttpRequest request = httpParser.parseHttpRequest(generateInvalidRequestLine());
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getErrorCode(), HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
        }
    }

    @Test
    void parseHttpEmptyRequestInvalid() {
        try {
            HttpRequest request = httpParser.parseHttpRequest(generateEmptyRequestLine());
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getErrorCode(), HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
        }
    }

    @Test
    void parseHttpRequestNoLineFeed() {
        try {
            HttpRequest request = httpParser.parseHttpRequest(generateInvalidOnlyCRAndNoLF());
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getErrorCode(), HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
        }
    }

    private InputStream generateValidGETTest() {
        String rawData = "GET / HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Connection: keep-alive\r\n" +
                "Cache-Control: max-age=0\r\n" +
                "sec-ch-ua: \"Not=A?Brand\";v=\"99\", \"Google Chrome\";v=\"151\", \"Chromium\";v=\"151\"\r\n" +
                "sec-ch-ua-mobile: ?0\r\n" +
                "sec-ch-ua-platform: \"Linux\"\r\n" +
                "Upgrade-Insecure-Requests: 1\r\n" +
                "User-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/151.0.0.0 Safari/537.36\r\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7\r\n" +
                "Sec-Fetch-Site: cross-site\r\n" +
                "Sec-Fetch-Mode: navigate\r\n" +
                "Sec-Fetch-User: ?1\r\n" +
                "Sec-Fetch-Dest: document\r\n" +
                "Accept-Encoding: gzip, deflate, br, zstd\r\n" +
                "Accept-Language: en-IN,en-GB;q=0.9,en-US;q=0.8,en;q=0.7,hi;q=0.6\r\n" +
                "\r\n";

        return new ByteArrayInputStream(rawData.getBytes(StandardCharsets.US_ASCII));
    }

    private InputStream generateInvalidGETTest() {
        String rawData = "GeT / HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "\r\n";

        return new ByteArrayInputStream(rawData.getBytes(StandardCharsets.US_ASCII));
    }

    private InputStream generateInvalidGETTest2() {
        String rawData = "GETTTTT / HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "\r\n";

        return new ByteArrayInputStream(rawData.getBytes(StandardCharsets.US_ASCII));
    }

    private InputStream generateInvalidRequestLine() {
        String rawData = "GET / AAAAA HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "\r\n";

        return new ByteArrayInputStream(rawData.getBytes(StandardCharsets.US_ASCII));
    }

    private InputStream generateEmptyRequestLine() {
        String rawData = "\r\n" +
                "Host: localhost:8080\r\n" +
                "\r\n";

        return new ByteArrayInputStream(rawData.getBytes(StandardCharsets.US_ASCII));
    }

    private InputStream generateInvalidOnlyCRAndNoLF() {
        String rawData = "GET / HTTP/1.1\r" +
                "Host: localhost:8080\r\n" +
                "\r\n";

        return new ByteArrayInputStream(rawData.getBytes(StandardCharsets.US_ASCII));
    }
}
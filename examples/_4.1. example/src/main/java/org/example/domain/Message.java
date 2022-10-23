package org.example.domain;

public class Message {

    private final String header;
    private final String body;
    private final String footer;

    public Message(String header, String body, String footer) {
        this.header = header;
        this.body = body;
        this.footer = footer;
    }

    public String getHeader() {
        return header;
    }

    public String getBody() {
        return body;
    }

    public String getFooter() {
        return footer;
    }
}

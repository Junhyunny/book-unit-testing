package org.example.domain;

public class HeaderRenderer implements Renderer {
    @Override
    public String render(Message message) {
        return new StringBuilder()
                .append("<h1>")
                .append(message.getHeader())
                .append("</h1>")
                .toString();
    }
}

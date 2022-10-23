package org.example.domain;

public class BodyRenderer implements Renderer {
    @Override
    public String render(Message message) {
        return new StringBuilder()
                .append("<body>")
                .append(message.getBody())
                .append("</body>")
                .toString();
    }
}

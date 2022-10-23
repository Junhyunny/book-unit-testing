package org.example.domain;

public class FooterRenderer implements Renderer {

    @Override
    public String render(Message message) {
        return new StringBuilder()
                .append("<footer>")
                .append(message.getFooter())
                .append("</footer>")
                .toString();
    }
}

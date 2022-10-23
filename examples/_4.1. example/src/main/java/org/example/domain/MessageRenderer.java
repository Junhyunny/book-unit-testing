package org.example.domain;

import java.util.Arrays;
import java.util.List;

public class MessageRenderer implements Renderer {
    List<Renderer> subRenders = Arrays.asList(
            new HeaderRenderer(),
            new BodyRenderer(),
            new FooterRenderer()
    );

    @Override
    public String render(Message message) {
        return subRenders.stream()
                .map(renderer -> renderer.render(message))
                .reduce("", (str1, str2) -> str1 + str2);
    }
}

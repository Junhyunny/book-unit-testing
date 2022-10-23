package org.example.domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class MessageRendererTests {

    @Test
    void messageRender_uses_correct_stub_renders() {

        MessageRenderer sut = new MessageRenderer();


        List<Renderer> renderers = sut.subRenders;


        assertEquals(renderers.size(), 3);
        assertInstanceOf(renderers.get(0).getClass(), new HeaderRenderer());
        assertInstanceOf(renderers.get(1).getClass(), new BodyRenderer());
        assertInstanceOf(renderers.get(2).getClass(), new FooterRenderer());
    }

    @Test
    void rendering_a_message() {

        MessageRenderer sut = new MessageRenderer();
        Message message = new Message("header", "body", "footer");


        String html = sut.render(message);


        assertEquals("<h1>header</h1><body>body</body><footer>footer</footer>", html);
    }
}
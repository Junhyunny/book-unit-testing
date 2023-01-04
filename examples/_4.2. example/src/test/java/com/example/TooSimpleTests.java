package com.example;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class User {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

public class TooSimpleTests {

    @Test
    void too_simple_test() {
        User sut = new User();

        sut.setName("Junhyunny");

        assertThat(sut.getName(), equalTo("Junhyunny"));
    }
}

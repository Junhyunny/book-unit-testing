package com.example;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class UserRepository {

    public User getById(int id) {
        // ..
        return null;
    }

    public String getLastExecutedSqlStatement() {
        return "select * from user where user_id = ${userId}";
    }
}
public class BrittleTests {

    @Test
    void brittle_test() {

        UserRepository sut = new UserRepository();


        User user = sut.getById(5);


        assertThat(sut.getLastExecutedSqlStatement(), equalTo("select * from user where user_id = ${userId}"));
    }
}

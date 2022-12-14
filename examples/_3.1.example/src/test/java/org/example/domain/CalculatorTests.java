package org.example.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculatorTests {

    @Test
    void sum_of_two_numbers() {
        // 준비
        double first = 10;
        double second = 20;
        Calculator calculator = new Calculator();

        // 실행
        double result = calculator.sum(first, second);

        // 검증
        assertEquals(30, result);
    }
}
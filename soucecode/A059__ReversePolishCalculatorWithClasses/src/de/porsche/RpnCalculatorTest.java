package de.porsche;

import static org.junit.jupiter.api.Assertions.*;

class RpnCalculatorTest {

    RpnCalculator calculator = null;
    String expression = "1 2 + 3 * 1 2 3 * + +";

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        calculator = new RpnCalculator();
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        calculator = null;
    }

    @org.junit.jupiter.api.Test
    void evaluate1000000Times() {
        for(int i = 0; i < 1000000; i++){
            int exp = 16;
            int act = assertDoesNotThrow(() -> {
                return calculator.evaluate(expression);
            });
            assertEquals(exp, act);
        }
    }
}
package de.porsche;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.EmptyStackException;
import java.util.Stack;

class RPNCalculatorPerformanceTest {

    String expression = "1 2 + 3 * 1 2 3 * + +";

    RPNCalculator rpnCalculator = null;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        rpnCalculator = new RPNCalculator();
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        rpnCalculator = null;
    }

    @org.junit.jupiter.api.Test
    void evaluate1000000Times() {
        for(int i = 0; i < 1000000; i++){
            int exp = 16;
            int act = assertDoesNotThrow(() -> {
                return rpnCalculator.processCommandLineParams(expression.split("\\s+"));
            });
            assertEquals(exp, act);
        }
    }

}


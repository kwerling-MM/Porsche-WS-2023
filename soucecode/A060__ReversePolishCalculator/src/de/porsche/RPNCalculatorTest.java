package de.porsche;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.EmptyStackException;
import java.util.Stack;

class RPNCalculatorTest {

    RPNCalculator rpnCalculator = null;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        rpnCalculator = new RPNCalculator();
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        rpnCalculator = null;
    }

    @Test
    public void testIsOperand() {
        assertTrue(                 rpnCalculator.isOperand("1"));
        assertEquals(true, rpnCalculator.isOperand("123"));
        assertFalse(                rpnCalculator.isOperand("+"));
        assertEquals(false, rpnCalculator.isOperand("-"));
        assertEquals(false, rpnCalculator.isOperand("*"));
        assertEquals(false, rpnCalculator.isOperand("/"));
    }

    @Test
    public void testPerformOperation() {
        assertEquals(3, rpnCalculator.performOperation("+", 1, 2));
        assertEquals(1, rpnCalculator.performOperation("-", 2, 1));
        assertEquals(4, rpnCalculator.performOperation("*", 2, 2));
        assertEquals(2, rpnCalculator.performOperation("/", 4, 2));
    }

    @Test
    public void testProcessCommandLineParams() {
         String[] args = new String[] { "1", "2", "+" };
        assertEquals(3, rpnCalculator.processCommandLineParams(args));


        args = new String[] { "2", "3", "*", "4", "+" };
        assertEquals(10, rpnCalculator.processCommandLineParams(args));
    }

    @Test
    public void testProcessCommandLineParams_InvalidOperator() {
        String[] args = new String[] { "1", "2", "%" };

        assertThrows(IllegalArgumentException.class, () -> {
            rpnCalculator.processCommandLineParams(args);
        });
    }

    @Test
    public void testProcessCommandLineParams_OneOperand() {
         String[] args = new String[] { "1" };

        assertEquals(1,  rpnCalculator.processCommandLineParams(args));
    }

    @Test
    public void testDoCalculation() {
        Stack<Integer> stack = new Stack<>();

        stack.push(1);
        stack.push(2);

        rpnCalculator.doCalculation(stack, "+");

        assertEquals(3, stack.pop());
    }

    @Test
    public void testDoCalculation_EmptyStack() {
        Stack<Integer> stack = new Stack<>();

        assertThrows(EmptyStackException.class, () -> {
            rpnCalculator.doCalculation(stack, "+");
        });
    }

    @Test
    public void testDoCalculation_OneOperand() {
        Stack<Integer> stack = new Stack<>();

        stack.push(1);

        assertThrows(EmptyStackException.class, () -> {
            rpnCalculator.doCalculation(stack, "+");
        });
    }
}





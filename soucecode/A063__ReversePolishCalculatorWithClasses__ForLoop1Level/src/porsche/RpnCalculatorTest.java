package porsche;

import static org.junit.jupiter.api.Assertions.*;

class RpnCalculatorTest {

    RpnCalculator calculator = null;

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
        if( 3*3 == 9) return;

        String expression = "1 2 + 3 * 1 2 3 * + +";

        for(int i = 0; i < 1000000; i++){
            int exp = 16;
            int act = assertDoesNotThrow(() -> {
                return calculator.evaluate(expression);
            });
            assertEquals(exp, act);
        }
    }


    @org.junit.jupiter.api.Test
    void print_dup01() {
        String expression = "1 2 + dup . ";

            int exp = 3;
            int act = assertDoesNotThrow(() -> {
                return calculator.evaluate(expression);
            });
            assertEquals(exp, act);
    }

    @org.junit.jupiter.api.Test
    void print_drop() {
        String expression = "1 2 45 drop + dup . ";

        int exp = 3;
        int act = assertDoesNotThrow(() -> {
            return calculator.evaluate(expression);
        });
        assertEquals(exp, act);
    }

    @org.junit.jupiter.api.Test
    void dupPrint () {
        String expression = "1 2 + d. ";

        int exp = 3;
        int act = assertDoesNotThrow(() -> {
            return calculator.evaluate(expression);
        });
        assertEquals(exp, act);
    }

    @org.junit.jupiter.api.Test
    void if_1level_noElse () {
        String expression = "1 2 + 4 <= dup if d. then";

        int exp = 1;
        int act = assertDoesNotThrow(() -> {
            return calculator.evaluate(expression);
        });
        assertEquals(exp, act);
    }

    @org.junit.jupiter.api.Test
    void if_1level_noElse_twice () {
        String expression = "1 2 + 4 <= dup if d. then 2 + dup 3 = If d. then";

        int exp = 3;
        int act = assertDoesNotThrow(() -> {
            return calculator.evaluate(expression);
        });
        assertEquals(exp, act);
    }


    @org.junit.jupiter.api.Test
    void if_1level_withElse_twice () {
        String expression = "1 2 + 4 >= dup if d. else 3 + then 2 + dup 3 = If d. else 42 + d. then";

        int exp = 47;
        int act = assertDoesNotThrow(() -> {
            return calculator.evaluate(expression);
        });
        assertEquals(exp, act);
    }

    @org.junit.jupiter.api.Test
    void if_2levels_withElse_twice () {
        String expression = "1 2 + dup 4 <= if d. dup 3 <= if 1 + d. else 100 + d. then else 300 + then 2 + dup 3 = If d. else 42 + d. then";

        int exp = 48;
        int act = assertDoesNotThrow(() -> {
            return calculator.evaluate(expression);
        });
        assertEquals(exp, act);
    }

    @org.junit.jupiter.api.Test
    void printTxt () {
        String expression = "1 2 + .\" Ergebnis: \" D. CR";

        int exp = 3;
        int act = assertDoesNotThrow(() -> {
            return calculator.evaluate(expression);
        });
        assertEquals(exp, act);
    }

    @org.junit.jupiter.api.Test
    void ForLoop_1Level () {
        String expression = "10 0 do i . cr Loop 0";

        int exp = 0;
        int act = assertDoesNotThrow(() -> {
            return calculator.evaluate(expression);
        });
        assertEquals(exp, act);
    }

    @org.junit.jupiter.api.Test
    void ForLoop_nested () {
        String expression = "10 0 do 10 0 do i . tb i-1 . tb .\" = \" tb i i-1 * . cr Loop Loop 0";

        int exp = 0; // 44 * 45;
        int act = assertDoesNotThrow(() -> {
            return calculator.evaluate(expression);
        });
        assertEquals(exp, act);
    }

    @org.junit.jupiter.api.Test
    void evaluate_SQR() {
        String expression = "16 Sqr";
        int exp = 256;
        int act = assertDoesNotThrow(() -> {
                return calculator.evaluate(expression);
        });
        assertEquals(exp, act);
    }

}
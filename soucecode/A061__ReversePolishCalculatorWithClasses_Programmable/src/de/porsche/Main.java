package de.porsche;

public class Main {
    public static void main(String[] args) throws Exception {
        String expression = "1 2 + 3 *";

        RpnCalculator calculator = new RpnCalculator();
        int result = calculator.evaluate(expression);

        System.out.println(result); //
    }
}
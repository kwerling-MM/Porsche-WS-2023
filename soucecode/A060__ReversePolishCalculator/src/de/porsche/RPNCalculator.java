package de.porsche;

import java.util.Stack;

public class RPNCalculator {

    public static void main(String[] args) {
        RPNCalculator rpnCalculator = new RPNCalculator();

        int result = rpnCalculator.processCommandLineParams(args);



        // Print the result to the console
        System.out.println("The result is: " + result);
    }

    int processCommandLineParams(String[] args) {
        // Create a stack to store the operands
        Stack<Integer> stack = new Stack<>();

        // Iterate over the command line parameters
        for (String arg : args) {
            // If the argument is an operand, push it onto the stack
            if (isOperand(arg)) {
                stack.push(Integer.parseInt(arg));
            } else {
                // If the argument is an operator, pop the top two elements of the stack, perform the operation, and push the result back onto the stack
                doCalculation(stack, arg);
            }
        }
        // The result of the calculation is now on the top of the stack
        return stack.pop();
    }

     void doCalculation(Stack<Integer> stack, String arg) {
        int operand2 = stack.pop();
        int operand1 = stack.pop();
        int result = performOperation(arg, operand1, operand2);
        stack.push(result);
    }

    boolean isOperand(String arg) {
        // An operand is a string that does not contain any of the following characters: +, -, *, /
        return !arg.contains("+") && !arg.contains("-") && !arg.contains("*") && !arg.contains("/");
    }

     int performOperation(String operator, int operand1, int operand2) {
        // Perform the specified operation on the two operands and return the result
        switch (operator) {
            case "+":
                return operand1 + operand2;
            case "-":
                return operand1 - operand2;
            case "*":
                return operand1 * operand2;
            case "/":
                return operand1 / operand2;
            default:
                throw new IllegalArgumentException("Unknown operator: " + operator);
        }
    }
}
package de.porsche;

import java.util.Stack;

interface Operator {

    int apply(int operand1, int operand2);
}

public class RpnCalculator {

    private final Stack<Integer> stack;

    public RpnCalculator() {
        stack = new Stack<>();
    }

    public int evaluate(String expression) throws Exception {
        String[] tokens = expression.split("\\s+");
        for (String token : tokens) {
            if (isOperator(token)) {
                Operator operator = getOperator(token);
                int operand2 = stack.pop();
                int operand1 = stack.pop();
                int result = operator.apply(operand1, operand2);
                stack.push(result);
            } else {
                int number = Integer.parseInt(token);
                stack.push(number);
            }
        }

        if (stack.size() != 1) {
            throw new Exception("Invalid RPN expression");
        }

        return stack.pop();
    }

    private boolean isOperator(String token) {
        return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/");
    }

    private Operator getOperator(String token) throws Exception {
        return switch (token) {
            case "+" -> new AdditionOperator();
            case "-" -> new SubtractionOperator();
            case "*" -> new MultiplicationOperator();
            case "/" -> new DivisionOperator();
            default -> throw new Exception("Invalid operator: " + token);
        };

        /*  switch (token) {
                case "+":   return new AdditionOperator();
                case "-":   return new SubtractionOperator();
                case "*":   return new MultiplicationOperator();
                case "/":   return new DivisionOperator();
                default:    throw new Exception("Invalid operator: " + token);
            }
         */
    }
}

class AdditionOperator implements Operator {

    @Override
    public int apply(int operand1, int operand2) {
        return operand1 + operand2;
    }
}

class SubtractionOperator implements Operator {

    @Override
    public int apply(int operand1, int operand2) {
        return operand1 - operand2;
    }
}

class MultiplicationOperator implements Operator {

    @Override
    public int apply(int operand1, int operand2) {
        return operand1 * operand2;
    }
}

class DivisionOperator implements Operator {

    @Override
    public int apply(int operand1, int operand2) {
        return operand1 / operand2;
    }
}

package de.porsche;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

interface Operator {

    void apply( Stack<Integer> stack );
}

interface Comparator {

    void apply( Stack<Integer> stack );

}

interface Command {

    int run( String[] tokens, int tokenPtr, Stack<Integer> stack );
    /*
    The int returned by the run method is the new tokenPtr.
    The reason for that is, that loops (While, For, Do) require 
    processing of the same tokens multiple times. Once the loop
    is done, the tokenPtr needs to be set to the next token after
    the end of the loop.
    
    There is also special processing required for If statements
    (Then and Else part).
     */
}

class StackHandling {
    static boolean tosIsFalse(Stack<Integer> stack){
        return stack.pop() == 0;
    }

    static boolean tosIsTrue(Stack<Integer> stack){
        return ! tosIsFalse( stack );
    }

}

public class RpnCalculator {

    private final Stack<Integer> stack;
    private SettingsAndAttributes saa = SettingsAndAttributes.getInstance();

    public RpnCalculator() {
        stack = new Stack<>();
    }

    public int evaluate(String expression) throws Exception {
        String[] tokens = expression.split("\\s+");
        int tokenPtr = 0;
        while (tokenPtr < tokens.length) {
            String token = tokens[ tokenPtr ].toUpperCase();
            if (isOperator(token)) {
                Operator operator = getOperator(token);
                operator.apply( stack );
                tokenPtr++;
            } else if (isComparator(token)) {
                Comparator comp = getComparator(token);
                comp.apply( stack );
                tokenPtr++;
            } else if( isCmd( token ) ) {
                tokenPtr = processCmdToken(token, tokenPtr, tokens);
            } else {
                int number = Integer.parseInt(token);
                stack.push(number);
                tokenPtr++;
            }
        }


        // if (stack.size() != 1) {
        if (stack.isEmpty()) {
            throw new Exception("Invalid RPN expression");
        }


        return stack.pop();
    }

    private int processCmdToken(String token, int tokenPtr, String[] tokens) throws Exception {
        if( saa.ifStm_active ) {
            // THEN ends the if statement
            if (token.equals("THEN")) {
                // Just continue the program
                tokenPtr++;
                saa.ifStm_active = false;
            } else if (token.equals("ELSE")) {
                // ELSE part can only show up when we need to process the IF part.
                // If the IF part would have been not processed, then the IFCMD had
                // already skipped to the ELSE part

                // Therefore read until THEN
                // as this part needs to be stopped.
                while (!token.equalsIgnoreCase("THEN")) {
                    tokenPtr++;
                    token = tokens[tokenPtr];
                }
            } else {
                tokenPtr = runCommand(tokens, tokenPtr, stack);
            }
        } else {
            tokenPtr = runCommand(tokens, tokenPtr, stack);
        }
        return tokenPtr;
    }

    private int runCommand(String[] tokens, int tokenPtr, Stack<Integer> stack) throws Exception {

        String token = tokens[tokenPtr];
        List<Command> cmds = getCommand(token);
        for (Command cmd : cmds) {
            tokenPtr = cmd.run(tokens, tokenPtr, stack);
        }

        return ++tokenPtr;
    }

    private boolean isCmd(String inpToken) {
        String token = inpToken.toUpperCase();
        return token.equals("IF")
                || token.equals("ELSE")
                || token.equals("THEN")
                || token.equals(".")
                || token.equals("D.")
                || token.equals("DUP")
                || token.equals("DROP")
                ;
    }

    private boolean isOperator(String inpToken) {
        String token = inpToken.toUpperCase();
        return  token.equals("+")
                || token.equals("-")
                || token.equals("*")
                || token.equals("/")
                || token.equals("SQR")
                ;
    }

    private boolean isComparator(String inpToken) {
        String token = inpToken.toUpperCase();
        return  token.equals(">")
                || token.equals("<")
                || token.equals(">=")
                || token.equals("<=")
                || token.equals("=")
                || token.equals("==")
                ;
    }

    private Operator getOperator(String token) throws Exception {
        return switch (token.toUpperCase()) {
            case "+" -> new AdditionOperator();
            case "-" -> new SubtractionOperator();
            case "*" -> new MultiplicationOperator();
            case "/" -> new DivisionOperator();
            case "SQR" -> new SqrOperator();
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

    private List<Command> getCommand(String token)  throws Exception {
        List<Command> cmds = new ArrayList<>();

        switch (token.toUpperCase()) {
            case "IF" ->    {  cmds.add( new CmdIF()    ); }
            case "." ->     {  cmds.add( new CmdPrint() ); }
            case "D." ->    {  cmds.add( new CmdDup()   );
                               cmds.add( new CmdPrint() ); }
            case "DUP" ->   {  cmds.add( new CmdDup()   ); }
            case "DROP" ->  {  cmds.add( new CmdDrop()  ); }
            default -> throw new Exception("Invalid command: " + token);
        };
        return cmds;
    }

    private Comparator getComparator(String token)  throws Exception {
        return switch (token.toUpperCase()) {
            case ">" -> new CompGT();
            case "<" -> new CompLT();
            case ">=" -> new CompGTE();
            case "<=" -> new CompLTE();
            case "=" -> new CompEQ();
            case "==" -> new CompEQ();
            default -> throw new Exception("Invalid command: " + token);
        };
    }

}

class AdditionOperator implements Operator {

    @Override
    public void apply( Stack<Integer> stack ) {

        int operand2 = stack.pop();
        int operand1 = stack.pop();

        int result = operand1 + operand2;
        stack.push(result);
    }
}

class SubtractionOperator implements Operator {

    @Override
    public void apply( Stack<Integer> stack ) {

        int operand2 = stack.pop();
        int operand1 = stack.pop();

        int result = operand1 - operand2;
        stack.push(result);
    }
}

class MultiplicationOperator implements Operator {

    @Override
    public void apply( Stack<Integer> stack ) {

        int operand2 = stack.pop();
        int operand1 = stack.pop();

        int result = operand1 * operand2;
        stack.push(result);
    }
}

class DivisionOperator implements Operator {

    @Override
    public void apply( Stack<Integer> stack ) {

        int operand2 = stack.pop();
        int operand1 = stack.pop();

        int result = operand1 / operand2;
        stack.push(result);
    }
}

class SqrOperator implements Operator {

    @Override
    public void apply( Stack<Integer> stack ) {

            int operand1 = stack.pop();

        int result = operand1 * operand1;
        stack.push(result);
    }
}

class CompGT implements Comparator {

    @Override
    public void apply( Stack<Integer> stack ) {

        int operand2 = stack.pop();
        int operand1 = stack.pop();

        boolean result = operand1 > operand2;
        stack.push(result?1:0);
    }
}

class CompLT implements Comparator {

    @Override
    public void apply( Stack<Integer> stack ) {

        int operand2 = stack.pop();
        int operand1 = stack.pop();

        boolean result = operand1 < operand2;
        stack.push(result?1:0);
    }
}

class CompGTE implements Comparator {

    @Override
    public void apply( Stack<Integer> stack ) {

        int operand2 = stack.pop();
        int operand1 = stack.pop();

        boolean result = operand1 >= operand2;
        stack.push(result?1:0);
    }
}

class CompLTE implements Comparator {

    @Override
    public void apply( Stack<Integer> stack ) {

        int operand2 = stack.pop();
        int operand1 = stack.pop();

        boolean result = operand1 <= operand2;
        stack.push(result?1:0);
    }
}

class CompEQ implements Comparator {

    @Override
    public void apply( Stack<Integer> stack ) {

        int operand2 = stack.pop();
        int operand1 = stack.pop();

        boolean result = operand1 == operand2;
        stack.push(result?1:0);
    }
}

class CmdIF extends StackHandling implements Command {
    @Override
    public int run(String[] tokens, int tokenPtr, Stack<Integer> stack) {

        SettingsAndAttributes saa = SettingsAndAttributes.getInstance();

        int retVal = -1;

        saa.ifStm_active = true;

        if( tosIsTrue( stack ) ) {
            // just continue
            // ++tokenPtr;
        } else {
            /* Must be FALSE
               Got to skip until Else or Then part starts
               Since Then ends the if statement we can continue after that.
             */
            while( ! tokens[tokenPtr].equalsIgnoreCase("ELSE")
              &&   ! tokens[tokenPtr].equalsIgnoreCase("THEN") ) {
                ++tokenPtr;
            }

            // Easy when we reached the then part-> if statement is finished,
            // nothing else to do
            if( tokens[tokenPtr].equalsIgnoreCase("ELSE") ) {
                // Nothing to do      ++tokenPtr;
            }
        }

        retVal = tokenPtr;
        return retVal;
    }
}

class CmdPrint extends StackHandling implements Command {
    @Override
    public int run(String[] tokens, int tokenPtr, Stack<Integer> stack) {
        Integer val = stack.pop();
        System.out.println( "Val: " + val );
        return tokenPtr;
    }
}

class CmdDup extends StackHandling implements Command {
    @Override
    public int run(String[] tokens, int tokenPtr, Stack<Integer> stack) {
        Integer val = stack.pop();
        stack.push( val );
        stack.push( val );
        return tokenPtr;
    }
}

class CmdDrop extends StackHandling implements Command {
    @Override
    public int run(String[] tokens, int tokenPtr, Stack<Integer> stack) {
        stack.pop();
        return tokenPtr;
    }
}


class SettingsAndAttributes{
    private static SettingsAndAttributes saa = new SettingsAndAttributes();

    private SettingsAndAttributes(){}

    public static SettingsAndAttributes getInstance() {
        return saa;
    }

    boolean ifStm_active = false;


}

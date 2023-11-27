package de.porsche;

import java.io.Console;

public class Main {
    public static void main(String[] args) {

        Console con = System.console();

        if( null  ==  con ) {
            System.err.println("Console read access unavailable!");
        } else {
            System.out.print("Enter some user input: ");
            String input = System.console().readLine();
            System.out.printf("You entered: %s", input);
        }
    }
}
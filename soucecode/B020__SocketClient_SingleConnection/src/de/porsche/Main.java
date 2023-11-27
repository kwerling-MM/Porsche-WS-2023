package de.porsche;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        EchoClient client = new EchoClient();
        try {
            client.startConnection("127.0.0.1", 7788);
            String response = client.sendMessage("hello server");
            System.out.println(response);
            client.stopConnection();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
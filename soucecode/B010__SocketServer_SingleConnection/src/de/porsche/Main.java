    package de.porsche;

    import java.io.IOException;

    public class Main {

        public static void main(String[] args) {
            EchoServer server=new EchoServer();
            try {
                server.start(7788);
                server.stop();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }

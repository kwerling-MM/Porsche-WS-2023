package de.porsche;

 class Timer {
     static long startTime = 0;
     static long endTime = 0;
     static void start() {
         startTime = System.nanoTime();
     }

     static void stop() {
         endTime = System.nanoTime();
     }

     static void results() {
         float duration = endTime - startTime;
         System.out.println();
         System.out.print("The operation took ");
         System.out.printf("%13.1f %s%n", duration,                      "nano seconds");
         System.out.printf("%32.1f %s%n", duration / 1000,               "micro seconds");
         System.out.printf("%32.1f %s%n", duration / 1000000,            "milli seconds");
         System.out.printf("%32.1f %s%n", duration / 1000000000,         "      seconds");
         System.out.printf("%32.1f %s%n", duration / 1000000000 / 60,    "      minutes");
         System.out.println();
     }

}

public class Main {

    public static void main(String[] args) {
        String sourceString = "1234".repeat(256);
        System.out.println("Source string length: " + sourceString.length());
        System.out.println();
        System.out.println();

        String resultString = "";

        // To get some idea of how long a loop takes
        System.out.println("Empty For-Loop, 100 iterations");
        Timer.start();
        for (int i = 0; i < 100; i++) {
        }
        Timer.stop();
        Timer.results();
        System.out.println("\n\n");


        System.out.println("Empty For-Loop, 1 million iterations");
        Timer.start();
        for (int i = 0; i < 1000000; i++) {
        }
        Timer.stop();
        Timer.results();
        System.out.println("\n\n");

        System.out.println("Concat string, 100 iterations");
        resultString = "";
        Timer.start();
        for (int i = 0; i < 100; i++) {
            resultString = resultString + sourceString;
        }
        Timer.stop();
        System.out.println("Result string length: " + resultString.length());
        Timer.results();
        System.out.println("\n\n");

        System.out.println("Concat string, 1000 iterations");
        resultString = "";
        Timer.start();
        for (int i = 0; i < 1000; i++) {
            resultString = resultString + sourceString;
        }
        Timer.stop();
        System.out.println("Result string length: " + resultString.length());
        Timer.results();
        System.out.println("\n\n");

        System.out.println("Concat string, 10000 iterations");
        resultString = "";
        Timer.start();
        for (int i = 0; i < 10000; i++) {
            resultString = resultString + sourceString;
        }
        Timer.stop();
        System.out.println("Result string length: " + resultString.length());
        Timer.results();
        System.out.println("\n\n");

//        System.out.println("Concat string, 100000 iterations");
//        resultString = "";
//        Timer.start();
//        for (int i = 0; i < 100000; i++) {
//            //  resultString = resultString + sourceString;
//            //  Took 1010 secs on my machine.
//        }
//        Timer.stop();
//        System.out.println("Result string length: " + resultString.length());
//        Timer.results();
//        System.out.println("\n\n");


        System.out.println("Concat string -- Stringbuffer , 100000 iterations");
        resultString = "";
        StringBuffer sbuf = new StringBuffer();
        Timer.start();
        for (int i = 0; i < 100000; i++) {
            sbuf.append(sourceString);
        }
        Timer.stop();
        resultString = sbuf.toString();
        System.out.println("Result string length: " + resultString.length());
        Timer.results();
        System.out.println("\n\n");


        System.out.println("Concat string -- Stringbuffer 32, 100000 iterations");
        resultString = "";
        sbuf = new StringBuffer(32);
        Timer.start();
        for (int i = 0; i < 100000; i++) {
            sbuf.append(sourceString);
        }
        Timer.stop();
        resultString = sbuf.toString();
        System.out.println("Result string length: " + resultString.length());
        Timer.results();
        System.out.println("\n\n");


        System.out.println("Concat string -- Stringbuilder , 100000 iterations");
        resultString = "";
        StringBuilder sbuild = new StringBuilder();
        Timer.start();
        for (int i = 0; i < 100000; i++) {
            sbuild.append(sourceString);
        }
        Timer.stop();
        resultString = sbuild.toString();
        System.out.println("Result string length: " + resultString.length());
        Timer.results();
        System.out.println("\n\n");


        System.out.println("Concat string -- String.repeat , 100000 iterations");
        resultString = "";
        Timer.start();
        resultString = sourceString.repeat(100000);
        Timer.stop();
        resultString = sbuild.toString();
        System.out.println("Result string length: " + resultString.length());
        Timer.results();
        System.out.println("\n\n");


    }
}
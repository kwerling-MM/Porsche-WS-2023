package de.porsche;

import java.util.Date;

class Main {

    private Thread thread;
    private boolean running;

    public Main() {
        running = true;
        thread = new Thread(() -> {
            while (running) {
                System.out.println(getTime() + "\t" + "Hello from the Class");
                Main.waitForSecs(10);
             }
        });
        thread.start();
    }

    @Override
    protected void finalize() throws Throwable {
        running = false;
        thread.interrupt();
        thread.join();
        System.out.println("Class is finished");
    }

    String getTime() {
        String retVal = "";
        Date date = new Date();
        long timeInMillis = date.getTime();

        // Calculate the hours, minutes, and milliseconds
        int hours = (int) date.getHours();
        int minutes = (int) ((timeInMillis / (1000 * 60)) % 60);
        int seconds = (int) ((timeInMillis / 1000) % 60);
        int milliseconds = (int) (timeInMillis % 1000);

        retVal = String.format("%02d", hours)   + ":" + String.format("%02d", minutes) + ":"
               + String.format("%02d", seconds) + "." + String.format("%03d", milliseconds);

        return retVal;
    }

    static void waitForSecs( int secs) {
        try {
            Thread.sleep(secs * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        Main instance = new Main();
        System.out.println("After instantiation -> Start waiting for 30 seconds.");
        Main.waitForSecs(30);
        System.out.println("Wait is over.");
        instance = null; // Collect the object for garbage collection
        System.out.println("After setting reference variable to null -> Start waiting for 30 seconds.");
        Main.waitForSecs(30);
        System.out.println("Wait is over.");
        System.gc();
        System.out.println("After calling Garbage Collector explicitely -> Start waiting for 30 seconds.");
        Main.waitForSecs(30);
        System.out.println("Wait is over.");
    }
}
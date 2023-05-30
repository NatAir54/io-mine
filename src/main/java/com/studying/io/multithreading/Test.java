package com.studying.io.multithreading;

import java.util.Arrays;
import java.util.List;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        Resource resource = new Resource();
        Thread thread = new Thread(new Incrementation(resource));
        Thread thread2 = new Thread(new Incrementation(resource));

        List<Thread> threadList = Arrays.asList(thread, thread2);

        for(Thread currentThread : threadList) {
            currentThread.start();
        }

        for(Thread currentThread : threadList) {
            currentThread.join();
        }

        System.out.println(resource.i);
    }
}

class Incrementation implements Runnable {
    Resource resource;

    public Incrementation(Resource resource) {
        this.resource = resource;
    }

    public void run() {
        for (int i = 0; i < 10000; i++) {
            resource.increment();
        }
    }
}

class Resource {
    final Object MONITOR = new Object();
    int i;

    void increment() {
        synchronized (MONITOR) {
            i++;
        }
    }

    // OR
    // void increment() {
    //    synchronized (this) {
    //      i++;
    //    }
    // }
}

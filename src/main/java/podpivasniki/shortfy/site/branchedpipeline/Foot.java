package podpivasniki.shortfy.site.branchedpipeline;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class Foot implements Runnable {

    private final String name;

    private final static Semaphore semaphoreLeft = new Semaphore(1);
    private final static Semaphore semaphoreRight  = new Semaphore(0);

    public Foot(String name) {
        this.name = name;
    }


    @Override
    public void run() {
        while (true){
            try {
                step();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private void step() throws InterruptedException {
        if (this.name.equals("left")) {
            semaphoreLeft.acquire();
            System.out.println("left");
            semaphoreRight.release();
        }
        if (this.name.equals("right")) {
            semaphoreRight.acquire();
            System.out.println("right");
            semaphoreLeft.release();
        }

    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new Foot("left"));
        Thread t2 = new Thread(new Foot("right"));
        t1.start();  // Используем start() вместо run()
        t2.start();  // Используем start() вместо run()

        Thread.sleep(1000);
        t1.join();
        t2.join();
    }
}

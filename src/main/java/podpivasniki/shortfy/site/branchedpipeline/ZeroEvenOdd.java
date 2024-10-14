package podpivasniki.shortfy.site.branchedpipeline;

import java.util.concurrent.Semaphore;
import java.util.function.IntConsumer;

class ZeroEvenOdd {
    private int n;

    private static Semaphore zeroSemaphore = new Semaphore(1);
    private static Semaphore evenSemaphore = new Semaphore(0);
    private static Semaphore oddSemaphore = new Semaphore(0);
    private volatile Integer current = 0;
    public ZeroEvenOdd(int n) {
        this.n = n;
    }
    

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void zero(IntConsumer printNumber) throws InterruptedException {
        zeroSemaphore.acquire();
        if(current > n)
            return;
        boolean isOdd = current % 2 == 0;
        printNumber.accept(0);
        if (isOdd) {
            current++;
            oddSemaphore.release();
        } else {
            current++;
            evenSemaphore.release();
        }
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        evenSemaphore.acquire(1);
        printNumber.accept(current);
        zeroSemaphore.release();
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        oddSemaphore.acquire(1);
        printNumber.accept(current);
        zeroSemaphore.release();
    }
}
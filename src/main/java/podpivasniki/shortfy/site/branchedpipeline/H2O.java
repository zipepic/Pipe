package podpivasniki.shortfy.site.branchedpipeline;

import java.util.concurrent.Semaphore;

class H2O {
    Semaphore oxegenSemaphore = new Semaphore(0);
    Semaphore hegrogenSemaphore = new Semaphore(2);


    public H2O() {
        
    }

    public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
        hegrogenSemaphore.acquire();
        // releaseHydrogen.run() outputs "H". Do not change or remove this line.
        releaseHydrogen.run();
        oxegenSemaphore.release();
    }

    public void oxygen(Runnable releaseOxygen) throws InterruptedException {
        oxegenSemaphore.acquire(2);
        // releaseOxygen.run() outputs "O". Do not change or remove this line.
		releaseOxygen.run();
        hegrogenSemaphore.release(2);
    }
}
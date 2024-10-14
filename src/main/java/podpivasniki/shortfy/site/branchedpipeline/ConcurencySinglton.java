package podpivasniki.shortfy.site.branchedpipeline;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConcurencySinglton {
    private static volatile ConcurencySinglton INSTANCE;
    private static Lock lock = new ReentrantLock();

    static  {
        System.out.println("Hi");
    }

    private ConcurencySinglton() {
    }

    public static ConcurencySinglton getInstance(){
        if(INSTANCE == null){
            synchronized (ConcurencySinglton.class){
                if(INSTANCE == null){
                    INSTANCE = new ConcurencySinglton();
                }
            }
        }
        return INSTANCE;
    }
}

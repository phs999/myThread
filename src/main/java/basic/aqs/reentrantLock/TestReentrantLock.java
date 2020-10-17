package basic.aqs.reentrantLock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * AQS源码阅读
 */
public class TestReentrantLock {

    private static volatile int i = 0;

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        for (int j = 0; j < 3; j++) {
            new Thread(()->{
                lock.lock();
                i++;
               // lock.unlock();
            }).start();
        }


    }
}

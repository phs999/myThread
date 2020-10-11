package basic.aqs.LockSupport;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;

/**
 * 官方给的LockSupport示例
 * 实现的是一个先进先出的线程等待队列
 * 使用park和unpark控制线程的阻塞和唤醒
 */
class FIFOMutex {
    private final AtomicBoolean locked = new AtomicBoolean(false);
    private final Queue<Thread> waiters
            = new ConcurrentLinkedQueue<>();

    public void lock() {
        boolean wasInterrupted = false;
        // publish current thread for unparkers
        waiters.add(Thread.currentThread());

        // Block while not first in queue or cannot acquire lock
        while (waiters.peek() != Thread.currentThread() ||
                !locked.compareAndSet(false, true)) {
            LockSupport.park(this);
            // ignore interrupts while waiting
            if (Thread.interrupted())
                wasInterrupted = true;
        }

        waiters.remove();
        // ensure correct interrupt status on return
        if (wasInterrupted)
            Thread.currentThread().interrupt();
    }

    public void unlock() {
        locked.set(false);
        LockSupport.unpark(waiters.peek());
    }

    static {
        // Reduce the risk of "lost unpark" due to classloading
        Class<?> ensureLoaded = LockSupport.class;
    }
}
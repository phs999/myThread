package interview.question2;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用Condition的好处在于可以把生产线程和消费线程区分开进行唤醒
 * @param <T>
 */
public class Container2<T> {
    final private LinkedList<T> lists = new LinkedList<>();
    final private int MAX = 10;
    //volatile int count=0;
    ReentrantLock lock = new ReentrantLock();
    Condition consumer = lock.newCondition();
    Condition producer = lock.newCondition();

    public void put(T o) {
        try {
            lock.lock();
            while (lists.size() == MAX) {
                System.out.println("生产完毕，生产者阻塞" + Thread.currentThread().getName());
                producer.await();
            }

            lists.add(o);
            consumer.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public synchronized T get() {
        T t=null;
        try {
            lock.lock();
            while (lists.size() == 0) {
                System.out.println("消耗完毕，消费者阻塞" + Thread.currentThread().getName());
                consumer.await();
            }
            t = lists.removeFirst();
            producer.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return t;
    }

    public synchronized int getCount() {
        return lists.size();
    }


    public static void main(String[] args) {
        Container2 c = new Container2();

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 5; j++) {//每个消费者线程消耗5个对象
                    System.out.println(Thread.currentThread().getName() + " 消费了 " + c.get());
                }
            }, "consumer" + i).start();
        }


        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                for (int j = 0; j < 25; j++) {//每个生产者线程生产25个对象
                    c.put(Thread.currentThread().getName() + " " + j);
                    System.out.println(Thread.currentThread().getName() + " 生产了 " + Thread.currentThread().getName() + " " + j);
                }
            }, "producer" + i).start();
        }
    }

}

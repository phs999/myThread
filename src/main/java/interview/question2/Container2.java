package interview.question2;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用Condition的好处在于可以把生产线程和消费线程区分开进行唤醒
 * @param <T>
 */
public class Container2<T> {
    final private LinkedList<T> lists = new LinkedList<>();
    final private int MAX = 10;
    ReentrantLock lock = new ReentrantLock();
    Condition consumer = lock.newCondition();
    Condition producer = lock.newCondition();

    public void put(T o) {
        try {
            lock.lock();
            while (lists.size() == MAX) {
                System.out.println("生产完毕，生产者 "+Thread.currentThread().getName()+" 阻塞中");
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

    public T get() {
        T t=null;
        lock.lock();
        try {
            while (lists.size() == 0) {
                System.out.println("库存为0，消费者 "+Thread.currentThread().getName()+" 阻塞中");
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


    public static void main(String[] args) throws InterruptedException {
        Container2 c = new Container2();

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 5; j++) {//每个消费者线程消耗5个对象
                    System.out.println(Thread.currentThread().getName()+" 消费了 "+c.get()+" 库存剩余："+c.getCount());
                }
            }, "consumer" + i).start();
        }

        TimeUnit.SECONDS.sleep(5);

        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                for (int j = 0; j < 25; j++) {//每个生产者线程生产25个对象
                    c.put(Thread.currentThread().getName() + " " + j);
                    System.out.println(Thread.currentThread().getName()+" 生产了 "+Thread.currentThread().getName()+" "+j+" 库存剩余："+c.getCount());
                }
            }, "producer" + i).start();
        }
    }

}

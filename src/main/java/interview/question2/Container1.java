package interview.question2;

import java.util.LinkedList;

public class Container1<T> {
    final private LinkedList<T> lists = new LinkedList<>();
    final private int MAX=10;
    public synchronized void put(T o){
        while (lists.size()==MAX){
            try {
                System.out.println("生产完毕，生产者 "+Thread.currentThread().getName()+" 阻塞中");
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        lists.add(o);
        this.notifyAll();
    }

    public synchronized T get(){
        while (lists.size()==0){
            try {
                System.out.println("库存为0，消费者 "+Thread.currentThread().getName()+" 阻塞中");
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        T t=lists.removeFirst();
        this.notifyAll();
        return t;
    }

    public synchronized int getCount(){
        return lists.size();
    }


    public static void main(String[] args) {
        Container1 c = new Container1();

        for (int i = 0; i <10 ; i++) {
            new Thread(()->{
                for (int j = 0; j < 5; j++) {//每个消费者线程消耗5个对象
                    System.out.println(Thread.currentThread().getName()+" 消费了 "+c.get()+" 库存剩余："+c.getCount());
                }
            },"consumer"+i).start();
        }


        for (int i = 0; i <2 ; i++) {
            new Thread(()->{
                for (int j = 0; j <25 ; j++) {//每个生产者线程生产25个对象
                    c.put(Thread.currentThread().getName()+" "+j);
                    System.out.println(Thread.currentThread().getName()+" 生产了 "+Thread.currentThread().getName()+" "+j+" 库存剩余："+c.getCount());
                }
            },"producer"+i).start();
        }
    }

}

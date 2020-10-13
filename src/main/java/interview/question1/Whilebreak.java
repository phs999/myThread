package interview.question1;

import java.util.ArrayList;
import java.util.List;

/**
 * 主要保证size的可见性没问题
 * t2通过while循环的方式占用cpu资源
 */
public class Whilebreak {
    volatile List list=new ArrayList();
    //volatile List list = Collections.synchronizedList(new LinkedList<>());
    public void add(Object o){
        list.add(o);
    }

    public synchronized int size(){
        return list.size();
    }

    public static void main(String[] args) {
        Whilebreak a=new Whilebreak();
        new Thread(()->{
            while (true){
                if (a.size()==5){
                    break;
                }
            }
            System.out.println("t2 end");
        },"t2").start();

        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                a.add(new Object());
                System.out.println("add"+i);
                /*try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
            }
        },"t1").start();



    }
}

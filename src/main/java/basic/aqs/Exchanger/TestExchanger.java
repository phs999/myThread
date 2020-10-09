package basic.aqs.Exchanger;

import java.util.concurrent.Exchanger;

/**
 * Exchanger支持两个线程交换指定类型T的值(对象的话是交换引用，基本类型的话直接交换值)
 * 一个线程在执行exchange（）方法后进入阻塞等待状态（也可以提前指定要等待的时间），等待另一个线程调用同一个exchanger对象的exchange（）方法
 * 交换完成后，线程不再阻塞，可以继续执行后面的代码
 */

public class TestExchanger {

    static Exchanger<String> exchanger = new Exchanger<>();

    public static void main(String[] args) {
        new Thread(()->{
            String s = "T1";
            try {
                s = exchanger.exchange(s);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " " + s);

        }, "t1").start();


        new Thread(()->{
            String s = "T2";
            try {
                s = exchanger.exchange(s);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " " + s);

        }, "t2").start();


    }
}

package basic.aqs.Semaphore;

import java.util.concurrent.Semaphore;

public class TestSemaphore {
    public static void main(String[] args) {
        //Semaphore s = new Semaphore(2);
        //Semaphore s = new Semaphore(2, true);
        //允许一个线程同时执行
        Semaphore s = new Semaphore(1);

        new Thread(()->{
            try {
                s.acquire();

                System.out.println("T1 begin to run...");
                Thread.sleep(200);
                System.out.println("T1 run over...");

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                s.release();
            }
        }).start();

        new Thread(()->{
            try {
                s.acquire();

                System.out.println("T2 begin to run...");
                Thread.sleep(200);
                System.out.println("T2 run over...");

                s.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}

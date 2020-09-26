package basic._synchronized.syncdeep;

import org.openjdk.jol.info.ClassLayout;

public class HelloJOL2 {


    private Object o = new Object();

    public void m() throws InterruptedException {
        synchronized(o) { //任何线程要执行下面的代码，必须先拿到o的锁
            System.out.println(Thread.currentThread().getName()+ "-----"+ClassLayout.parseInstance(o).toPrintable());
            Thread.sleep(5000);
        }
    }
    public static void main(String[] args) {
        new Thread(()-> {
            try {
                new HelloJOL2().m();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(()-> {
            try {
                new HelloJOL2().m();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

}


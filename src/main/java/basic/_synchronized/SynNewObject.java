package main.java.basic._synchronized;

public class SynNewObject {

    private int count = 10;
    private Object o = null;//new Object();

    public void m() {
        synchronized(o) { //任何线程要执行下面的代码，必须先拿到o的锁
            count--;
            System.out.println(Thread.currentThread().getName() + " count = " + count);
        }
    }


    public static void main(String[] args) {
        new Thread(()->new SynNewObject().m()).start();
    }

}

package main.java.basic._synchronized;

public class SynStaticMethod {
    private static int count = 10;

    public synchronized static void m() { //这里等同于synchronized(SynStaticMethod.class)
        count--;
        System.out.println(Thread.currentThread().getName() + " count = " + count);
    }

}

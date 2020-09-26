package basic._synchronized.syncdeep;

import org.openjdk.jol.info.ClassLayout;

public class HelloJOL {
    public static void main(String[] args) throws InterruptedException {
        //Thread.sleep(5000);
        Object o =new Object();
        System.out.println(ClassLayout.parseInstance(o).toPrintable());

        synchronized (o){
            System.out.println(ClassLayout.parseInstance(o).toPrintable());
        }
    }
}

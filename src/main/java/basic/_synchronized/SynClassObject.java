package main.java.basic._synchronized;

public class SynClassObject {

    private static int count = 10;

    public static void mm() {
        synchronized(SynClassObject.class) { //考虑一下这里写synchronized(this)是否可以？
            count --;
        }
    }
}

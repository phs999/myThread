package main.java.basic;

import java.util.concurrent.TimeUnit;

public class WhatIsThread {

    private static class T1 extends Thread{
        @Override
        public void run() {
            for (int i = 0; i <10 ; i++) {
                try {
                    TimeUnit.MICROSECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("T1");
            }
        }
    }

    public static void main(String[] args) {
        //new T1().run();//使用主线程执行，相当于方法调用，执行完后才继续往下执行
        new T1().start();//单独开辟一个线程执行，主线程可以不受影响的并行执行
        for (int i = 0; i <10 ; i++) {
            try {
                TimeUnit.MICROSECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("main");
        }
    }
}

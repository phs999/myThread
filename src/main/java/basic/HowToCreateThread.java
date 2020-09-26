package main.java.basic;

public class HowToCreateThread {
    static class MyThread1 extends Thread{
        @Override
        public void run() {
            System.out.println("创建线程的方式一：继承Thread类，重写run()方法");
        }
    }

    static class MyThread2 implements Runnable{

        @Override
        public void run() {
            System.out.println("创建线程的方式二：实现Runnable接口，实现run()方法");
        }
    }


    public static void main(String[] args) {
        new MyThread1().start();
        new Thread(new MyThread2()).start();

        new Thread(()->{
            System.out.println("创建线程的方式二的变种：用lambda表达式表示实现Runnable接口，实现run()方法");
        }).start();
    }
}

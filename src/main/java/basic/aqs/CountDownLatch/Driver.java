package basic.aqs.CountDownLatch;


import java.util.concurrent.CountDownLatch;

class Driver { // ...
    void main() throws InterruptedException {
      CountDownLatch startSignal = new CountDownLatch(1);
      CountDownLatch doneSignal = new CountDownLatch(15);
 
      for (int i = 0; i < 15; ++i) // create and start threads
        new Thread(new Worker(startSignal, doneSignal)).start();
 
      //doSomethingElse();            // don't let run yet开始前的准备工作
      startSignal.countDown();      // let all threads proceed 让线程正常工作取消阻塞
      //doSomethingElse();
      doneSignal.await();           // wait for all to finish等待所有线程执行完成
    }
  }

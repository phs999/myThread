/**
 * 曾经的面试题：（淘宝？）
 * 实现一个容器，提供两个方法，add，size
 * 写两个线程，线程1添加10个元素到容器中，线程2实现监控元素的个数，当个数到5个时，线程2给出提示并结束
 *
 * notify之后，t1必须释放锁，t2退出后，也必须notify，通知t1继续执行
 * 整个通信过程比较繁琐
 * 
 * 使用Latch（门闩）替代wait notify来进行通知
 * 好处是通信方式简单，同时也可以指定等待时间
 * 使用await和countdown方法替代wait和notify
 * CountDownLatch不涉及锁定，当count的值为零时当前线程继续运行
 * 当不涉及同步，只是涉及线程通信的时候，用synchronized + wait/notify就显得太重了
 * 这时应该考虑countdownlatch/cyclicbarrier/semaphore
 */
package interview.question1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CountDownLatch {

	// 添加volatile，使t2能够得到通知
	volatile List lists = new ArrayList();

	public void add(Object o) {
		lists.add(o);
	}

	public int size() {
		return lists.size();
	}

	public static void main(String[] args) {
		CountDownLatch c = new CountDownLatch();

		java.util.concurrent.CountDownLatch latch_t2 = new java.util.concurrent.CountDownLatch(1);
		java.util.concurrent.CountDownLatch latch_t1 = new java.util.concurrent.CountDownLatch(1);


		new Thread(() -> {
			System.out.println("t2启动");
				try {
					latch_t2.await();
					
					//也可以指定等待时间
					//latch.await(5000, TimeUnit.MILLISECONDS);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			System.out.println("t2 结束");
				latch_t1.countDown();

		}, "t2").start();

		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		new Thread(() -> {
			System.out.println("t1启动");
			for (int i = 0; i < 10; i++) {
				c.add(new Object());
				System.out.println("add " + i);

				if (c.size() == 5) {
					// 打开门闩，让t2得以执行
					latch_t2.countDown();
					try {
						latch_t1.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				/*try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}*/
			}

		}, "t1").start();

	}
}

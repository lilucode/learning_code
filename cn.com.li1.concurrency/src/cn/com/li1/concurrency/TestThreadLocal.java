package cn.com.li1.concurrency;

/**
 * 
 * ThreadLocal为每一个线程都提供了一个变量的副本（可以存放多个不同的变量），使得每个线程在某一时间访问到的
 * 并不是同一个对象，这样就隔离了多个线程对数据的数据共享
 * 
 * 总结一下：
 * 
 * 1）实际的通过ThreadLocal创建的副本是存储在每个线程自己的threadLocals中的；
 * 
 * 2）为何threadLocals的类型ThreadLocalMap的键值为ThreadLocal对象，因为每个线程中可有多个threadLocal变量，就像上面代码中的longLocal和stringLocal；
 * 
 * 3）在进行get之前，必须先set，否则会报空指针异常；
 * 
 * 如果想在get之前不需要调用set就能正常访问的话，必须重写initialValue()方法。
 */
public class TestThreadLocal {

	ThreadLocal<Long> longLocal = new ThreadLocal<Long>();
	ThreadLocal<String> stringLocal = new ThreadLocal<String>();

	// 没有先set，直接get的话，运行时会报空指针异常。需要重写initialValue方法：
	// ThreadLocal<Long> longLocal = new ThreadLocal<Long>(){
	// protected Long initialValue() {
	// return Thread.currentThread().getId();
	// };
	// };
	// ThreadLocal<String> stringLocal = new ThreadLocal<String>(){;
	// protected String initialValue() {
	// return Thread.currentThread().getName();
	// };
	// };

	// 对Thread类中的threadLocals进行初始化，并存放值
	public void set() {
		longLocal.set(Thread.currentThread().getId());
		stringLocal.set(Thread.currentThread().getName());
	}

	public void setChange() {
		longLocal.set(6L);
	}

	public long getLong() {
		return longLocal.get();
	}

	public String getString() {
		return stringLocal.get();
	}

	public static void main(String[] args) throws InterruptedException {
		final TestThreadLocal test = new TestThreadLocal();

		test.set();
		System.out.println(test.getLong());
		System.out.println(test.getString());

		Thread thread1 = new Thread() {
			public void run() {
				test.set();
				System.out.println(test.getLong());
				System.out.println(test.getString());
			};
		};
		thread1.start();
		// 父线程等待子线程结束之后才能继续运行
		thread1.join();

		// 会修改存储的值
		test.setChange();
		System.out.println(test.getLong());
		System.out.println(test.getString());
	}
}

package main;

public class ExampleWaitNotify {

	static Integer sum = null;
	static Object lock = new Object();
	
	public static void main(String[] args) {
		Thread t1 = new Thread1();
		Thread t2 = new Thread2();
		
		t1.start();
		t2.start();
	}
	
	// Izracunati sumu od 1 do 100
	static class Thread1 extends Thread {
		@Override
		public void run() {
			int tempSum = 0;
			for (int i = 1; i <= 1000; i++) {
				tempSum += i;
			}			
			sum = tempSum;
			
			synchronized (lock) {
				lock.notify();				
			}			
		}
	}
	
	// Isprintati sumu
	static class Thread2 extends Thread {
		@Override
		public void run() {
			synchronized (lock) {				
				while (sum == null) {
					try {
						lock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			System.out.println(sum);
		}
	}

}

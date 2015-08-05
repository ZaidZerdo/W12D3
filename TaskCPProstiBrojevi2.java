package main;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class TaskCPProstiBrojevi2 {

	private static LinkedBlockingQueue<Task> queue;
	private static Integer primeCounter = 0;
	private static Object lock = new Object();
	private static ArrayList<Worker> workers;
	
	public static void main(String[] args) {
		queue = new LinkedBlockingQueue<>();
		
		int workLoad = 1_000;
		int loadTaken = 0;
		while (loadTaken < 1_000_000) {
			Task t = new Task(loadTaken, loadTaken + workLoad);
			queue.add(t);
			
			loadTaken += workLoad;
		}
		
		workers = new ArrayList<>();
		long t = System.currentTimeMillis();
		for (int i = 0; i < 4; i++) {
			Worker w = new Worker();
			w.start();
			workers.add(w);
		}		
		
		for (Worker w : workers) {
			try {
				w.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("Time [ms]: " + (System.currentTimeMillis() - t));
		System.out.println("Primes: " + primeCounter);
	}
	
	static class Task implements Runnable {
		private int start;
		private int end;
		
		public Task(int start, int end) {
			this.start = start;
			this.end = end;
		}
		
		@Override
		public void run() {
			for (int i = start; i < end; i++) {
				if (isPrime(i)) {
					synchronized (lock) {
						primeCounter++;
					}
				}
			}
		}		
	}
	
	static class Worker extends Thread {
		@Override
		public void run() {
			while (!queue.isEmpty()) {
				try {
					Task t = queue.take();
					t.run();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	// 165_088 ms -> 74_999 ms
	public static boolean isPrime(int num) {
		if (num == 0 || num == 1) {
			return false;
		}
		
		for (int i = 2; i < num; i++) {
			if (num % i == 0) {
				return false;
			}
		}
		return true;
	}
	
	// 301 ms -> 123 ms
	public static boolean isPrimeEfficient(int num) {
		if (num == 0 || num == 1) {
			return false;
		}
		
		if (num == 2) {
			return true;
		}
		
		if (num % 2 == 0) {
			return false;
		}
		
		for (int i = 3; i <= Math.sqrt(num); i += 2) {
			if (num % i == 0) {
				return false;
			}
		}
		
		return true;
	}

}

package main;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class ExampleProducerConsumer {

	static LinkedBlockingQueue<Runnable> queue;
	static ArrayList<Slave> slaves;
	
	public static void main(String[] args) {
		queue = new LinkedBlockingQueue<>();
		// Produce the tasks and add them to the queue
		for (int i = 0; i < 3; i++) {
			queue.add(new Task());
		}
		
		// Make the consumers and let them consume
		slaves = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			Slave s = new Slave();
			s.start();
			slaves.add(s);
		}
		
	}
	
	// Consumers
	static class Slave extends Thread {
		
		@Override
		public void run() {			
			while (!queue.isEmpty()) {
				try {
					Runnable job = queue.take();
					job.run();					
				} catch (InterruptedException e) {
					break;
				}
			}
		}
	}
	
	// Jobs to do
	static class Task implements Runnable {

		@Override
		public void run() {
			for (int i = 0; i < 10; i++) {
				System.out.println(i);
			}
		}
		
	}

}

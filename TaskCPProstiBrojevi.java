package main;

public class TaskCPProstiBrojevi {

	private static int primeCounter = 0;
	
	public static void main(String[] args) {
		long t = System.currentTimeMillis();
		for (int i = 10; i < 1_000_000; i++) {
			if (isPrimeEfficient(i)) {
				primeCounter++;
			}
		}
		System.out.println("Time [ms]: " + (System.currentTimeMillis() - t));
		System.out.println("Primes: " + primeCounter);
	}
	
	// 165088 ms
	public static boolean isPrime(int num) {
		for (int i = 2; i < num; i++) {
			if (num % i == 0) {
				return false;
			}
		}
		return true;
	}
	
	// 301 ms
	public static boolean isPrimeEfficient(int num) {
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

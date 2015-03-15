package org.kash.algos.misc;


public class Deadlock {
	
	public static void main(String[] args) {
		
		Object obj1 = new Object();
		Object obj2 = new Object();
		
		ThreadA threadA = new ThreadA(obj1, obj2);
		ThreadB threadB = new ThreadB(obj1, obj2);
		
		new Thread(threadA).start();
		new Thread(threadB).start();
		
		
	}
}

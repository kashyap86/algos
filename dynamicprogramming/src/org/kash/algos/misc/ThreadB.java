package org.kash.algos.misc;

public class ThreadB implements Runnable {

	Object obj1;
	Object obj2;
	
	public ThreadB(Object obj1, Object obj2) {
		this.obj1 = obj1;
		this.obj2 = obj2;
	}
	
	@Override
	public void run() {
		
		synchronized(obj2) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			synchronized(obj1) {
				System.out.println(">>> End of Task.");
			}
		}
		
	}

}

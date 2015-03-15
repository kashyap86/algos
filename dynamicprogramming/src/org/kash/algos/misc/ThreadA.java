package org.kash.algos.misc;

public class ThreadA implements Runnable{

	Object obj1;
	Object obj2;
	
	public ThreadA(Object obj1, Object obj2) {
		this.obj1 = obj1;
		this.obj2 = obj2;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		synchronized(obj1) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			synchronized(obj2) {
				System.out.println(">>> End of Task.");
			}
		}
		
	}

}

package org.kash.algos.misc;

public class Factors {

	public static void main(String[] args) {
		Factors factors = new Factors();
		factors.printFactors(12);
	}
	
	private void printFactors(int number) {
	    printFactors("", number, number);
	}

	private void printFactors(String expression, int dividend, int previous) {
	    if(expression == "")
	        System.out.println(previous + " * 1");

	    for (int factor = dividend - 1; factor >= 2; --factor) {
	        if (dividend % factor == 0 && factor <= previous) {
	            int next = dividend / factor;
	            if (next <= factor && next <= previous)
	                System.out.println(expression + factor + " * " + next);

	            printFactors(expression + factor + " * ", next, factor);
	        }
	    }
	}
}

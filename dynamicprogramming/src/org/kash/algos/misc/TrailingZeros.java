package org.kash.algos.misc;

public class TrailingZeros {

	public static void main(String[] args) {
		TrailingZeros tz = new TrailingZeros();
		System.out.println("Trailing zeros = " + tz.trailingZeroes(1808548329));
	}
	
	public int trailingZeroes(int n) {
        int count = 0;
        for(long i=5; n/i >= 1; i*=5) {
            count += n/i;
        }
        return count;
    }
}

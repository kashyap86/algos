package org.kash.algos.misc;

public class UpperLowerCombinations {

	public static void main(String[] args) {
		String inputString = "abc";
		printCombinations(inputString);
	}

	private static void printCombinations(String inputString) {
		int len = inputString.length();
		int n = 1 << len;
		for(int i=0; i<n; i++) {
			char[] out = new char[len];
			for(int j = len-1, k=0; j>=0; j--) {
				out[j] = (isBitSet(i, k++) ? Character.toUpperCase(inputString.charAt(j)) : inputString.charAt(j));
			}
			System.out.println(out);
		}
		
	}

	private static boolean isBitSet(int i, int j) {
		return (i >> j & 1) != 0;
	}
}

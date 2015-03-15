package org.kash.algos.dp;

import java.util.ArrayList;
import java.util.List;

public class DynamicProgramming {
	
	public static void main(String args[]) {
		List<String> dictionary = new ArrayList<String>();
		dictionary.add("cat");
		dictionary.add("cats");
		dictionary.add("and");
		dictionary.add("sand");
		dictionary.add("dog");
		dictionary.add("catsanddog");
		
		String s = "catsanddog";
		String s2 = "select abc from t where label='test' and id='?' and name ='?'";
		s2= s2.replaceAll("'[?]'", "?");
		System.out.println(s2);
		DynamicProgramming dp = new DynamicProgramming();
		int result = dp.findMinSplit(dictionary, s);
		System.out.println(">>> result  = " + result);
		System.out.println(">>> Integer.MAX_VALUE = " + Integer.MAX_VALUE);
	}

	private int findMinSplit(List<String> dictionary, String s) {
		int n = s.length();
		int[] ans = new int[n+1];
		ans[n] = 0;
		for(int i = n-1; i>=0; i--) {
			ans[i] = Integer.MAX_VALUE;
			for(int j=i+1; j<=n; j++) {
				int cost = cost(s.substring(i, j), dictionary); 
				int min = Integer.MAX_VALUE;
				if(cost != Integer.MAX_VALUE)
					min = cost + ans[j];
				if(min < ans[i]) 
						ans[i] = min;
			}
		}
		
		return ans[0] - 1;
	}

	private int cost(String substring, List<String> dictionary) {
		if(dictionary.contains(substring))
			return 1;
		return Integer.MAX_VALUE;
	}
}

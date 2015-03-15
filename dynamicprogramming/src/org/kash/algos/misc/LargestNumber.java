package org.kash.algos.misc;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

public class LargestNumber {

	public static Comparator<Integer> intComp = new Comparator<Integer>() {
		public int compare(Integer i1, Integer i2) {
			if(i1 % 10 == 0 && i2 % 10 != 0)
					return 1;
			else if(i1 % 10 != 0 && i2 % 10 == 0)
					return -1;
			return i2 - i1;
		}
	};
	
	public static void main(String[] args) {
		LargestNumber ln = new LargestNumber();
		int[] numbers = {3, 30, 34, 5, 9};
		System.out.println(ln.largestNumber(numbers));
	}
	
	public String largestNumber(int[] num) {
        /*if(num == null || num.length <= 0) 
            return "";
        HashMap<Integer, PriorityQueue<Integer>> map = new HashMap<Integer, PriorityQueue<Integer>>();
        for(int i=0; i<num.length; i++) {
            Integer temp = findFirstDigit(num[i]);
            PriorityQueue<Integer> queue = null;
            if(map.get(temp) != null)
                queue = map.get(temp);
            else {
            	
                queue = new PriorityQueue<Integer>(num.length, intComp);
            }
            queue.add(num[i]);
            map.put(temp, queue);
        }
        PriorityQueue<Integer> q1 = new PriorityQueue<Integer>(map.size(), intComp);
        for(Integer key : map.keySet())
            q1.add(key);
        StringBuilder numSoFar = new StringBuilder();
        while(!q1.isEmpty()) {
            int curKey = q1.poll();
            PriorityQueue<Integer> tempq = map.get(curKey);
            while(!tempq.isEmpty()) {
                numSoFar.append(tempq.poll().toString());
            }
        }
        return numSoFar.toString();*/
		
		if(num==null || num.length==0)
            return "";
        String[] Snum = new String[num.length];
        for(int i=0;i<num.length;i++)
            Snum[i] = num[i]+"";

        Comparator<String> comp = new Comparator<String>(){
            public int compare(String str1, String str2){
                String s1 = str1+str2;
                String s2 = str2+str1;
                return s2.compareTo(s1);
            }
        };

        Arrays.sort(Snum,comp);

        StringBuilder sb = new StringBuilder();
        for(String s: Snum)
            sb.append(s);//sb.insert(0, s);

        /*if(sb.charAt(0)=='0')
            return "0";*/

        return sb.toString();
    }
    
    public Integer findFirstDigit(int num) {
        int tempNum = num;
        int curNum = num;
        while(tempNum > 0) {
            curNum = tempNum;
            tempNum /= 10;
        }
        return curNum;
    }
}
